import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

abstract class Thing {    
    static int count;
    int iD;

    boolean isObstacle;
    int size = 20;
    Color color = Color.GRAY;

    int dimensions = Toroid.DIMENSIONS;
    int[] sector = new int[dimensions];
    int[] location = new int[dimensions];

    int angle;
    boolean isMoving;
    
    public Thing() {
        iD = ++count;        
    }
    
    public Sprite createSprite() {
        return new Thing.Sprite(iD, size, color);
    }
    
    public String className() {
        return getClass().getSimpleName();
    }
    
    class Sprite extends JLabel {
        String className;
        
        int picSize;
        int iD;
        Color bgColor;
        
        BufferedImage spriteSheet;                    
        BufferedImage spriteImage;
        
        //int[] sector = new int[dimensions];
        //int[] location = new int[dimensions];

        
        int currentAnimationFrame = 0;
        int lastFrame = 2;
        int frameIncrement = 1;
        
        int animationDelay = 2;
        int animationCountdown;
        
        public Sprite(int iD, int size, Color color) {
            this.iD = iD;
            picSize = size*2;          
            bgColor = color;
            //this.location = location;
            //this.sector = sector;
            
            className = className();
            //System.out.println(className());
            
            try {
                spriteSheet = ImageIO.read(new File("res/" + className + ".png"));
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            
            spriteImage = spriteSheet.getSubimage(0, 0, picSize, picSize);
        }
            
        public void animate() {
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
        }
        
        public void paintComponent(Graphics g) {
            //             Graphics2D g2d = (Graphics2D) g;
            //             g2d.setColor(color);
            //             g2d.fillRect(size/2, size, size, size);
            g.drawImage(spriteImage, 0, 0, null);
            this.getToolkit().sync();
        }
    }    
}
