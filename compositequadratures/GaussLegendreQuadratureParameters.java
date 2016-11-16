package compositequadratures;


public class GaussLegendreQuadratureParameters implements QuadratureParameters {

    private final static byte MAX_NODES = 8;
    private final byte nNodes;

    // Gauss-Legendre quadrature nodes
    private static final double NODES[][] = new double[][] {
        {-0.57735026918963,  Double.NaN,        Double.NaN,        Double.NaN      },
        {-0.77459666924148,  Double.NaN,        Double.NaN,        Double.NaN      },
        {-0.86113631159405, -0.33998104358486,  Double.NaN,        Double.NaN      },
        {-0.90617984593866, -0.53846931010568,  Double.NaN,        Double.NaN      },
        {-0.93246951420315, -0.66120938646626, -0.23861918608320,  Double.NaN      },
        {-0.94910791234276, -0.74153118559939, -0.40584515137740,  Double.NaN      },
        {-0.96028985649754, -0.79666647741363, -0.52553240991633, -0.18343464249565}
    };

    // Gauss-Legendre quadrature weights
    private static final double WEIGHTS[][] = new double[][] {
        {1.,               Double.NaN,       Double.NaN,       Double.NaN      },
        {0.55555555555556, 0.88888888888889, Double.NaN,       Double.NaN      },
        {0.34785484513745, 0.65214515486255, Double.NaN,       Double.NaN      },
        {0.23692688505619, 0.47862867049937, 0.56888888888889, Double.NaN      },
        {0.17132449237917, 0.36076157304814, 0.46791393457269, Double.NaN      },
        {0.12948496616887, 0.27970539148928, 0.38183005050512, 0.41795918367347},
        {0.10122853629038, 0.22238103445337, 0.31370664587789, 0.36268378337836}
    };


    public GaussLegendreQuadratureParameters(byte nNodes) {

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
