import java.util.ArrayList;
import java.awt.Point;
import javax.swing.JLayeredPane;
import java.util.Timer;
import java.util.TimerTask;

abstract class Living extends Mobile {
    Gui gui;
    
    int viewRange;
    
    public void guiOn() {
        gui = new Gui(this);
    }
    
    public void setDestination(int[] p) {
        if(gui.screen != null) {
            for(int i = 0; i < dimensions; i++) {
                destination[i] = location[i] + p[i] - viewRange;
            }

            //             System.out.println("Destination is set to " + destination[0] + " " + destination[1]);
            //             System.out.println("Location is " + location[0] + " " + location[1] + " in sector " + sector[0] + " " + sector[1]);
        } else {        
            destination[0] = p[0];
            destination[1] = p[1];
            //             System.out.println("destination= " + destination[0] + " " + destination[1]);
        }
    }
}
