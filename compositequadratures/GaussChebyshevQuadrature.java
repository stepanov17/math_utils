package compositequadratures;


import java.util.concurrent.RecursiveTask;
import java.util.function.DoubleFunction;

public class GaussChebyshevQuadrature extends RecursiveTask<Double> {

    // 1st kind: integrand is f(x) / sqrt(1 - x^2)
    // 2nd kind: integrand is f(x) * sqrt(1 - x^2)
    private final DoubleFunction<Double> f;

    // 1st / 2nd Chebyshev kind
    private final byte kind;

    // calculate not more than N_POINTS_PART at once
    private final int N_POINTS_PART = 10;

    // start point index
    private final int start;
    // end point index
    private final int end;

    private int n = 0;


    private GaussChebyshevQuadrature(DoubleFunction<Double> f,
                                     byte                   kind,
                                     int                    start,
                                     int                    end,
                                     int                    nPoints) {
        this.f = f;

        if ((kind != 1) && (kind != 2)) {
            throw new IllegalArgumentException("invalid Chebyshev kind: "
                + kind + ", should be 1 or 2");
        }
        this.kind = kind;

        if (start < 0) {
            // shouldn't reach this line, but just in case
            throw new IllegalArgumentException("start index must be nonnegative");
        }

        if (end < 1) {
            throw new IllegalArgumentException(
                "at least two points must be used for integration");
        }

        this.start = start;
        this.end   = end;

        if (nPoints < 2) {
            throw new IllegalArgumentException("invalid nPoints value: "
                + nPoints + ", should be >= 2");
        }
        this.n = nPoints;
    }

        public GaussChebyshevQuadrature(DoubleFunction<Double> f,
                                        byte                   kind,
                                        int                    nPoints) {
        this(f, kind, 0, nPoints, nPoints);
    }


    private double computePart(int i0, int i1) {

        double s = 0.;

        if (kind == 1) {
            double c = Math.PI / n;
            for (int i = i0; i < i1; i++) {
                s += f.apply(Math.cos((i - 0.5) * c));
            }
            s *= c;
        } else {
            double c = Math.PI / (n + 1);
            for (int i = i0; i < i1; i++) {
                double tmp = Math.sin(c * i);
                s += (c * tmp * tmp * f.apply(Math.cos(c * i)));
            }
        }

        return s;
    }

    @Override
    protected Double compute() {

        if (end - start <= N_POINTS_PART) { return computePart(start, end); } else {
            int m = start + (end - start) / 2;
            GaussChebyshevQuadrature left =
                new GaussChebyshevQuadrature(f, kind, start, m, n);
            left.fork();
            GaussChebyshevQuadrature right =
                new GaussChebyshevQuadrature(f, kind, m, end, n);
            return right.compute() + left.join();
        }
    }
}
