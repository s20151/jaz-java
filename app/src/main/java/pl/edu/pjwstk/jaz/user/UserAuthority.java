package pl.edu.pjwstk.jaz.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthority {
    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/userauthority")
    public void testAuthority(){

    }
}
