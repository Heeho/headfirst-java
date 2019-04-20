import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Arrow extends Mobile {    
    public Arrow() {
        speed = 1;
        size = 11;
        color = Color.YELLOW;
        
        initWalker();
        //destination = [mouse position, left-click];
    }
    
    /**
     * TODO:
     * 1) add a special collision check for Livings
     * 2) add atk() method as if it had melee? 
     * 3) push atk() method up to Mobile class?
     */
    //calc destination depending on shooting skill
    
    //vanish when destination reached
}