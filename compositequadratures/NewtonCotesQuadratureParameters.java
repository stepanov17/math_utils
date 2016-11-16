package compositequadratures;


public class NewtonCotesQuadratureParameters implements QuadratureParameters {

    private final static byte MAX_NODES = 8;
    private final byte nNodes;

    // Newton-Cotes quadrature nodes
    private static final double NODES[][] = new double[][] {
        {-1., Double.NaN, Double.NaN, Double.NaN},
        {-1., Double.NaN, Double.NaN, Double.NaN},
        {-1., -1./3,      Double.NaN, Double.NaN},
        {-1., -1./2,      Double.NaN, Double.NaN},
        {-1., -3./5,      -1./5,      Double.NaN},
        {-1., -2./3,      -1./3,      Double.NaN},
        {-1., -5./7,      -3./7,      -1./7     }
    };

    // Newton-Cotes quadrature weights
    private static final double WEIGHTS[][] = new double[][] {
        {1.,         Double.NaN,   Double.NaN,  Double.NaN },
        {1.  /3,     4.    /3,     Double.NaN,  Double.NaN },
        {1.  /4,     3.    /4,     Double.NaN,  Double.NaN },
        {7.  /45,    32.   /45,    4.  /15,     Double.NaN },
        {19. /144,   25.   /48,    25. /72,     Double.NaN },
        {41. /420,   18.   /35,    9.  /140,    272.  /420 },
        {751./8640,  3577. /8640,  49. /320,    2989. /8640}
    };


    public NewtonCotesQuadratureParameters(byte nNodes) {

        if ((nNodes < 2) || (nNodes > MAX_NODES)) {
            throw new IllegalArgumentException("invalid number of nodes: " + 
                nNodes + ", must be in range [2 .. " + MAX_NODES + "]");
        }
        this.nNodes = nNodes;
    }

    @Override
    public byte getNNodes() { return nNodes; }

    @Override
    public double[] getNodes() { return NODES[nNodes - 2]; }

    @Override
    public double[] getWeights() { return WEIGHTS[nNodes - 2]; }
}
