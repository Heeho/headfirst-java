import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;

class Thug extends Living {       
    public Thug() {
        speed = 3;
        viewRange = 300;
        size = 16;
        color = Color.BLACK;
        isObstacle = true;
        
        initWalker();
    }
    
    public void slash() {}
    
    public void stalk() {}
}
  



    
	