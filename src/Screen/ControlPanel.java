package Screen;

import javax.swing.*;

import Primitives.CreatePanels.ObjectCreatorPanel;

import java.awt.*;
import java.awt.event.*;

import Screen.ActionQueue.*;
import Screen.ActionQueue.ActionPanels.*;
import Objects.*;

/**
 * Class for the control panel at the side of the screen
 */
public class ControlPanel extends JPanel {

    /**
     * ControlPanel constructor
     * @param drawPanel Panel the controlPanel is to the side of
     */
    public ControlPanel(DemoPanel drawPanel, PQueue<UpdateAction> queue, Camera cam) {

        //setup
        setPreferredSize( new Dimension(150, 750) );
        setBackground(Color.BLACK);    
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JCheckBox showOriginsCheckBox = new JCheckBox("Show Origins");   

        JButton PlayPauseButton = new JButton("Play ▶");
        

        //show origins button
        JPanel checkBoxes = new JPanel();
        this.add(checkBoxes);
        checkBoxes.add(showOriginsCheckBox);

        showOriginsCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                DemoPanel.drawOrigins = !DemoPanel.drawOrigins;
            }
        });

        
        //play pause button
        JPanel PlayPause = new JPanel();
        this.add(PlayPause);
        PlayPause.add(PlayPauseButton);
        PlayPauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DemoPanel.play = !DemoPanel.play;
            
                if(DemoPanel.play) {
                    PlayPauseButton.setText("Pause ||");
                }
                else {
                    PlayPauseButton.setText("Play ▶");
                }
            }
        });

        //object creator panel
        ObjectCreatorPanel constructPanel = new ObjectCreatorPanel(drawPanel, cam);
        this.add(constructPanel);

        //action creator panel
        ActionCreatorPanel actionPanel = new ActionCreatorPanel(drawPanel, queue);
        this.add(actionPanel);
    }
}
