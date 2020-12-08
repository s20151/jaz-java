package pl.edu.pjwstk.jaz;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthority {
    @PreAuthorize ("hasAnyAuthority('admin')")
    @GetMapping("/testauthority")
    public void testAuthority(){

    }
}
