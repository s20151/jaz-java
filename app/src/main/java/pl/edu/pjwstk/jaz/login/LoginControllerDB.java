package pl.edu.pjwstk.jaz.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.AuthenticationServiceDB;
import pl.edu.pjwstk.jaz.UnauthorizedException;


@RestController
public class LoginControllerDB {
    private final AuthenticationServiceDB authenticationService;

    public LoginControllerDB (AuthenticationServiceDB authenticationService){
        this.authenticationService=authenticationService;
    }
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){
        var isLogged = authenticationService.login (loginRequest.getUsername (),loginRequest.getPassword ());
        if(!isLogged){
          throw new UnauthorizedException();
        }
    }
}
