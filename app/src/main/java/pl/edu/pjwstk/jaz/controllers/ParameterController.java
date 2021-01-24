package pl.edu.pjwstk.jaz.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.jaz.requests.ParameterRequest;
import pl.edu.pjwstk.jaz.services.ParameterService;

import javax.transaction.Transactional;

@RestController
public class ParameterController {
    ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @PostMapping("/parameter")
    @Transactional
    public void createParameter(@RequestBody ParameterRequest parameterRequest){
        parameterService.createParameter(parameterRequest);
    }
}
