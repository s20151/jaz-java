package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginControllerDB {
    private final AuthenticationServiceDB authenticationService;

    public LoginControllerDB (AuthenticationServiceDB authenticationService){
        this.authenticationService=authenticationService;
    }
    @PostMapping("/logindb")
    public void login(@RequestBody LoginRequest loginRequest){
        var isLogged = authenticationService.login (loginRequest.getUsername (),loginRequest.getPassword ());
        if(!isLogged){
          throw new UnauthorizedException ();
        }
    }
}
