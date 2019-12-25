import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class BackpropAlgo {
    static String pathName;
    static Scanner input;
    static int M, L, N, T;
    static double learningRate, errorLimit;
    static double[] X, Y, I, O, Neth, Neto, S, So, Sh;
    static double[][] Wh, Wo;


    public static void main(String[] args) throws FileNotFoundException {
        pathName = "data";
        input = new Scanner(new File(pathName));
        M = input.nextInt();
        L = input.nextInt();
        N = input.nextInt();
        T = input.nextInt();
        X = new double[M];
        Y = new double[N];
        Wh = new double[L][M];
        Neth = new double[L];
        I = new double[L];
        Wo = new double[N][L];
        Neto = new double[N];
        O = new double[N];
        S = new double[N];
        So = new double[N];
        Sh = new double[L];
        learningRate = 0.1;
        errorLimit = 10.0;

        initWeights(Wh, -5, 5);
        initWeights(Wo, -5, 5);

        for (int t = 0; t < T; t++) {
            for (int i = 0; i < M; i++) {
                X[i] = input.nextDouble();
            }

            for (int i = 0; i < N; i++) {
                Y[i] = input.nextDouble();
            }

            double error = Double.MAX_VALUE;
            while (error > errorLimit) {
                feedForward();
                backPropagate();
                error = meanSquareError(O, Y);
            }
        }

        System.out.println("Weights of hidden layer:");
        for(int i = 0; i < L; i++){
            for(int j = 0; j < M; j++){
                System.out.print(Wh[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Weights of output layer:");
        for(int i = 0; i < N; i++){
            for(int j = 0; j < L; j++){
                System.out.print(Wo[i][j] + " ");
            }
            System.out.println();
        }
        
    }

    static void initWeights(double[][] W, double min, double max) {
        for (int i = 0; i < W.length; i++) {
            for (int j = 0; j < W[i].length; j++) {
                W[i][j] = min + (max - min) * new Random().nextDouble();
            }
        }
    }

    static double activfun(double net, double c) {
        return 1 / (1 + Math.exp(-c * net));
    }

    static double[] mulMatByVec(double[][] mat, double[] vec) {
        double[] resultVec = new double[mat.length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                resultVec[i] += mat[i][j] * vec[j];
            }
        }
        return resultVec;
    }

    static double meanSquareError(double[] Y, double[] O) {
        double result = 0;
        for (int i = 0; i < Y.length; i++) {
            result += Math.pow(Y[i] - O[i], 2);
        }
        return result;
    }

    static boolean feedForward() {
        Neth = mulMatByVec(Wh, X);
        for (int i = 0; i < Neth.length; i++) {
            I[i] = activfun(Neth[i], 1);
        }
        Neto = mulMatByVec(Wo, I);
        for (int i = 0; i < Neto.length; i++) {
            O[i] = activfun(Neto[i], 1);
        }

        return true;
    }

    static boolean backPropagate() {
        calcErrorTerms();
        updateWeights();
        return true;
    }

    static boolean calcErrorTerms() {
        for (int i = 0; i < N; i++) {
            S[i] = Y[i] - O[i];
        }

        for (int i = 0; i < N; i++) {
            So[i] = O[i] * (1 - O[i]) * S[i];
        }

        for (int i = 0; i < L; i++) {
            double sum = 0;
            for (int j = 0; j < N; j++) {
                sum += S[j] * Wo[j][i];
            }
            Sh[i] = I[i] * (1 - I[i]) * sum;
        }

        return true;
    }

    static boolean updateWeights() {
        for (int k = 0; k < Wo.length; k++) {
            for (int j = 0; j < Wo[k].length; j++) {
                Wo[k][j] = Wo[k][j] + learningRate * So[k] * I[j];
            }
        }

        for (int j = 0; j < Wh.length; j++) {
            for (int i = 0; i < Wh[j].length; i++) {
                Wh[j][i] = Wh[j][i] + learningRate * Sh[j] * X[i];
            }
        }
        return true;
    }
}
