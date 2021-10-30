package Objects;
import java.util.*;

/**
 * Interface for 2D and 3D objects
 */
public interface AllObjectsInterface {

    public ArrayList<Point> getPoints();
    public ArrayList<Edge> getEdges();
    public Point getOrigin(); 
    
    public void update();
    public int getNum();
}
