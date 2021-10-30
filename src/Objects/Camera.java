package Objects;
import org.ejml.simple.*;

/**
 * 3D camera
 */
public class Camera {
    
    private ThreeDPoint origin;
    private ThreeDPoint centerOfAttention;
    private double angleOfView;
    private double near;
    private double far;

    //TODO WARNING THIS BREAKS IF THE CAMERA IS POINTING DIRECTLY UP OR DIRECTLY DOWN -- NOT FIXED
    //TODO CLIPPING
    //TODO MOVING CAMERA
    //TODO WORKING ROTATIONS AROUND ARBITRARY AXIS
    ///TODO REMOVE ALL REFERENCES TO 2D STUFF
    //TODO INTERPOLATION
    
    /**
     * Returns the matrix to move the camera to the origin with the correct orientation
     * @return SimpleMatrix for transformation
     */
    public SimpleMatrix originTransform() {
    
        //get direction of centerOfAttention
        SimpleMatrix direction = centerOfAttention.getCoords().minus(origin.getCoords());
        direction = direction.cols(0, 3);
        // direction.print();

        //declare matrices
        SimpleMatrix up = new SimpleMatrix(
            new double[][] {
                new double[] {0, 1, 0}
            }
        );

        SimpleMatrix newUp = new SimpleMatrix(
            new double[][] {
                new double[] {direction.get(1)*up.get(2) - direction.get(2)*up.get(1),
                              direction.get(2)*up.get(0) - direction.get(0)*up.get(2),
                              direction.get(0)*up.get(1) - direction.get(1)*up.get(0)},
            }
        );

        newUp = new SimpleMatrix(
            new double[][] {
                new double[] {newUp.get(1)*direction.get(2) - newUp.get(2)*direction.get(1),
                              newUp.get(2)*direction.get(0) - newUp.get(0)*direction.get(2),
                              newUp.get(0)*direction.get(1) - newUp.get(1)*direction.get(0)},
            }
        );

        // SimpleMatrix negZDirection = new SimpleMatrix(
        //     new double[][] {
        //         new double[] {0, 0, -1}
        //     }
        // );

        // SimpleMatrix posYDirection = new SimpleMatrix(
        //     new double[][] {
        //         new double[] {0, 1, 0}
        //     }
        // );

        //start transform matrix
        SimpleMatrix transform = ThreeDPoint.getTranslateMatrix(origin.getX()*-1, origin.getY()*-1, origin.getZ()*-1);

        //+Z
        if(direction.get(2) >= 0){
            double thetaY = -Math.atan(direction.get(0) / direction.get(2));
            // System.out.println("ThetaY: " + thetaY);
            transform = transform.mult(ThreeDPoint.getRotationYAxisMatrix(Math.PI+thetaY));
            
            
        }
        //-Z
        else {
            double thetaY = -Math.atan(direction.get(0) / direction.get(2));
            // System.out.println("ThetaY: " + thetaY);
            transform = transform.mult(ThreeDPoint.getRotationYAxisMatrix(thetaY));

            
        }

        double phiX = Math.atan(direction.get(1) / Math.sqrt(Math.pow(direction.get(0), 2) + Math.pow(direction.get(2), 2)));
        transform = transform.mult(ThreeDPoint.getRotationXAxisMatrix(-phiX));

        return transform;
        
        //TESTS SHIT

        // direction = new SimpleMatrix(
        //     new double[][] {
        //         new double[] {direction.get(0, 0), direction.get(0, 1), direction.get(0, 2), 1}
        //     }
        // );
        // newUp = new SimpleMatrix(
        //     new double[][] {
        //         new double[] {newUp.get(0, 0), newUp.get(0, 1), newUp.get(0, 2), 1}
        //     }
        // );

        // direction = direction.mult(transform);
        // newUp = newUp.mult(transform);

        // //test angle between direction and -z axis
        // direction = new SimpleMatrix(
        //     new double[][] {
        //         new double[] {direction.get(0, 0), direction.get(0, 1), direction.get(0, 2), 1}
        //     }
        // );

        // direction = direction.mult(transform);
        // // direction.print();
    
        // double directionMag = Math.sqrt(Math.pow(direction.get(0), 2) + Math.pow(direction.get(1), 2) + Math.pow(direction.get(2), 2));
        // double negZMag = Math.sqrt(Math.pow(negZDirection.get(0), 2) + Math.pow(negZDirection.get(1), 2) + Math.pow(negZDirection.get(2), 2));

        // direction = direction.cols(0,3);

        // double zAngle = Math.acos(direction.dot(negZDirection) / (directionMag*negZMag));
        // // System.out.println(zAngle);


        // //test angle between newUp and +y axis
        // newUp = new SimpleMatrix(
        //     new double[][] {
        //         new double[] {newUp.get(0, 0), newUp.get(0, 1), newUp.get(0, 2), 1}
        //     }
        // );

        // // newUp.print();
        // newUp = newUp.mult(transform);
        // // newUp.print();

        // double upMag = Math.sqrt(Math.pow(newUp.get(0), 2) + Math.pow(newUp.get(1), 2) + Math.pow(newUp.get(2), 2));
        // double posYMag = Math.sqrt(Math.pow(posYDirection.get(0), 2) + Math.pow(posYDirection.get(1), 2) + Math.pow(posYDirection.get(2), 2));

        // newUp = newUp.cols(0,3);

        // double yAngle = Math.acos(newUp.dot(posYDirection) / (upMag*posYMag));
        // // System.out.println(yAngle);

        // System.out.println(zAngle);
        // System.out.println(yAngle);
        // System.out.println();
    }
    
    /**
     * Returns matrix for the viewing transform
     * @return SimpleMatrix viewing transform
     */
    public SimpleMatrix viewingTransform() {
        return new SimpleMatrix(
            new double[][] {
                new double[] {1/Math.tan(angleOfView/2), 0, 0, 0},
                new double[] {0, 1/Math.tan(angleOfView/2), 0, 0},
                new double[] {0, 0, (far+near)/(far-near), -1},
                new double[] {0, 0, (2*far*near)/(far-near), 0}
            }
        );
    }
    
    /**
     * Calculates OriginTransform * ViewingTransform
     * @return SimpleMatrix cameraTransform
     */
    public SimpleMatrix cameraTransform() {
        return originTransform().mult(viewingTransform());
    }

    /**
     * Converts a 3D point into a 2D perspective point
     * @param point ThreeDPoint to transform
     * @return Point with correct perspective
     */
    public Point ThreeDToTwoD(ThreeDPoint point) {

        SimpleMatrix ThreeDCoords = point.getCoords();
        ThreeDCoords = ThreeDCoords.mult(cameraTransform());
        ThreeDCoords = ThreeDCoords.divide(ThreeDCoords.get(3));

        SimpleMatrix TwoDCoords = new SimpleMatrix(
            new double[][] {
                new double[] {(ThreeDCoords.get(0)+1)*250, (ThreeDCoords.get(1)+1)*250, 1}
            }
        );

        Point output = new Point(TwoDCoords);

        return output;
    }

    /**
     * Constructor
     * @param origin ThreeDPoint origin
     * @param centerOfAttention ThreeDPoint where camera is looking
     * @param angleOfView double camera angle
     * @param near double Z-coord for near (should be negative)
     * @param far double Z-coord for far (should be negative)
     */
    public Camera(ThreeDPoint origin, ThreeDPoint centerOfAttention, double angleOfView, double near, double far) {
        this.origin = origin;
        this.centerOfAttention = centerOfAttention;
        this.angleOfView = angleOfView;
        this.near = near;
        this.far = far;
    }   

    public double getAngle() {
        return angleOfView;
    }

    public void setAngle(double angle) {
        angleOfView = angle;
    }

    public ThreeDPoint getCenter() {
        return centerOfAttention;
    }

    public void setCenter(ThreeDPoint center) {
        centerOfAttention = center;
    }

    public ThreeDPoint getOrigin() {
        return origin;
    }

    public void setOrigin(ThreeDPoint origin) {
        this.origin = origin;
    }
}
