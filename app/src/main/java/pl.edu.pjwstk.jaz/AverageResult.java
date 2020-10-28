package pl.edu.pjwstk.jaz;

public class AverageResult {
    private double average;

    public AverageResult (int[] numbers) {
        this.average=calculateAverage (numbers);
    }

    public double calculateAverage(int[] numbers){
        int suma=0;
        int ilosc=0;
        for(int number:numbers){
            suma+=number;
            ilosc++;
        }
        double avg= (double)suma/(double)ilosc;
        double roundedAvg = Math.round(avg * 100.0) / 100.0;
        return roundedAvg;
    }

    @Override
    public String toString () {
        return "Average: " + average;
    }
}
