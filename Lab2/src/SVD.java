import org.apache.commons.math3.linear.*;

public class SVD {
    private double[] beta;

    public  SVD(double[][] z_matrix, double[] x_vector){
        double lamda = 0.5;
        RealMatrix rm = MatrixUtils.createRealMatrix(z_matrix);
        RealMatrix E = MatrixUtils.createRealIdentityMatrix(z_matrix.length);
        RealVector rv = MatrixUtils.createRealVector(x_vector);
        DecompositionSolver decompositionSolver = new SingularValueDecomposition(rm.multiply(rm.transpose()).add(E.scalarMultiply(lamda))).getSolver();
        RealVector answer = decompositionSolver.solve(rm.transpose().preMultiply(rv));
        double[] answer_vector = new double[answer.getDimension()];
        for (int i =0; i<answer.getDimension(); i++){
            answer_vector[i] = answer.getEntry(i);
        }
        this.beta = answer_vector.clone();
        //return decompositionSolver.solve(new Array2DRowRealMatrix(rv)).getColumn(0);
    }

    public double get (double t){
        double res = 0.;
        for (int i = 0; i < this.beta.length; i++) {
            res += this.beta[i] * Math.pow(t, i);
        }
        return res;
    }

    public String getBeta(){
        String str = "gammas: ";
        for (int i = 0; i < this.beta.length; i++) {
            str = str + this.beta[i] + " ";
            //str = str + String.format("%.3f",this.beta[i]) + " ";
        }
        str += "\n" + "betas: " + this.beta[1] + " " + this.beta[2]/2 +" " +  this.beta[3]/6 +" " +  this.beta[4]/24;
        return str;
    }
}