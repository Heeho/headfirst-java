import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.*;
import java.util.*;

abstract class Thing {    
    static int count;
    int iD;

    boolean isObstacle;
    int size = 20;
    Color color = Color.GRAY;

    int dimensions = Toroid.DIMENSIONS;
    int[] sector = new int[dimensions];
    int[] location = new int[dimensions];
    Map currentMap;

    int angle;
    boolean isMoving;
    
    static ArrayList<BufferedImage> spriteBank = new ArrayList<BufferedImage>();
    
    public Thing() {
        iD = ++count;        
    }
    
    public Thing(int[] loc) {
        this();
        location = loc;
    }
    
    public Sprite createSprite() {
        return new Thing.Sprite(iD, size, color);
    }
    
    public String className() {
        return getClass().getSimpleName();
    }
    
    public int[] distance(Thing thg) {
        int[] a = new int[dimensions];
        int secDist;
        
        for(int i = 0; i < dimensions; i++) {
            secDist = thg.sector[i] - this.sector[i];

            if(secDist >= currentMap.sectors/2) {
                secDist -= currentMap.sectors;
            } else {
                if(secDist < -currentMap.sectors/2) {
                    secDist += currentMap.sectors;
                }
            }
            
            a[i] = thg.location[i] - this.location[i] + secDist * currentMap.sectorSize;
        }
        
        return a;
    }
    
    public void setLoc(int[] p) {
        for(int i = 0; i < dimensions; i++) {
            location[i] = p[i];
        }
    }
    
    public void setSec(int[] p) {
        for(int i = 0; i < dimensions; i++) {
            sector[i] = p[i];
        }
    }

    public void offMap() {
        currentMap.map.get(sector[0]).get(sector[1]).remove(this);
    }
    //public void interact(Thing thg) {}
    
    class Sprite extends JLabel {
        String className;
        
        int picSize;
        int iD;
        Color bgColor;
        
        BufferedImage spriteSheet;                    
        BufferedImage spriteImage;

        int currentAnimationFrame = 0;
        int lastFrame = 2;
        int frameIncrement = 1;
        
        int animationDelay = 2;
        int animationCountdown;
        
        public Sprite(int iD, int size, Color color) {
            this.iD = iD;
            picSize = size*2;          
            bgColor = color;           
            className = className();   
            boolean isInBank = false;
            
            try {
                spriteSheet = ImageIO.read(new File("res/" + className + ".png"));
                for(BufferedImage img: spriteBank) {
                    if(img.equals(spriteSheet)) {
                        spriteSheet = img;
                        spriteImage = spriteSheet.getSubimage(0, 0, picSize, picSize);
                        return;
                    }
                }
                
                spriteBank.add(spriteSheet);
                spriteImage = spriteSheet.getSubimage(0, 0, picSize, picSize);
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
            
        public void animate() {
            if(isMoving) {
                if(animationCountdown == animationDelay) {
                    spriteImage = spriteSheet.getSubimage(currentAnimationFrame*picSize, angle*picSize, picSize, picSize);
                    
                    if(isMoving) {
                        currentAnimationFrame += frameIncrement;
                    } else {
                        currentAnimationFrame = 1;
                    }
                    
                    animationCountdown = 0;
                    
                    if(currentAnimationFrame == lastFrame) {
                        frameIncrement = -1;
                    } else {
                        if(currentAnimationFrame == 0) {
                            frameIncrement = 1;
                        }
                    }               
                } else {
                    animationCountdown++;
                }                        
            } else {
                currentAnimationFrame = 1;
            }
        }
        
        public void paintComponent(Graphics g) {
            g.drawImage(spriteImage, 0, 0, null);
            this.getToolkit().sync();
        }
    }    
}
