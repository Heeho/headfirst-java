import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;

class Thug extends Living implements Proactive {
    Timer interactWith = new Timer();
    
    public Thug() {
        speed = 3;
        viewRange = 600;
        attackRange = 50;
        size = 24;
        color = Color.BLACK;
        isObstacle = true;
        
        initWalker();
        initProactive(interactWith);
    }    
    
    public void stalk() {}
    
    public void action() {
        
        for(Thing thg: this.proximity) {            
            if(
                !thg.equals(this)
                &&
                thg instanceof Interactive
                &&
                actionCondition(this, thg)
            ){
                ((Interactive) thg).interact(this);
            }
        }
    }
}
  



    
	