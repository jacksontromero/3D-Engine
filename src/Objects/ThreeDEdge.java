package Objects;

/**
 * ThreeDEdge class
 */
public class ThreeDEdge {

    private ThreeDPoint a;
    private ThreeDPoint b;

    /**
     * Constructor
     * @param a ThreeDPoint a
     * @param b ThreeDPoint b
     */
    public ThreeDEdge(ThreeDPoint a, ThreeDPoint b) {
        this.a = a;
        this.b = b;
    }

    /**
     * @return ThreeDPoint a
     */
    public ThreeDPoint getPointA() {
        return a;
    }

    /**
     * @return ThreeDPoint b
     */
    public ThreeDPoint getPointB() {
        return b;
    }

}
