package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){
        var isLogged = authenticationService.login (loginRequest.getUsername (),loginRequest.getPassword ());
        if(!isLogged){
          throw new UnauthorizedException ();
        }
    }

    @PostMapping("/auth0/login")
    public void loginFilter(@RequestBody LoginRequest loginRequest){
        var isLogged = authenticationService.login (loginRequest.getUsername (),loginRequest.getPassword ());
        if(!isLogged){
            throw new UnauthorizedException ();
        }
    }

}
