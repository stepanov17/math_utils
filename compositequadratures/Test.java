package compositequadratures;

import java.util.function.DoubleFunction;


public class Test {

    private static double RelError(double exact, double approx) {
        return Math.abs((exact - approx) / exact);
    }

    public static void main(String[] args) {

        DoubleFunction<Double> f = (double x) -> Math.exp(x + Math.exp(x));
        double a = -1., b = 2.;
        int nSub = 100;
        byte nPts = 8;

        // check:
        //
        //   2/
        //    | exp(x + exp(x)) dx = exp(exp(2)) - exp(exp(-1))
        //  -1/

        double I = Math.exp(Math.exp(2)) - Math.exp(Math.exp(-1));

        double Q = (new CompositeQuadrature(f, a, b, nSub,
            new NewtonCotesQuadratureParameters(nPts))).compute();

        System.out.println("Newton-Cotes composite quadrature: rel. error = " +
            RelError(I, Q));

        Q = (new CompositeQuadrature(f, a, b, nSub,
            new GaussLegendreQuadratureParameters(nPts))).compute();

        System.out.println("Gauss-Legendre composite quadrature: rel. error = " +
            RelError(I, Q));

        Q = (new CompositeQuadrature(f, a, b, nSub,
            new GaussLobattoQuadratureParameters(nPts))).compute();

        System.out.println("Gauss-Lobatto composite quadrature: rel. error = " +
            RelError(I, Q));

        // check:
        //
        //   1/
        //    | x^2 / sqrt(1 - x^2) dx = PI / 2
        //  -1/

        I = 0.5 * Math.PI;

        f = (double x) -> x * x;

        Q = (new GaussChebyshevQuadrature(f, (byte)1, 1000)).compute();
        System.out.println("Gauss-Chebyshev composite quadrature, " + 
            "I kind: rel. error = " + RelError(I, Q));

        // check:
        //
        //   1/
        //    | x^2 * sqrt(1 - x^2) dx = PI / 8
        //  -1/

        I = 0.125 * Math.PI;

        Q = (new GaussChebyshevQuadrature(f, (byte)2, 1000)).compute();
        System.out.println("Gauss-Chebyshev composite quadrature, " + 
            "II kind: rel. error = " + RelError(I, Q));
    }
}
