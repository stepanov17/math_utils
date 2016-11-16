package compositequadratures;

import java.util.concurrent.RecursiveTask;
import java.util.function.DoubleFunction;

public class CompositeQuadrature extends RecursiveTask<Double> {

    private final double EPS = 1.e-10; // min subinterval length

    // integrand
    private final DoubleFunction<Double> integrand;
    // limits of integration
    private final double a, b;
    // use non-composite quadrature for subintervals with length < subintervalLength
    private final double subintervalLength;

    private final QuadratureParameters parameters;

    public CompositeQuadrature(DoubleFunction<Double> f,
                               double                 a,
                               double                 b,
                               double                 subintervalLength,
                               QuadratureParameters   parameters) {
        this.integrand = f;

        if (Double.isNaN(a) || Double.isInfinite(a) ||
            Double.isNaN(b) || Double.isInfinite(b)) {
            throw new IllegalArgumentException("invalid integration limits");
        }
        this.a = a;
        this.b = b;

        if (Double.isNaN(subintervalLength) || Double.isInfinite(subintervalLength) ||
                (subintervalLength < EPS)) {
            throw new IllegalArgumentException("invalid subintervalLength value: " +
                subintervalLength + ", should be >= " + EPS);
        }
        this.subintervalLength = subintervalLength;

        this.parameters = parameters;
    }

    public CompositeQuadrature(DoubleFunction<Double> f,
                               double                 a,
                               double                 b,
                               int                    nSubintervals,
                               QuadratureParameters   parameters) {
        this(f, a, b, Math.abs(b - a) / nSubintervals, parameters);
    }

    public CompositeQuadrature(DoubleFunction<Double> f,
                               double                 subintervalLength,
                               QuadratureParameters   parameters) {
        this(f, -1., 1., subintervalLength, parameters);
    }

    public CompositeQuadrature(DoubleFunction<Double> f,
                               int                    nSubintervals,
                               QuadratureParameters   parameters) {
        this(f, -1., 1., 2. / nSubintervals, parameters);
    }

    private double computePart(double start, double end) {

        double s = 0., c1 = 0.5 * (start + end), c2 = 0.5 * (end - start);

        byte nNodes = parameters.getNNodes();
        double x[] = parameters.getNodes();
        double w[] = parameters.getWeights();

        int m = nNodes / 2;
        for (int i = 0; i < m; i++) {
            s += (w[i] * (integrand.apply(c1 + c2 * x[i]) +
                integrand.apply(c1 - c2 * x[i])));
        }

        if (nNodes % 2 == 1) {
            s += (w[m] * integrand.apply(c1));
        }

        return c2 * s;
    }

    @Override
    protected Double compute() {

        if (Math.abs(b - a) <= subintervalLength) {
            return computePart(a, b);
        } else {
            double m = 0.5 * (a + b);
            CompositeQuadrature left =
                new CompositeQuadrature(integrand, a, m, subintervalLength, parameters);
            left.fork();
            CompositeQuadrature right =
                new CompositeQuadrature(integrand, m, b, subintervalLength, parameters);
            return right.compute() + left.join();
        }
    }
}
