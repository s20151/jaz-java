package pl.edu.pjwstk.jaz.oldcontrollers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.login.LoginRequest;
import pl.edu.pjwstk.jaz.UnauthorizedException;


@RestController
public class LoginController {
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }
    @PostMapping("/loginold")
    public void login(@RequestBody LoginRequest loginRequest){
        var isLogged = authenticationService.login (loginRequest.getUsername (),loginRequest.getPassword ());
        if(!isLogged){
          throw new UnauthorizedException();
        }
    }

    @PostMapping("/auth0/loginold")
    public void loginFilter(@RequestBody LoginRequest loginRequest){
        var isLogged = authenticationService.login (loginRequest.getUsername (),loginRequest.getPassword ());
        if(!isLogged){
            throw new UnauthorizedException ();
        }
    }

}
