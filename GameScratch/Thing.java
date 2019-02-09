import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

class Thing extends JLabel {
    static int count;
    int iD;
    
    Point tLoc;
    Color color = new Color(0x8fbc8f);
    int picSize;    

    public Thing(Point p) {
        iD = count++;
        tLoc = p;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);
        g2d.fillRect(0, 0, picSize, picSize);
    }
}
