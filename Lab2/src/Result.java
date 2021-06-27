import org.apache.commons.math3.stat.inference.MannWhitneyUTest;

import java.util.Arrays;

public class Result {
    private double pValue;
    private double sigma;


    /*private double getSigma(Sample sample1, SVD sample2){

    }*/

    public Result(double[] T_data, double[] X_data, SVD sample2, int interval_start, int interval_size, int row){
        int left_bound = interval_start;
        int right_bound = interval_start + interval_size;
        double[] check_sample = new double[interval_size];
        double[] check_sample1;
        double[] check_sample2;
        double math_sr = 0;
        for (int i = left_bound; i<right_bound; i++)
        {
            check_sample[i-left_bound] = X_data[i] - sample2.get(T_data[i]);
            math_sr += Math.pow(check_sample[i-left_bound], 2);
        }
        check_sample1 = Arrays.copyOfRange(check_sample, 0,interval_size/2);
        check_sample2 = Arrays.copyOfRange(check_sample, interval_size/2, interval_size);
        MannWhitneyUTest MWT = new MannWhitneyUTest();
        this.pValue = MWT.mannWhitneyUTest(check_sample1, check_sample2);
        this.sigma = Math.sqrt(math_sr/(interval_size-row));
        //this.sigma = getSigma(sample1, sample2);
    }

    public String get_pValue(double my_pValue){
        String If_Homogeneous = " - false ";
        if (my_pValue<=pValue)
            If_Homogeneous = " - true ";
        return  pValue + If_Homogeneous;
        //return String.format("%.3f",pValue) + " " + If_Homogeneous;
    }
    public String getSigma(){
        return sigma + "";
        //return String.format("%.5f",sigma);
    }
}
