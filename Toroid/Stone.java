import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Stone extends Thing {
    public Stone() {
        isObstacle = true;
        size = 32;
        color = Color.GRAY;
    }
}
