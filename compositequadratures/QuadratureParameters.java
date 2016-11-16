package compositequadratures;

public interface QuadratureParameters {

    public byte getNNodes(); // number of nodes

    public double[] getNodes();

    public double[] getWeights();
}
