package Screen;
import javax.swing.*;

import java.util.Timer;

//import org.ejml.simple.*;

import Primitives.*;
import Objects.*;
import Screen.ActionQueue.*;

public class Window {

    public static int fpsMS = 17;
    public static void main(String[] args) throws Exception {

        //frame for the entire window
        JFrame frame = new JFrame( "Test Panel" );
        frame.setResizable(false);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        //JPanel container for all other panels
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        
        //canvas panel
        DemoPanel drawPanel = new DemoPanel();
        container.add(drawPanel);


        PQueue<UpdateAction> testQueue = new PQueue<UpdateAction>();
        Camera testCam = new Camera(new ThreeDPoint(750/2, 750/2, 0), new ThreeDPoint(750/2, 750/2, -1), Math.PI/3, -1, -1000);

        //controls panel
        ControlPanel controlPanel = new ControlPanel(drawPanel, testQueue, testCam);
        container.add(controlPanel);

        //finish frame
        frame.getContentPane().add(container);
        frame.pack();
        frame.setVisible( true );

        int width = drawPanel.getWidth();
        int height = drawPanel.getHeight();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new PanelUpdate(drawPanel, testQueue), 0, fpsMS);


        Cube test3 = new Cube(width/2, height/2-50, -800, 200, testCam); 
        drawPanel.addObject(test3);

        Tetrahedron test4 = new Tetrahedron(width/2, height/2, -800, 200, 200, testCam);
        drawPanel.addObject(test4);
    }
}

