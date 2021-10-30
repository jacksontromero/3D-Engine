package Primitives;

import Objects.*;

/**
 * Primitive Tetrahedron class
 */
public class Tetrahedron extends ThreeDObject {

    public static int total = 0;

    /**
     * Constructor for a square
     * @param x origin x-coord
     * @param y origin y-coord
     * @param z origin z-coord
     * @param baseSize side length
     * @param height height length
     * @param cam Camera
     */
    public Tetrahedron(double x, double y, double z, double baseSize, double height, Camera cam) {
        super(x,y,z,total,cam);

        total++;

        double radius = baseSize/2;

        ThreeDPoint p1 = new ThreeDPoint(-radius, 0, -radius);
        ThreeDPoint p2 = new ThreeDPoint(radius, 0, -radius);
        ThreeDPoint p3 = new ThreeDPoint(radius, 0, radius);
        ThreeDPoint p4 = new ThreeDPoint(-radius, 0, radius);
        ThreeDPoint p5 = new ThreeDPoint(0, height, 0);

        addPoint(p1);
        addPoint(p2);
        addPoint(p3);
        addPoint(p4);
        addPoint(p5);

        addEdge(new ThreeDEdge(p1, p2));
        addEdge(new ThreeDEdge(p2, p3));
        addEdge(new ThreeDEdge(p3, p4));
        addEdge(new ThreeDEdge(p4, p1));

        addEdge(new ThreeDEdge(p1, p5));
        addEdge(new ThreeDEdge(p2, p5));
        addEdge(new ThreeDEdge(p3, p5));
        addEdge(new ThreeDEdge(p4, p5));


    }

    

    /**
     * Updates the tetrahedron
     */
    public void update() {
        

        // rotate(get3DOrigin(), "x", .01);
        // rotate(get3DOrigin(), "y", .01);
        // translate(0, 0, 5);
    }
}
