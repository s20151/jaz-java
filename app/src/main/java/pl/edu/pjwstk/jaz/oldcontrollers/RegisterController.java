package pl.edu.pjwstk.jaz.oldcontrollers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.register.RegisterRequest;
import pl.edu.pjwstk.jaz.user.User;
import pl.edu.pjwstk.jaz.user.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;


@RestController
public class RegisterController {
    final
    UserRepository userRepository;

    public RegisterController (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/registerold")
    public void register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response){
        if(userRepository.doesUserExist (registerRequest.getUsername ())){
            response.setStatus (HttpStatus.CONFLICT.value ()); //User already exists.
        }else {
            if(registerRequest.getPassword ()==null || registerRequest.getPassword ()==""){
                response.setStatus (HttpStatus.CONFLICT.value ()); //Invalid password.
            }else {
                Set<String> authorities = new HashSet<> ();
                if(userRepository.isEmpty ()){
                    authorities.add ("admin");
                }
                authorities.add ("user");
                userRepository.addUser (new User(registerRequest.getUsername (),registerRequest.getPassword (),authorities)); //New user created.
            }
        }
    }
}
