import java.util.Random;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.distribution.CauchyDistribution;

public class Annealing {
    static double T0 = 10;
    static Random rand = new Random();
    static double f(double[] x, double[] t, double beta_0, double beta_1, int my_N){
        double res = 0;
        for (int i = 0; i<my_N; i++){
            res += Math.pow(x[i] - beta_0 * Math.exp(beta_1*t[i]), 2);
        }
        return res;
    }

    static double Temp(int i, String type){
        if (type.equals("Boltzmann"))
            return T0 / (Math.log(1 + i));
        else
            return T0 / i;
    }

    static double[] G(double[] point, double T, String type){
        if (type.equals("Boltzmann"))
        {
            double[][] cov = new double[2][2];
            for (int i = 0; i<2; i++)
                cov[i][i] = T;
            MultivariateNormalDistribution beta_new = new MultivariateNormalDistribution(point, cov);
            return beta_new.sample();
        }
        else{
            double[] sample = new double[2];
            for (int i = 0; i<2; i++)
                sample[i] = new CauchyDistribution(point[i], T).sample();
            return sample;
        }
    }

    static String annealing (double[] x, double[] t, int N, String type){
        double[] beta= new double[2];
        double h;
        double deltaE = 0;
        beta[0] = 0;
        beta[1] = 0;
        double[] beta_new = beta;
        double E = f(x, t, beta[0], beta[1], N);
        for (int i = 1; i<=1000; i++) {
            double T = Temp(i, type);
            if (f(x, t, beta[0], beta[1], N) < E) {
                E = f(x, t, beta[0], beta[1], N);
            }
            do{
                /*do beta_new = G(beta, T, type);
                while (beta_new[0]<0||beta_new[1]>0||beta_new[0]>10||beta_new[1]<-10);*/
                beta_new = G(beta, T, type);

                deltaE = f(x, t, beta_new[0], beta_new[1], N) - E;
                if (deltaE<0)
                    h = 1;
                h = Math.exp(-deltaE/T);
            }while (h<=rand.nextFloat());
            beta = beta_new;
            //E = f(x, t, beta[0], beta[1], N);
        }
        return type + " " + beta[0] +  " " + beta[1] + " \n";
    }
}
