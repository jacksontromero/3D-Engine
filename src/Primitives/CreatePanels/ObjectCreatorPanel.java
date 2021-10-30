package Primitives.CreatePanels;

import javax.swing.*;
import java.awt.event.*;
import Screen.*;
import Objects.*;

/**
 * Class for the generic object creation panel
 */
public class ObjectCreatorPanel extends JPanel {
    
    public ObjectCreatorPanel(DemoPanel drawPanel, Camera cam) {

        //list of object types
        String[] objectTypes = {"Cube", "Tetrahedron"};

        //creates menu
        JComboBox<String> objectList = new JComboBox<String>(objectTypes);
        objectList.setSelectedIndex(0);
        this.add(new JLabel("Create new object: "));

        //listener for when an object type is selected
        objectList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String selected = (String)objectList.getSelectedItem();

                if(selected.equals("Cube")) {
                    CubePanel.CreateCubePanel(drawPanel, cam);
                }
                else if(selected.equals("Tetrahedron")) {
                    TetrahedronPanel.CreateTetrahedronPanel(drawPanel, cam);
                }
            }
        });

        this.add(objectList);
    }
}
