package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AverageController {
    @GetMapping("average")
    public String getAverage(@RequestParam(value = "numbers", required = false)String numbers){
        if(numbers==null){
            return "Please put parameters.";
        }
        String[] stringsArray = numbers.split(",");
        int[] numbersArray = new int[stringsArray.length];
        for ( int i = 0; i < stringsArray.length; i++ ) {
            numbersArray[i] = Integer.parseInt (stringsArray[i]);
        }
        int sum=0;
        for(int number:numbersArray){
            sum+=number;
        }
        double avg = (double)sum/(double)numbersArray.length;
        double roundedAvg = Math.round(avg * 100.0) / 100.0;
        if(roundedAvg==(int)roundedAvg){
            return "Average equals: "+ (int)roundedAvg;
        }else {
            return "Average equals: "+ roundedAvg;
        }
    }
}
