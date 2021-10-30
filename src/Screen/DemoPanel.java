package Screen;
import java.util.*;
import javax.swing.*;

import java.awt.*;

import Objects.*;
import Objects.Point;

/**
 * Drawing panel class
 */
public class DemoPanel extends JPanel {

    public static boolean drawOrigins = false;
    public static boolean play = false;

    private ArrayList<AllObjectsInterface> objects = new ArrayList<AllObjectsInterface>();

    /**
     * Constructor for panel
     */
    public DemoPanel() {
        setPreferredSize( new Dimension(750, 750) );
        setBackground(Color.BLACK);    
    } 

    /**
     * Adds an object to the panel
     * @param d DrawObject
     */
    public void addObject(AllObjectsInterface d) {
        objects.add(d);
    }

    /**
     * Updates all objects in the panel
     */
    public void updateObjects() {
        for(int i = 0; i<objects.size(); i++) {
            objects.get(i).update();
        }
    }

    public ArrayList<AllObjectsInterface> getObjects() {
        return objects;
    }


    /**
     * Draws objects on the screen
     */
    public void paintComponent ( Graphics gr ) { 
        // int width  = getWidth();
        // int height = getHeight();
        super.paintComponent( gr );

        Graphics2D g = (Graphics2D)gr;
        g.scale(1, -1);
        g.translate(0, -getHeight());

        for(int i = 0; i<objects.size(); i++) {
            AllObjectsInterface currentObject = objects.get(i);
            ArrayList<Edge> edges = currentObject.getEdges();
            g.setColor(Color.WHITE);


            int size = 10;

            for(int j = 0; j<edges.size(); j++) {

                Edge edge = edges.get(j);
                Point a = Point.transformFromOrigin(currentObject.getOrigin(), edge.getPointA());
                Point b = Point.transformFromOrigin(currentObject.getOrigin(), edge.getPointB());

                g.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());


                g.drawRect((int)a.getX()-size/2, (int)a.getY()-size/2, size, size);
                g.drawRect((int)b.getX()-size/2, (int)b.getY()-size/2, size, size);
            }

            if(drawOrigins) {

                g.setColor(Color.ORANGE);
                Point o = currentObject.getOrigin();
                gr.drawRect((int)o.getX()-size/2, (int)o.getY()-size/2, size, size);
            }
        }

    }
}
