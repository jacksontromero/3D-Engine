package Objects;
import java.util.*;

/**
 * Interface for 3D objects
 */
public interface Draw3DInterface extends AllObjectsInterface {

    public ThreeDPoint addPoint(ThreeDPoint p);
    public ThreeDEdge addEdge(ThreeDEdge e);

    public ArrayList<ThreeDPoint> get3DPoints();
    public ArrayList<ThreeDEdge> get3DEdges();
    public ThreeDPoint get3DOrigin();

    public TwoDObject getTwoDObject();
    public void updateTwoD();
}