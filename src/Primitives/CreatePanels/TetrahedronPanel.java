package Primitives.CreatePanels;

import Screen.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.text.*;

import Primitives.*;
import Objects.*;

public class TetrahedronPanel {

    public static void CreateTetrahedronPanel(DemoPanel drawPanel, Camera cam) {


        //declare all buttons and fields
        JButton CreateTetrahedronButton = new JButton("Create Tetrahedron:");
        JButton CancelButton = new JButton("Cancel");

        JFormattedTextField TetrahedronXField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        TetrahedronXField.setColumns(4);
        JFormattedTextField TetrahedronYField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        TetrahedronYField.setColumns(4);
        JFormattedTextField TetrahedronZField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        TetrahedronZField.setColumns(4);
        JFormattedTextField TetrahedronBaseSizeField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        TetrahedronBaseSizeField.setColumns(4);
        JFormattedTextField TetrahedronHeightField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        TetrahedronHeightField.setColumns(4);


        //create JFrame
        JFrame TetrahedronFrame = new JFrame("Tetrahedron Creator");
        TetrahedronFrame.setResizable(false);
        TetrahedronFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );


        //add Tetrahedron panel
        JPanel addTetrahedron = new JPanel();

        //popup menu listener
        addTetrahedron.add(CreateTetrahedronButton);
        CreateTetrahedronButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Long xVal = (Long)TetrahedronXField.getValue();
                Long yVal = (Long)TetrahedronYField.getValue();
                Long zVal = (Long)TetrahedronZField.getValue();

                Long baseSizeVal = (Long)TetrahedronBaseSizeField.getValue();
                Long heightVal = (Long)TetrahedronHeightField.getValue();


                if(xVal == null || yVal == null || zVal == null || baseSizeVal == null || heightVal == null) {
                    System.out.println("FIELDS EMPTY, CANNOT MAKE TETRAHEDRON");
                }
                else {
                    int originX = xVal.intValue();
                    int originY = yVal.intValue();
                    int originZ = zVal.intValue();
                    int baseSize = baseSizeVal.intValue();
                    int height = heightVal.intValue();


                    Tetrahedron test = new Tetrahedron(originX, originY, originZ, baseSize, height, cam);
                
                    drawPanel.addObject(test);
                    TetrahedronFrame.dispose();
                }
            }
        });

        
        //CancelButton listener
        addTetrahedron.add(CancelButton);
        CancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TetrahedronFrame.dispose();
            }
        });



        //Tetrahedron Origin panel
        JPanel TetrahedronOriginPanel = new JPanel();
        TetrahedronOriginPanel.add(new JLabel("Origin X: "));
        TetrahedronXField.setColumns(4);
        TetrahedronOriginPanel.add(TetrahedronXField);

        TetrahedronOriginPanel.add(new JLabel("Origin Y: "));
        TetrahedronYField.setColumns(4);
        TetrahedronOriginPanel.add(TetrahedronYField);

        TetrahedronOriginPanel.add(new JLabel("Origin Z: "));
        TetrahedronZField.setColumns(4);
        TetrahedronOriginPanel.add(TetrahedronZField);

        addTetrahedron.add(TetrahedronOriginPanel);

        //Tetrahedron size panel
        JPanel TetrahedronSizePanel = new JPanel();
        TetrahedronSizePanel.add(new JLabel("Side Length: "));
        TetrahedronBaseSizeField.setColumns((4));
        TetrahedronSizePanel.add(TetrahedronBaseSizeField);

        TetrahedronSizePanel.add(new JLabel("Height: "));
        TetrahedronHeightField.setColumns((4));
        TetrahedronSizePanel.add(TetrahedronHeightField);
        
        addTetrahedron.add(TetrahedronSizePanel);


        //render frame
        TetrahedronFrame.getContentPane().add(addTetrahedron);
        TetrahedronFrame.pack();
        TetrahedronFrame.setVisible( true );
    }
    
}
