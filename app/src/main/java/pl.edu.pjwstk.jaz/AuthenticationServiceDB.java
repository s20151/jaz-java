package pl.edu.pjwstk.jaz;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.jaz.user.User;
import pl.edu.pjwstk.jaz.user.UserEntity;
import pl.edu.pjwstk.jaz.user.UserService;
import pl.edu.pjwstk.jaz.user.UserSession;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Component
public class AuthenticationServiceDB {
    final
    UserSession userSession;
    final
    UserService userService;

    public AuthenticationServiceDB (UserSession userSession, UserService userService) {
        this.userSession = userSession;
        this.userService = userService;
    }

    public boolean login(String username, String password){
        if(userService.doesUserExist (username)){
            final UserEntity currentUser = userService.findUserByUsername (username);
            final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder ();
            if(encoder.matches (password,currentUser.getPassword ())){
                userSession.logIn ();
                String[] authoritiesArray = currentUser.getAuthorities ().split(",");
                Set<String> authoritiesAsSet = new HashSet<String> (Arrays.asList(authoritiesArray));
                User user = new User (currentUser.getUsername (),currentUser.getPassword (), authoritiesAsSet);
                SecurityContextHolder.getContext ().setAuthentication (new AppAuthentication (user));
                return true; // Logged in.
            }else{
                return false; // Invalid password.
            }
        }else{
            return false; // User not found.
        }
    }
}
