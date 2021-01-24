package pl.edu.pjwstk.jaz.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;


@Repository
public class UserService {
    private final EntityManager entityManager;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();

    public UserService (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveUser(User user){
        var userEntity = new UserEntity ();
        userEntity.setUsername (user.getUsername ());
        userEntity.setPassword (passwordEncoder.encode (user.getPassword ()));
        userEntity.setAuthorities (String.join(",", user.authorities));
        entityManager.persist (userEntity);
    }

    public boolean doesUserExist (String username){
            if (entityManager.createQuery ("SELECT ue FROM UserEntity ue WHERE ue.username= :username", UserEntity.class)
                    .setParameter ("username", username)
                    .getResultList ().isEmpty ()) return false;
            else return true;
    }
    public boolean isEmpty(){
        if (entityManager.createQuery ("SELECT ue FROM UserEntity ue ", UserEntity.class)
                .getResultList ().isEmpty ()) return true;
        else return false;
    }

    public UserEntity findUserByUsername(String username){
        return entityManager.createQuery ("SELECT ue FROM UserEntity ue WHERE ue.username= :username", UserEntity.class)
                .setParameter ("username", username)
                .getSingleResult ();
    }

    public UserEntity findCurrentUser(){
        return entityManager.createQuery ("SELECT ue FROM UserEntity ue WHERE ue.username= :username", UserEntity.class)
                .setParameter ("username", getCurrentUsername())
                .getSingleResult ();
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User) {
            String username = ((User) principal).getUsername();
            return username;
        }else {
            return principal.toString();
        }
    }
}
