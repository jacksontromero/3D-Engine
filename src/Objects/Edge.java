package Objects;

/**
 * Edge class
 */
public class Edge {

    private Point a;
    private Point b;

    /**
     * Constructor
     * @param a Point a
     * @param b Point b
     */
    public Edge(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    /**
     * @return Point a
     */
    public Point getPointA() {
        return a;
    }

    /**
     * @return Point b
     */
    public Point getPointB() {
        return b;
    }

}
