package pl.edu.pjwstk.jaz.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.requests.SectionRequest;
import pl.edu.pjwstk.jaz.services.SectionService;

import javax.transaction.Transactional;

@RestController
public class SectionController {
    SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }
    @PostMapping("/section")
    @Transactional
    public void createSection(@RequestBody SectionRequest sectionRequest){
        sectionService.createSection(sectionRequest);
    }
}
