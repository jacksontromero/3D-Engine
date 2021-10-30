package Screen.ActionQueue.ActionPanels;

import Screen.*;
import Screen.ActionQueue.*;

import javax.swing.*;
import javax.swing.text.*;

import Objects.*;

import java.awt.event.*;
import java.math.*;
import java.text.*;
import java.time.temporal.ChronoUnit;
import java.time.*;

/**
 * Class for a 3D translate action
 */
public class TranslatePanel {
    public static void CreateTranslatePanel(DemoPanel drawPanel, PQueue<UpdateAction> queue) {

        //create JFrame
        JFrame translateFrame = new JFrame("translate");
        translateFrame.setResizable(false);
        translateFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );


        //add square panel
        JPanel translatePanel = new JPanel();

        
        //declares all buttons and fields
        JButton TranslateButton = new JButton("Translate");
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

        JComboBox<String> objectList = new JComboBox<String>(objectNames);
        objectList.setSelectedIndex(0);

        NumberFormat format = DecimalFormat.getInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);

        JCheckBox relativeOriginCheckBox = new JCheckBox("Move relative to origin", true);   
        JCheckBox interpolateCheckBox = new JCheckBox("Interpolate", false);   

        JFormattedTextField translateXField = new JFormattedTextField(format);
        translateXField.setColumns(4);

        JFormattedTextField translateYField = new JFormattedTextField(format);
        translateYField.setColumns(4);

        JFormattedTextField translateZField = new JFormattedTextField(format);
        translateZField.setColumns(4);

        JFormattedTextField translateTimeField = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
        translateTimeField.setColumns(4);

        JComboBox<String> translateTimeUnitList = new JComboBox<String>(timeUnitListLabels);
        translateTimeUnitList.setSelectedIndex(3);

        translatePanel.add(TranslateButton);
        translatePanel.add(CancelButton);

        translatePanel.add(relativeOriginCheckBox);
        translatePanel.add(interpolateCheckBox);

        translatePanel.add(new JLabel("Move "));
        translatePanel.add(objectList);
        translatePanel.add(new JLabel("X: "));
        translatePanel.add(translateXField);
        translatePanel.add(new JLabel("Y: "));
        translatePanel.add(translateYField);
        translatePanel.add(new JLabel("Z: "));
        translatePanel.add(translateZField);
        translatePanel.add(new JLabel("Translate In: "));
        translatePanel.add(translateTimeField);
        translatePanel.add(translateTimeUnitList);

        JFormattedTextField translateDuration = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
        translateDuration.setColumns(4);

        JComboBox<String> translateDurationTimeUnitList = new JComboBox<String>(timeUnitListLabels);
        translateDurationTimeUnitList.setSelectedIndex(3);

        JLabel durationLabel = new JLabel("Duration: ");

        interpolateCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(interpolateCheckBox.isSelected()) {
                    translatePanel.add(durationLabel);
                    translatePanel.add(translateDuration);
                    translatePanel.add(translateDurationTimeUnitList);
                }
                else {
                    translatePanel.remove(durationLabel);
                    translatePanel.remove(translateDuration);
                    translatePanel.remove(translateDurationTimeUnitList);
                }
                translatePanel.revalidate();
                translatePanel.repaint();
                translateFrame.pack();
            }
        });

        TranslateButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ThreeDObject chosen = objects[objectList.getSelectedIndex()];
                Double X = Double.valueOf(((Number)translateXField.getValue()).doubleValue()); 
                Double Y = Double.valueOf(((Number)translateYField.getValue()).doubleValue()); 
                Double Z = Double.valueOf(((Number)translateZField.getValue()).doubleValue()); 
                
                if(!relativeOriginCheckBox.isSelected()) {
                    X-=chosen.get3DOrigin().getX();
                    Y-=chosen.get3DOrigin().getY();
                    Z-=chosen.get3DOrigin().getZ();

                }

                Long translateTime = Duration.of((long)translateTimeField.getValue(), timeUnitList[translateTimeUnitList.getSelectedIndex()]).toMillis(); 

                if(chosen == null || X == null || Y == null || translateTime == null) {
                    System.out.println("FIELDS EMPTY, CANNOT TRANSLATE OBJECT");
                }
                else {
                    try {

                        if(interpolateCheckBox.isSelected()) {
                            int duration = ((Long)Duration.of((long)translateDuration.getValue(), timeUnitList[translateDurationTimeUnitList.getSelectedIndex()]).toMillis()).intValue();
                            int frames = duration/Window.fpsMS;

                            for(int i = 0; i<frames; i++) {
                                queue.enqueue(new UpdateAction(chosen, "translate", new Object[] {X/frames, Y/frames, Z/frames}, PanelUpdate.currentTime + translateTime+i*Window.fpsMS));
                            }
                        }
                        else {
                            queue.enqueue(new UpdateAction(chosen, "translate", new Object[] {X, Y, Z}, PanelUpdate.currentTime + translateTime));
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                translateFrame.dispose();
            }
        }));

        //render frame
        translateFrame.getContentPane().add(translatePanel);
        translateFrame.pack();
        translateFrame.setVisible( true );




    }
}
