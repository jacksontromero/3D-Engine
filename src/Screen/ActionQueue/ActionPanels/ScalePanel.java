package Screen.ActionQueue.ActionPanels;

import Screen.*;
import Screen.ActionQueue.*;

import javax.swing.*;
import javax.swing.text.*;
import java.time.*;

import Objects.*;

import java.awt.event.*;
import java.math.*;
import java.text.*;
import java.time.temporal.ChronoUnit;

/**
 * Class for a 3D scale action panel
 */
public class ScalePanel {

    public static void CreateScalePanel(DemoPanel drawPanel, PQueue<UpdateAction> queue) {

        //create JFrame
        JFrame scaleFrame = new JFrame("Scale");
        scaleFrame.setResizable(false);
        scaleFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );


        //add square panel
        JPanel scalePanel = new JPanel();

        
        //declares all buttons and fields
        JButton ScaleButton = new JButton("Scale");
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

        JCheckBox interpolateCheckBox = new JCheckBox("Interpolate", false);   

        JFormattedTextField scaleSizeField = new JFormattedTextField(format);
        scaleSizeField.setColumns(4);

        JFormattedTextField scaleTimeField = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
        scaleTimeField.setColumns(4);

        JComboBox<String> scaleTimeUnitList = new JComboBox<String>(timeUnitListLabels);
        scaleTimeUnitList.setSelectedIndex(3);

        scalePanel.add(ScaleButton);
        scalePanel.add(CancelButton);
        scalePanel.add(interpolateCheckBox);

        scalePanel.add(new JLabel("Scale "));
        scalePanel.add(objectList);
        scalePanel.add(new JLabel("by "));
        scalePanel.add(scaleSizeField);
        scalePanel.add(new JLabel("in "));
        scalePanel.add(scaleTimeField);
        scalePanel.add(scaleTimeUnitList);

        JFormattedTextField scaleDuration = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
        scaleDuration.setColumns(4);

        JComboBox<String> scaleDurationTimeUnitList = new JComboBox<String>(timeUnitListLabels);
        scaleDurationTimeUnitList.setSelectedIndex(3);

        JLabel durationLabel = new JLabel("Duration: ");

        interpolateCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(interpolateCheckBox.isSelected()) {
                    scalePanel.add(durationLabel);
                    scalePanel.add(scaleDuration);
                    scalePanel.add(scaleDurationTimeUnitList);
                }
                else {
                    scalePanel.remove(durationLabel);
                    scalePanel.remove(scaleDuration);
                    scalePanel.remove(scaleDurationTimeUnitList);
                }
                scalePanel.revalidate();
                scalePanel.repaint();
                scaleFrame.pack();
            }
        });

        ScaleButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ThreeDObject chosen = objects[objectList.getSelectedIndex()];
                Double scaleSize = Double.valueOf(((Number)scaleSizeField.getValue()).doubleValue()); 
                Long scaleTime = Duration.of((long)scaleTimeField.getValue(), timeUnitList[scaleTimeUnitList.getSelectedIndex()]).toMillis(); 

                if(chosen == null || scaleSize == null || scaleTime == null) {
                    System.out.println("FIELDS EMPTY, CANNOT SCALE OBJECT");
                }
                else {
                    try {

                        if(interpolateCheckBox.isSelected()) {
                            int duration = ((Long)Duration.of((long)scaleDuration.getValue(), timeUnitList[scaleDurationTimeUnitList.getSelectedIndex()]).toMillis()).intValue();
                            int frames = duration/Window.fpsMS;

                            for(int i = 0; i<frames; i++) {
                                queue.enqueue(new UpdateAction(chosen, "scale", new Object[] {chosen.get3DOrigin(), Math.pow(scaleSize, (1/(double)frames))}, PanelUpdate.currentTime + scaleTime+i*Window.fpsMS));
                            }
                        }
                        else {
                            queue.enqueue(new UpdateAction(chosen, "scale", new Object[] {chosen.get3DOrigin(), scaleSize}, PanelUpdate.currentTime + scaleTime));
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                scaleFrame.dispose();
            }
        }));

        //render frame
        scaleFrame.getContentPane().add(scalePanel);
        scaleFrame.pack();
        scaleFrame.setVisible( true );




    }
}
