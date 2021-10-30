package Screen;

import java.util.*;

import Screen.ActionQueue.*;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class PanelUpdate extends TimerTask {

    private DemoPanel panel;
    private PQueue<UpdateAction> queue;

    //stuff for pausing
    public static long currentTime = 0;
    private LocalDateTime lastTime;
    private boolean playing = false;

    /**
     * Constructor
     * @param panel a DemoPanel to be updated
     * @param queue a PQueue that holds events
     */
    public PanelUpdate(DemoPanel panel, PQueue<UpdateAction> queue){
        this.panel = panel;
        this.queue = queue;
    }

    @Override
    public void run() {

        //if play button pressed
        if(DemoPanel.play) {

            //if previously paused
            if(!playing) {
                playing = true;
                this.lastTime = LocalDateTime.now();
            }

            currentTime += Duration.between(lastTime, LocalDateTime.now()).toMillis();
            this.lastTime = LocalDateTime.now();

            //update all objects
            panel.updateObjects();

            //check if there are queue actions to take place
            while(!queue.isEmpty() && queue.peek().getPriority() < currentTime) {

                UpdateAction action = queue.dequeue();

                try {

                    //run queue action
                    action.run();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //else if paused
        else {
            if(playing) {
                playing = false;
            }   
        }
        panel.repaint();
    }
}