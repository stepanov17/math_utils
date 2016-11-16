package compositequadratures;


public class GaussLobattoQuadratureParameters implements QuadratureParameters {

    private final static byte MAX_NODES = 8;
    private final byte nNodes;

    // Gauss-Lobatto quadrature nodes
    private static final double NODES[][] = new double[][] {
        {-1.,   Double.NaN,         Double.NaN,         Double.NaN      },
        {-1.,   Double.NaN,         Double.NaN,         Double.NaN      },
        {-1.,  -0.44721359549996,   Double.NaN,         Double.NaN      },
        {-1.,  -0.65465367070798,   Double.NaN,         Double.NaN      },
        {-1.,  -0.76505532392946,  -0.28523151648065,   Double.NaN      },
        {-1.,  -0.83022389627857,  -0.46884879347071,   Double.NaN      },
        {-1.,  -0.87174014850961,  -0.59170018143314,  -0.20929921790248}
    };

    // Gauss-Lobatto quadrature weights
    private static final double WEIGHTS[][] = new double[][] {
        {1.,                Double.NaN,        Double.NaN,        Double.NaN      },
        {0.33333333333333,  1.33333333333333,  Double.NaN,        Double.NaN      },
        {0.16666666666667,  0.83333333333333,  Double.NaN,        Double.NaN      },
        {0.10000000000000,  0.54444444444444,  0.71111111111111,  Double.NaN      },
        {0.06666666666667,  0.37847495629785,  0.55485837703549,  Double.NaN      },
        {0.04761904761905,  0.27682604736157,  0.43174538120986,  0.48761904761905},
        {0.03571428571429,  0.21070422714351,  0.34112269248350,  0.41245879465870}
    };


    public GaussLobattoQuadratureParameters(byte nNodes) {

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
