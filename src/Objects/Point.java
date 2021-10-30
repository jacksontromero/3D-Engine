package Objects;

import org.ejml.simple.*;

/**
 * Point class
 */
public class Point {

    private SimpleMatrix coords;

    /**
     * Static method to return a point in canvas space given an origin point and a point in origin space
     * @param origin origin Point
     * @param p Point to be transformed relative to the origin
     * @return transformed Point
     */
    public static Point transformFromOrigin(Point origin, Point p) {

        SimpleMatrix output = origin.getCoords().plus(p.getCoords());
        output.set(0, 2, 1);

        return new Point(output);
    }

    /**
     * Static method to return a point in origin space given an origin point and a point in canvas space
     * @param origin origin Point
     * @param p Point to be transformed into space relative to the provided origin
     * @return transformed Point
     */
    public static Point transformToOriginRelative(Point origin, Point p) {

        SimpleMatrix output = p.getCoords().minus(origin.getCoords());
        output.set(0, 2, 1);

        return new Point(output);
    }

    /**
     * Static method to return a SimpleMatrix for a 2D translation
     * @param x x translation
     * @param y y translation
     * @return SimpleMatrix transformation
     */
    public static SimpleMatrix getTranslateMatrix(double x, double y) {
        return new SimpleMatrix(
            new double[][] {
                new double[] {1, 0, 0},
                new double[] {0, 1, 0},
                new double[] {x, y, 1}
            }
        );
    }

    /**
     * Static method to return a SimpleMatrix for a 2D scale
     * @param scale scale factor
     * @return SimpleMatrix transformation
     */
    public static SimpleMatrix getScaleMatrix(double scale) {
        return new SimpleMatrix(
            new double[][] {
                new double[] {scale, 0, 0},
                new double[] {0, scale, 0},
                new double[] {0, 0, 1}
            }
        );
    }

    public static SimpleMatrix getRotationMatrix(double theta) {
        return new SimpleMatrix(
            new double[][] {
                new double[] {Math.cos(theta), Math.sin(theta), 0},
                new double[] {-Math.sin(theta), Math.cos(theta), 0},
                new double[] {0, 0, 1}
            }
        );
    }
    
    
    /**
     * Constructor
     * @param x x-coord
     * @param y y-coord
     */
    public Point(double x, double y) {

        coords = new SimpleMatrix(
            new double[][] {
                new double[] {x, y, 1}
            }
        );
    }

    /**
     * Constructor
     * @param matrix SimpleMatrix for coordinates
     */
    public Point(SimpleMatrix matrix) {
        coords = matrix;
    }

    /**
     * Transforms a point's coordinates by a SimpleMatrix
     * @param transformMatrix SimpleMatrix for transformation
     */
    public void transform(SimpleMatrix transformMatrix) {
        coords = coords.mult(transformMatrix);
    }

    /**
     * @return x-coord
     */
    public double getX() {
        return coords.get(0, 0);
    }

    /**
     * @return y-coord
     */
    public double getY() {
        return coords.get(0, 1);
    }

    /**
     * @param x new x-coord
     */
    public void setX(double x) {
        coords.set(0, 0, x);
    }

    /**
     * @param y new y-coord
     */
    public void setY(double y) {
        coords.set(0, 1, y);
    }

    /**
     * @return SimpleMatrix of coords
     */
    public SimpleMatrix getCoords() {
        return coords;
    }

    /**
     * @param coords SimpleMatrix of coordinates
     */
    public void setCoords(SimpleMatrix coords) {
        this.coords = coords;
    }
}
