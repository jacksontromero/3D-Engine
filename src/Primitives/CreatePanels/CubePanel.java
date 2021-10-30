package Primitives.CreatePanels;

import Screen.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.text.*;

import Primitives.*;
import Objects.*;

public class CubePanel {

    public static void CreateCubePanel(DemoPanel drawPanel, Camera cam) {


        //declare all buttons and fields
        JButton CreateCubeButton = new JButton("Create Cube:");
        JButton CancelButton = new JButton("Cancel");

        JFormattedTextField CubeXField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        CubeXField.setColumns(4);
        JFormattedTextField CubeYField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        CubeYField.setColumns(4);
        JFormattedTextField CubeZField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        CubeZField.setColumns(4);
        JFormattedTextField CubeSizeField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        CubeSizeField.setColumns(4);


        //create JFrame
        JFrame CubeFrame = new JFrame("Cube Creator");
        CubeFrame.setResizable(false);
        CubeFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );


        //add Cube panel
        JPanel addCube = new JPanel();

        //popup menu listener
        addCube.add(CreateCubeButton);
        CreateCubeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Long xVal = (Long)CubeXField.getValue();
                Long yVal = (Long)CubeYField.getValue();
                Long zVal = (Long)CubeZField.getValue();

                Long sizeVal = (Long)CubeSizeField.getValue();


                if(xVal == null || yVal == null || zVal == null || sizeVal == null) {
                    System.out.println("FIELDS EMPTY, CANNOT MAKE CUBE");
                }
                else {
                    int originX = xVal.intValue();
                    int originY = yVal.intValue();
                    int originZ = zVal.intValue();
                    int size = sizeVal.intValue();

                    Cube test = new Cube(originX, originY, originZ, size, cam);
                
                    drawPanel.addObject(test);
                    CubeFrame.dispose();
                }
            }
        });

        
        //CancelButton listener
        addCube.add(CancelButton);
        CancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CubeFrame.dispose();
            }
        });



        //Cube Origin panel
        JPanel CubeOriginPanel = new JPanel();
        CubeOriginPanel.add(new JLabel("Origin X: "));
        CubeXField.setColumns(4);
        CubeOriginPanel.add(CubeXField);

        CubeOriginPanel.add(new JLabel("Origin Y: "));
        CubeYField.setColumns(4);
        CubeOriginPanel.add(CubeYField);

        CubeOriginPanel.add(new JLabel("Origin Z: "));
        CubeZField.setColumns(4);
        CubeOriginPanel.add(CubeZField);

        addCube.add(CubeOriginPanel);

        //Cube size panel
        JPanel CubeSizePanel = new JPanel();
        CubeSizePanel.add(new JLabel("Side Length: "));
        CubeSizeField.setColumns((4));
        CubeSizePanel.add(CubeSizeField);

        addCube.add(CubeSizePanel);


        //render frame
        CubeFrame.getContentPane().add(addCube);
        CubeFrame.pack();
        CubeFrame.setVisible( true );
    }
    
}
