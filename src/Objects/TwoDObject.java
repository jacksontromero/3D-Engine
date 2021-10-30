package Objects;

import java.util.*;
import org.ejml.simple.*;

/**
 * Parent class of all 2D objects
 */
public class TwoDObject implements DrawObjectInterface{
    private Point origin;

    private ArrayList<Point> points = new ArrayList<Point>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private int num;


    /**
     * Takes x and y coordinates for the object's origin
     * @param x
     * @param y
     */
    public TwoDObject(double x, double y, int num) {
        origin = new Point(x,y);
        this.num = num;
    }

    /**
     * Adds a point to the object
     * @param p Point to add
     * @return Point added
     */
    public Point addPoint(Point p) {
        points.add(p);
        return p;
    }

    /**
     * Returns all points
     * @return ArrayList of points
     */
    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     * Adds an edge to the object
     * @param e Edge to add
     * @return Edge added
     */
    public Edge addEdge(Edge e) {
        edges.add(e);
        return e;
    }

    /**
     * Returns all edges
     * @return ArrayList of edges
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Returns origin point of the object
     * @return origin point 
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * To be overridden for each object, updates it
     */
    public void update() {}

    public int getNum() {
        return num;
    }

    public void translate(Object[] params) {
        translate((double)params[0], (double)params[1]);
    }
    
    /**
     * Moves an object around an origin point
     * @param x x pixels
     * @param y y pixels
     */
    public void translate(double x, double y) {
        getOrigin().transform(Point.getTranslateMatrix(x, y));
    }

    public void scale(Object[] params) {
        scale((Point)params[0], (double)params[1]);
    }
    
    /**
     * Scales an object around an origin point
     * @param scaleOrigin Point to scale from
     * @param scale scale factor
     */
    public void scale(Point scaleOrigin, double scale) {

        //generate matrix
        SimpleMatrix totalTransform = Point.getTranslateMatrix(scaleOrigin.getX()*-1, scaleOrigin.getY()*-1).mult(
            Point.getScaleMatrix(scale)
        ).mult(
            Point.getTranslateMatrix(scaleOrigin.getX(), scaleOrigin.getY())
        );

        //transfer all points to canvas space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(Point.transformFromOrigin(origin, points.get(i)).getCoords());
        }

        //transform origin
        origin.transform(totalTransform);

        //transform all points and place them back in origin space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).transform(totalTransform);
            points.get(i).setCoords(Point.transformToOriginRelative(origin, points.get(i)).getCoords());
        }
    }

    public void rotate(Object[] params) {
        rotate((Point)params[0], (double)(params[1]));
    }

    /**
     * Rotates an object around an origin point
     * @param rotateOrigin Point to rotate from
     * @param theta angle to rotate
     */
    public void rotate(Point rotateOrigin, double theta) {
        //generate matrix
        SimpleMatrix totalTransform = Point.getTranslateMatrix(rotateOrigin.getX()*-1, rotateOrigin.getY()*-1).mult(
            Point.getRotationMatrix(theta)
        ).mult(
            Point.getTranslateMatrix(rotateOrigin.getX(), rotateOrigin.getY())
        );

        //transfer all points to canvas space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(Point.transformFromOrigin(origin, points.get(i)).getCoords());
        }

        //transform origin
        origin.transform(totalTransform);

        //transform all points and place them back in origin space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).transform(totalTransform);
            points.get(i).setCoords(Point.transformToOriginRelative(origin, points.get(i)).getCoords());
        }
    }

    /**
     * Moves an object's origin point without moving any of the other points in the object
     * @param transformMatrix transformation to apply to the origin
     */
    public void panBehind(SimpleMatrix transformMatrix) {
        //transfer all points to canvas space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(Point.transformFromOrigin(origin, points.get(i)).getCoords());
        }

        //transform origin
        origin.transform(transformMatrix);

        //Place all points back in origin space
        for(int i = 0; i<points.size(); i++) {
            points.get(i).setCoords(Point.transformToOriginRelative(origin, points.get(i)).getCoords());
        }
    }
}
