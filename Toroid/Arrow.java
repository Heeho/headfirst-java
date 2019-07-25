import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Arrow extends Mobile implements Proactive, Interactive {             
    Living caller;
    Living target;
    
    Timer hitCheck = new Timer();
    
    public Arrow(Living c, int[] p) {
        speed = 1;
        size = 11;
        isObstacle = false;
        
        destination = new int[dimensions];
        
        caller = c;
        currentMap = caller.currentMap;

        setSec(caller.sector);        
        setLoc(caller.location);

        for(int i = 0; i < dimensions; i++) {
            destination[i] = location[i] + p[i] - caller.viewRange;
        }        
        
        currentMap.put(this);
        
        initWalker();
        
        isMoving = true;
        
        initProactive(hitCheck);
    }

    public void action() {                
        if(isMoving) {
            for(Thing thg: this.proximity) {            
                if(
                    thg instanceof Living
                    &&
                    !thg.equals(caller)
                    &&
                    actionCondition(this, thg)
                ){
                    target = (Living) thg;
                    target.die(caller.iD);
                    walk.cancel();
                    isMoving = false;
                }
            }
        } else {
            hitCheck.cancel();
            walk.cancel();
        }
    }
    
    public void interact(Mobile thg) {
        if(
            !isMoving
            &&
            thg.equals(caller)
        ){
            isPickedUpBy(caller);
        }
    }
        
    private void isPickedUpBy(Thing caller) {
        offMap();
        //caller.arrowCount++
    }
}
    /**
     * TODO:
     * 1) add a special collision check for Livings
     * 2) add atk() method as if it had melee? 
     * 3) push atk() method up to Mobile class?
     */
    //calc destination depending on shooting skill
    
    //vanish when destination reached