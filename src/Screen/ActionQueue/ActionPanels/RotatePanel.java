package Screen.ActionQueue.ActionPanels;

import Screen.*;
import Screen.ActionQueue.*;

import javax.swing.*;
import javax.swing.text.*;

import Objects.*;


import java.awt.event.*;
import java.math.*;
import java.text.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Class for a 3D rotation action panel
 */
public class RotatePanel {
    public static void CreateRotatePanel(DemoPanel drawPanel, PQueue<UpdateAction> queue) {

        //create JFrame
        JFrame rotateFrame = new JFrame("Rotate");
        rotateFrame.setResizable(false);
        rotateFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );


        //add square panel
        JPanel rotatePanel = new JPanel();

        
        //declares all buttons and fields
        JButton RotateButton = new JButton("Rotate");
        JButton CancelButton = new JButton("Cancel");

        String[] objectNames = new String[drawPanel.getObjects().size()];
        ThreeDObject[] objects = new ThreeDObject[drawPanel.getObjects().size()];
        for(int i = 0; i<drawPanel.getObjects().size(); i++) {
            objectNames[i] = (drawPanel.getObjects().get(i).getClass().getSimpleName() + " " + drawPanel.getObjects().get(i).getNum());
            objects[i] = (ThreeDObject)drawPanel.getObjects().get(i);
        }

        ChronoUnit[] timeUnitList = ChronoUnit.values();
        String[] timeUnitListLabels = new String[timeUnitList.length];

        for(int i = 0; i<timeUnitList.length; i++) {
            timeUnitListLabels[i] = timeUnitList[i].name();
        }

        String[] axes = new String[] {"X-Axis", "Y-Axis", "Z-Axis"};

        JComboBox<String> objectList = new JComboBox<String>(objectNames);
        objectList.setSelectedIndex(0);

        NumberFormat format = DecimalFormat.getInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);

        JCheckBox interpolateCheckBox = new JCheckBox("Interpolate", false);   

        JFormattedTextField rotateAngleField = new JFormattedTextField(format);
        rotateAngleField.setColumns(4);

        JComboBox<String> axesList = new JComboBox<String>(axes);
        axesList.setSelectedIndex(0);

        JFormattedTextField rotateTimeField = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
        rotateTimeField.setColumns(4);

        JComboBox<String> rotateTimeUnitList = new JComboBox<String>(timeUnitListLabels);
        rotateTimeUnitList.setSelectedIndex(3);

        rotatePanel.add(RotateButton);
        rotatePanel.add(CancelButton);
        rotatePanel.add(interpolateCheckBox);

        rotatePanel.add(new JLabel("Rotate "));
        rotatePanel.add(objectList);
        rotatePanel.add(rotateAngleField);
        rotatePanel.add(new JLabel("degrees around the "));
        rotatePanel.add(axesList);
        rotatePanel.add(new JLabel("in "));
        rotatePanel.add(rotateTimeField);
        rotatePanel.add(rotateTimeUnitList);

        JFormattedTextField rotateDuration = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
        rotateDuration.setColumns(4);

        JComboBox<String> rotateDurationTimeUnitList = new JComboBox<String>(timeUnitListLabels);
        rotateDurationTimeUnitList.setSelectedIndex(3);

        JLabel durationLabel = new JLabel("Duration: ");

        interpolateCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(interpolateCheckBox.isSelected()) {
                    rotatePanel.add(durationLabel);
                    rotatePanel.add(rotateDuration);
                    rotatePanel.add(rotateDurationTimeUnitList);
                }
                else {
                    rotatePanel.remove(durationLabel);
                    rotatePanel.remove(rotateDuration);
                    rotatePanel.remove(rotateDurationTimeUnitList);
                }
                rotatePanel.revalidate();
                rotatePanel.repaint();
                rotateFrame.pack();
            }
        });

        RotateButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ThreeDObject chosen = objects[objectList.getSelectedIndex()];
                Double angle = Double.valueOf(((Number)rotateAngleField.getValue()).doubleValue()); 
                angle = angle*Math.PI/180;
                int chosenAxis = axesList.getSelectedIndex();
                Long rotateTime = Duration.of((long)rotateTimeField.getValue(), timeUnitList[rotateTimeUnitList.getSelectedIndex()]).toMillis(); 

                if(chosen == null || angle == null || rotateTime == null) {
                    System.out.println("FIELDS EMPTY, CANNOT ROTATE OBJECT");
                }
                else {
                    try {
                        String axis = "";
                        if(chosenAxis == 0) {
                            axis = "x";
                        }
                        else if(chosenAxis == 1) {
                            axis = "y";
                        }
                        else if(chosenAxis == 2) {
                            axis = "z";
                        }

                        if(interpolateCheckBox.isSelected()) {
                            int duration = ((Long)Duration.of((long)rotateDuration.getValue(), timeUnitList[rotateDurationTimeUnitList.getSelectedIndex()]).toMillis()).intValue();
                            int frames = duration/Window.fpsMS;

                            for(int i = 0; i<frames; i++) {
                                queue.enqueue(new UpdateAction(chosen, "rotate", new Object[] {chosen.get3DOrigin(), axis, angle/frames}, PanelUpdate.currentTime + rotateTime+i*Window.fpsMS));
                            }
                        }
                        else {
                            queue.enqueue(new UpdateAction(chosen, "rotate", new Object[] {chosen.get3DOrigin(), axis, angle}, PanelUpdate.currentTime + rotateTime));
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                rotateFrame.dispose();
            }
        }));

        //render frame
        rotateFrame.getContentPane().add(rotatePanel);
        rotateFrame.pack();
        rotateFrame.setVisible( true );




    }
}
