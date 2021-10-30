package Screen.ActionQueue.ActionPanels;

import javax.swing.*;
import java.awt.event.*;
import Screen.ActionQueue.*;
import Screen.*;

/**
 * Parent class for an action panel
 */
public class ActionCreatorPanel extends JPanel {
    
    public ActionCreatorPanel(DemoPanel drawPanel, PQueue<UpdateAction> queue) {

        //list of action types
        String[] actionTypes = {"Scale", "Rotate", "Translate"};

        //creates menu
        JComboBox<String> actionList = new JComboBox<String>(actionTypes);
        actionList.setSelectedIndex(0);
        this.add(new JLabel("Add new action: "));

        //listener for when an object type is selected
        actionList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String selected = (String)actionList.getSelectedItem();

                if(selected.equals("Scale")) {
                    ScalePanel.CreateScalePanel(drawPanel, queue);
                }
                else if(selected.equals("Rotate")) {
                    RotatePanel.CreateRotatePanel(drawPanel, queue);
                }
                else if(selected.equals("Translate")) {
                    TranslatePanel.CreateTranslatePanel(drawPanel, queue);
                }
            }
        });

        this.add(actionList);
    }
}
