package pl.edu.pjwstk.jaz.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.requests.SectionRequest;
import pl.edu.pjwstk.jaz.services.SectionService;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
public class SectionController {
    SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/section")
    @Transactional
    public void createSection(@RequestBody SectionRequest sectionRequest, HttpServletResponse response){
        sectionService.createSection(sectionRequest, response);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/section/{sectionID}")
    @Transactional
    public void createSection(@PathVariable("sectionID") Long id, @RequestBody SectionRequest sectionRequest, HttpServletResponse response){
        sectionService.editSection(id,sectionRequest, response);
    }
}
