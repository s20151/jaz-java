package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AverageController {
    public AverageResult getAverage(@RequestParam(value = "numbers", required = false)String numbers){
        String[] strArray = numbers.split (",");
        int[] numbersArray = new int[strArray.length];
        for ( int i = 0; i < strArray.length; i++ ) {
            numbersArray[i] = Integer.parseInt (strArray[i]);
        }
    return new AverageResult (numbersArray);
    }
}
