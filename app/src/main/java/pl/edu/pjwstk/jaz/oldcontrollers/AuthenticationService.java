package pl.edu.pjwstk.jaz.oldcontrollers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.jaz.AppAuthentication;
import pl.edu.pjwstk.jaz.user.User;
import pl.edu.pjwstk.jaz.user.UserRepository;
import pl.edu.pjwstk.jaz.user.UserSession;


@Component
public class AuthenticationService {
    final
    UserSession userSession;
    final
    UserRepository userRepository;

    public AuthenticationService (UserSession userSession, UserRepository userRepository) {
        this.userSession = userSession;
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password){
        if(userRepository.doesUserExist (username)){
            String storedPassword = userRepository.getPassword (username);
            if(storedPassword.equals (password)){
                userSession.logIn ();
                User user = userRepository.getUser (username);
                SecurityContextHolder.getContext ().setAuthentication (new AppAuthentication(user));
                return true; // Logged in.
            }else{
                return false; // Invalid password.
            }
        }else{
            return false; // User not found.
        }
    }
}
