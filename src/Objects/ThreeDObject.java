package Objects;

import java.util.*;
import org.ejml.simple.*;

/**
 * Parent class of all 3D objects
 */
public class ThreeDObject implements Draw3DInterface{
    private ThreeDPoint origin;

    private ArrayList<ThreeDPoint> points = new ArrayList<ThreeDPoint>();
    private ArrayList<ThreeDEdge> edges = new ArrayList<ThreeDEdge>();
    private int num;

    private TwoDObject twoD;
    private Camera cam;


    /**
     * Constructor
     * @param x
     * @param y
     * @param z
     * @param num int for # of this type of object
     * @param cam Camera this object is in relation to
     */
    public ThreeDObject(double x, double y, double z, int num, Camera cam) {
        origin = new ThreeDPoint(x,y,z);
        this.num = num;
        this.cam = cam;
    }

    /**
     * Adds a ThreeDPoint to the object
     * @param p ThreeDPoint to add
     * @return ThreeDPoint added
     */
    public ThreeDPoint addPoint(ThreeDPoint p) {
        points.add(p);
        return p;
    }

    /**
     * Returns all ThreeDPoints
     * @return ArrayList of ThreeDPoints
     */
    public ArrayList<ThreeDPoint> get3DPoints() {
        return points;
    }

    /**
     * Adds a ThreeDEdge to the object
     * @param e ThreeDEdge to add
     * @return ThreeDEdge added
     */
    public ThreeDEdge addEdge(ThreeDEdge e) {
        edges.add(e);
        return e;
    }

    /**
     * Returns all ThreeDEdges
     * @return ArrayList of ThreeDEdges
     */
    public ArrayList<ThreeDEdge> get3DEdges() {
        return edges;
    }

    /**
     * Returns 3D origin point of the object
     * @return 3D origin point 
     */
    public ThreeDPoint get3DOrigin() {
        return origin;
    }

    /**
     * To be overridden for each object, updates it
     */
    public void update() {}

    /**
     * Returns this object's id num
     * @return int num
     */
    public int getNum() {
        return num;
    }

    /**
     * Translates an object
     * @param params an Object[] of params in the order (double x, double y, double z)
     */
    public void translate(Object[] params) {
        translate((double)params[0], (double)params[1], (double)params[2]);
    }
    
    /**
     * Moves an object around an origin point
     * @param x x pixels
     * @param y y pixels
     * @param z z pixels
     */
    public void translate(double x, double y, double z) {
        get3DOrigin().transform(ThreeDPoint.getTranslateMatrix(x, y, z));
    }

    /**
     * Scales an object
     * @param params and Object[] of params in the order (ThreeDPoint scaleOrigin, double scale)
     */
    public void scale(Object[] params) {
        scale((ThreeDPoint)params[0], (double)params[1]);
    }
    
    /**
     * Scales an object around an origin point
     * @param scaleOrigin ThreeDPoint to scale from
     * @param scale scale factor
     */
    public void scale(ThreeDPoint scaleOrigin, double scale) {

        //generate matrix
        SimpleMatrix totalTransform = ThreeDPoint.getTranslateMatrix(scaleOrigin.getX()*-1, scaleOrigin.getY()*-1, scaleOrigin.getZ()*-1).mult(
            ThreeDPoint.getScaleMatrix(scale)
        ).mult(
            ThreeDPoint.getTranslateMatrix(scaleOrigin.getX(), scaleOrigin.getY(), scaleOrigin.getZ())
        );

        //transfer all points to canvas space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(ThreeDPoint.transformFromOrigin(origin, points.get(i)).getCoords());
        }

        //transform origin
        origin.transform(totalTransform);

        //transform all points and place them back in origin space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).transform(totalTransform);
            points.get(i).setCoords(ThreeDPoint.transformToOriginRelative(origin, points.get(i)).getCoords());
        }
    }

    /**
     * Rotates an object
     * @param params an Object[] of params in the order (ThreeDPoint rotateOrigin, SimpleMatrix axis, double theta)  OR  (ThreeDPoint rotateOrigin, String axis, double theta)
     */
    public void rotate(Object[] params) {
        rotate((ThreeDPoint)params[0], (String)params[1], (double)(params[2]));
    }

    //rotate around arbitrary axis
    public void rotate(ThreeDPoint rotateOrigin, SimpleMatrix axis, double theta) {
        //generate matrix
        SimpleMatrix totalTransform = new SimpleMatrix(new double[][] {new double[] {}});
        
        totalTransform = ThreeDPoint.getTranslateMatrix(rotateOrigin.getX()*-1, rotateOrigin.getY()*-1, rotateOrigin.getZ()*-1);
        
        //+Z
        if(axis.get(2) > 0){
            double thetaY = -Math.atan(axis.get(0) / axis.get(2));
            totalTransform.mult(ThreeDPoint.getRotationYAxisMatrix(thetaY));

            double phiX = Math.atan(axis.get(1) / Math.sqrt(Math.pow(axis.get(0), 2) + Math.pow(axis.get(2), 2)));
            totalTransform.mult(ThreeDPoint.getRotationXAxisMatrix(phiX));

            totalTransform.mult(ThreeDPoint.getRotationZAxisMatrix(theta));

            //UNDO
            totalTransform.mult(ThreeDPoint.getRotationXAxisMatrix(-phiX));
            totalTransform.mult(ThreeDPoint.getRotationZAxisMatrix(-thetaY));
        }
        //-Z
        else {
            double thetaY = Math.atan(axis.get(0) / axis.get(2));
            totalTransform.mult(ThreeDPoint.getRotationYAxisMatrix(thetaY));

            double phiX = -Math.atan(axis.get(1) / Math.sqrt(Math.pow(axis.get(0), 2) + Math.pow(axis.get(2), 2)));
            totalTransform.mult(ThreeDPoint.getRotationXAxisMatrix(phiX));

            totalTransform.mult(ThreeDPoint.getRotationZAxisMatrix(theta));

            //UNDO
            totalTransform.mult(ThreeDPoint.getRotationXAxisMatrix(-phiX));
            totalTransform.mult(ThreeDPoint.getRotationZAxisMatrix(-thetaY));
        }
    
        totalTransform.mult(ThreeDPoint.getTranslateMatrix(rotateOrigin.getX(), rotateOrigin.getY(), rotateOrigin.getZ()));

        //transfer all points to canvas space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(ThreeDPoint.transformFromOrigin(origin, points.get(i)).getCoords());
        }

        //transform origin
        origin.transform(totalTransform);

        //transform all points and place them back in origin space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).transform(totalTransform);
            points.get(i).setCoords(ThreeDPoint.transformToOriginRelative(origin, points.get(i)).getCoords());
        }
    }

    /**
     * Rotates an object around an origin point
     * @param rotateOrigin Point to rotate from
     * @param axis String x, y, or z that represents the axis
     * @param theta angle to rotate
     */
    public void rotate(ThreeDPoint rotateOrigin, String axis, double theta) {
        //generate matrix
        SimpleMatrix totalTransform = new SimpleMatrix(new double[][] {new double[] {}});
        if(axis.toLowerCase().equals("x")) {
            totalTransform = ThreeDPoint.getTranslateMatrix(rotateOrigin.getX()*-1, rotateOrigin.getY()*-1, rotateOrigin.getZ()*-1).mult(
                ThreeDPoint.getRotationXAxisMatrix(theta)
            ).mult(
                ThreeDPoint.getTranslateMatrix(rotateOrigin.getX(), rotateOrigin.getY(), rotateOrigin.getZ())
            );
        }
        else if(axis.toLowerCase().equals("y")) {
            totalTransform = ThreeDPoint.getTranslateMatrix(rotateOrigin.getX()*-1, rotateOrigin.getY()*-1, rotateOrigin.getZ()*-1).mult(
                ThreeDPoint.getRotationYAxisMatrix(theta)
            ).mult(
                ThreeDPoint.getTranslateMatrix(rotateOrigin.getX(), rotateOrigin.getY(), rotateOrigin.getZ())
            );
        }
        else if(axis.toLowerCase().equals("z")) {
            totalTransform = ThreeDPoint.getTranslateMatrix(rotateOrigin.getX()*-1, rotateOrigin.getY()*-1, rotateOrigin.getZ()*-1).mult(
                ThreeDPoint.getRotationZAxisMatrix(theta)
            ).mult(
                ThreeDPoint.getTranslateMatrix(rotateOrigin.getX(), rotateOrigin.getY(), rotateOrigin.getZ())
            );
        }
        else {
            System.out.println("INVALID AXIS");
        }
        

        //transfer all points to canvas space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(ThreeDPoint.transformFromOrigin(origin, points.get(i)).getCoords());
        }

        //transform origin
        origin.transform(totalTransform);

        //transform all points and place them back in origin space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).transform(totalTransform);
            points.get(i).setCoords(ThreeDPoint.transformToOriginRelative(origin, points.get(i)).getCoords());
        }
    }

    /**
     * Moves an object's origin point without moving any of the other points in the object
     * @param transformMatrix transformation to apply to the origin
     */
    public void panBehind(SimpleMatrix transformMatrix) {
        //transfer all points to canvas space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(ThreeDPoint.transformFromOrigin(origin, points.get(i)).getCoords());
        }

        //transform origin
        origin.transform(transformMatrix);

        //Place all points back in origin space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(ThreeDPoint.transformToOriginRelative(origin, points.get(i)).getCoords());
        }
    }

    /**
     * Updates this 3D object's 2D projection
     */
    public void updateTwoD() {

        TwoDObject TwoDO = new TwoDObject(get3DOrigin().getX(), get3DOrigin().getY(), num);
        Point twoDOrigin = cam.ThreeDToTwoD(origin);

        for(int i = 0; i<get3DEdges().size(); i++) {
            ThreeDPoint a = ThreeDPoint.transformFromOrigin(get3DOrigin(), get3DEdges().get(i).getPointA());
            ThreeDPoint b = ThreeDPoint.transformFromOrigin(get3DOrigin(), get3DEdges().get(i).getPointB());

            Point twoDA = cam.ThreeDToTwoD(a);
            Point twoDB = cam.ThreeDToTwoD(b);

            twoDA = Point.transformToOriginRelative(twoDOrigin, twoDA);
            twoDB = Point.transformToOriginRelative(twoDOrigin, twoDB);

            TwoDO.addEdge(new Edge(twoDA, twoDB));
        }

        for(int i = 0; i<get3DPoints().size(); i++) {
            ThreeDPoint a = ThreeDPoint.transformFromOrigin(get3DOrigin(), get3DPoints().get(i));

            Point twoD = cam.ThreeDToTwoD(a);
            twoD = Point.transformToOriginRelative(twoDOrigin, twoD);

            TwoDO.addPoint(twoD);
        }

        this.twoD = TwoDO;
    }

    /**
     * @return TwoDObject representation of this ThreeDObject
     */
    public TwoDObject getTwoDObject() {
        return this.twoD;
    }

    /**
     * @return ArrayList<Edge> the Edges of the 2D projection
     */
    public ArrayList<Edge> getEdges() {
        updateTwoD();
        
        return twoD.getEdges();
    }

    /**
     * @return ArrayList<Point> the Points of the 2D projection
     */
    public ArrayList<Point> getPoints() {
        updateTwoD();

        return twoD.getPoints();
    }

    /**
     * @return Point the origin point of the 2D projection
     */
    public Point getOrigin() {
        updateTwoD();

        return twoD.getOrigin();
    }

    public Camera getCam() {
        return cam;
    }
}
