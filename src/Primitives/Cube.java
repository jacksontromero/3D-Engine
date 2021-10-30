package Primitives;

import Objects.*;

/**
 * Primitive Cube class
 */
public class Cube extends ThreeDObject {

    public static int total = 0;

    /**
     * Constructor for a square
     * @param x origin x-coord
     * @param y origin y-coord
     * @param z origin z-coord
     * @param size side length
     * @param cam Camera
     */
    public Cube(double x, double y, double z, double size, Camera cam) {
        super(x,y,z,total,cam);

        total++;

        double radius = size/2;

        ThreeDPoint p1 = new ThreeDPoint(+radius,+radius,+radius);
        ThreeDPoint p2 = new ThreeDPoint(+radius,-radius,+radius);
        ThreeDPoint p3 = new ThreeDPoint(-radius,+radius,+radius);
        ThreeDPoint p4 = new ThreeDPoint(-radius,-radius,+radius);
        ThreeDPoint p5 = new ThreeDPoint(+radius,+radius,-radius);
        ThreeDPoint p6 = new ThreeDPoint(+radius,-radius,-radius);
        ThreeDPoint p7 = new ThreeDPoint(-radius,+radius,-radius);
        ThreeDPoint p8 = new ThreeDPoint(-radius,-radius,-radius);

        addPoint(p1);
        addPoint(p2);
        addPoint(p3);
        addPoint(p4);
        addPoint(p5);
        addPoint(p6);
        addPoint(p7);
        addPoint(p8);


        addEdge(new ThreeDEdge(p1, p2));
        addEdge(new ThreeDEdge(p3, p4));
        addEdge(new ThreeDEdge(p5, p6));
        addEdge(new ThreeDEdge(p7, p8));

        addEdge(new ThreeDEdge(p1, p3));
        addEdge(new ThreeDEdge(p2, p4));
        addEdge(new ThreeDEdge(p5, p7));
        addEdge(new ThreeDEdge(p6, p8));

        addEdge(new ThreeDEdge(p1, p5));
        addEdge(new ThreeDEdge(p2, p6));
        addEdge(new ThreeDEdge(p3, p7));
        addEdge(new ThreeDEdge(p4, p8));
    }

    

    /**
     * Updates the cube
     */
    public void update() {
        

        // rotate(get3DOrigin(), "x", .01);
        // rotate(get3DOrigin(), "y", .01);
        // translate(0, 0, 5);
    }
}
