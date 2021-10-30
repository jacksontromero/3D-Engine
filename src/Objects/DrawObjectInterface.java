package Objects;

/**
 * Interface for 2D objects
 */
public interface DrawObjectInterface extends AllObjectsInterface{

    public Point addPoint(Point p);
    public Edge addEdge(Edge e);
}