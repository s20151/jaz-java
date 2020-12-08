package pl.edu.pjwstk.jaz;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@RestController
public class RegisterControllerDB {
    UserService userService;

    public RegisterControllerDB (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerdb")
    @Transactional
    public void register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response){
        if(userService.doesUserExist (registerRequest.getUsername ())){
            response.setStatus (HttpStatus.CONFLICT.value ()); //User already exists.
        }else {
            if(registerRequest.getPassword ()==null || registerRequest.getPassword ()==""){
                response.setStatus (HttpStatus.CONFLICT.value ()); //Invalid password.
            }else {
                Set<String> authorities = new HashSet<> ();
                if(userService.isEmpty ()){
                    authorities.add ("admin");
                }
                authorities.add ("user");
                userService.saveUser (new User (registerRequest.getUsername (),registerRequest.getPassword (),authorities)); //New user created.
            }
        }
    }
}
