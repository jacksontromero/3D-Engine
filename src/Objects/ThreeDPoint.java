package Objects;

import org.ejml.simple.*;

/**
 * ThreeDPoint class
 */
public class ThreeDPoint {

    private SimpleMatrix coords;

    /**
     * Static method to return a ThreeDPoint in canvas space given an origin ThreeDPoint and a ThreeDPoint in origin space
     * @param origin origin ThreeDPoint
     * @param p ThreeDPoint to be transformed relative to the origin
     * @return transformed ThreeDPoint
     */
    public static ThreeDPoint transformFromOrigin(ThreeDPoint origin, ThreeDPoint p) {

        SimpleMatrix output = origin.getCoords().plus(p.getCoords());
        output.set(0, 3, 1);

        return new ThreeDPoint(output);
    }

    /**
     * Static method to return a ThreeDPoint in origin space given an origin ThreeDPoint and a ThreeDPoint in canvas space
     * @param origin origin ThreeDPoint
     * @param p ThreeDPoint to be transformed into space relative to the provided origin
     * @return transformed ThreeDPoint
     */
    public static ThreeDPoint transformToOriginRelative(ThreeDPoint origin, ThreeDPoint p) {

        SimpleMatrix output = p.getCoords().minus(origin.getCoords());
        output.set(0, 3, 1);

        return new ThreeDPoint(output);
    }

    /**
     * Static method to return a SimpleMatrix for a 3D translation
     * @param x x translation
     * @param y y translation
     * @param z z translation
     * @return SimpleMatrix transformation
     */
    public static SimpleMatrix getTranslateMatrix(double x, double y, double z) {
        return new SimpleMatrix(
            new double[][] {
                new double[] {1, 0, 0, 0},
                new double[] {0, 1, 0, 0},
                new double[] {0, 0, 1, 0},
                new double[] {x, y, z, 1}
            }
        );
    }

    /**
     * Static method to return a SimpleMatrix for a 3D scale
     * @param scale scale factor
     * @return SimpleMatrix transformation
     */
    public static SimpleMatrix getScaleMatrix(double scale) {
        return new SimpleMatrix(
            new double[][] {
                new double[] {scale, 0, 0, 0},
                new double[] {0, scale, 0, 0},
                new double[] {0, 0, scale, 0},
                new double[] {0, 0, 0, 1}
            }
        );
    }

    /**
     * Static method to return a SimpleMatrix for rotation around the Z Axis
     * @param theta angle in radians to rotate
     * @return SimpleMatrix for Z rotation
     */
    public static SimpleMatrix getRotationZAxisMatrix(double theta) {
        return new SimpleMatrix(
            new double[][] {
                new double[] {Math.cos(theta), Math.sin(theta), 0, 0},
                new double[] {-Math.sin(theta), Math.cos(theta), 0, 0},
                new double[] {0, 0, 1, 0},
                new double[] {0, 0, 0, 1},
            }
        );
    }

    /**
     * Static method to return a SimpleMatrix for rotation around the X Axis
     * @param theta angle in radians to rotate
     * @return SimpleMatrix for X rotation
     */
    public static SimpleMatrix getRotationXAxisMatrix(double theta) {
        return new SimpleMatrix(
            new double[][] {
                new double[] {1, 0, 0, 0},
                new double[] {0, Math.cos(theta), Math.sin(theta), 0},
                new double[] {0, -Math.sin(theta), Math.cos(theta), 0},
                new double[] {0, 0, 0, 1},
            }
        );
    }
    
    /**
     * Static method to return a SimpleMatrix for rotation around the Y Axis
     * @param theta angle in radians to rotate
     * @return SimpleMatrix for Y rotation
     */
    public static SimpleMatrix getRotationYAxisMatrix(double theta) {
        return new SimpleMatrix(
            new double[][] {
                new double[] {Math.cos(theta), 0, -Math.sin(theta), 0},
                new double[] {0, 1, 0, 0},
                new double[] {Math.sin(theta), 0, Math.cos(theta), 0},
                new double[] {0, 0, 0, 1},
            }
        );
    }
    
    
    /**
     * Constructor
     * @param x x-coord
     * @param y y-coord
     * @param z z-coord
     */
    public ThreeDPoint(double x, double y, double z) {

        coords = new SimpleMatrix(
            new double[][] {
                new double[] {x, y, z, 1}
            }
        );
    }

    /**
     * Constructor
     * @param matrix SimpleMatrix for coordinates
     */
    public ThreeDPoint(SimpleMatrix matrix) {
        coords = matrix;
    }

    /**
     * Transforms a ThreeDPoint's coordinates by a SimpleMatrix
     * @param transformMatrix SimpleMatrix for transformation
     */
    public void transform(SimpleMatrix transformMatrix) {
        this.coords = this.coords.mult(transformMatrix);
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
     * @return z-coord
     */
    public double getZ() {
        return coords.get(0, 2);
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
     * @param z new z-coord
     */
    public void setZ(double z) {
        coords.set(0, 2, z);
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
