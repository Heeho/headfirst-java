import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Gui {
    JFrame f;
    JLayeredPane screen = new JLayeredPane();
    
    Living caller;
                   
    CopyOnWriteArrayList<Thing.Sprite> view = new CopyOnWriteArrayList<Thing.Sprite>();   

    //int distX, distY;
    int dimensions = Toroid.DIMENSIONS;
    int[] distA = new int[dimensions];
    
    public Gui(Living caller) {        
        this.caller = caller;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               gui();
            }
        });       
    }
    
    public void gui() {
        f = new JFrame("Toroid");
        f.setSize(2*caller.viewRange, 2*caller.viewRange);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        f.setResizable(false);

        screen.setLayout(null);
        screen.setOpaque(true);
        screen.setBackground(Color.decode("#defcec"));
        screen.setPreferredSize(f.getSize());
        
        screen.addMouseListener(new PlayerControl());                   

        f.getContentPane().add(screen, BorderLayout.CENTER);
        
        f.setVisible(true);
        
        initSeer();
    }//gui
    
    public void initSeer() {
        System.out.println("GUI show() started");
        
        Timer t = new Timer();
        TimerTask seer = new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() { 
                        show();
                    }
                });  
            }
        };
        t.scheduleAtFixedRate(seer, 0L, Toroid.FPS);
    }
    
    public void clearView() {
        screen.removeAll();
        screen.validate();
        screen.repaint();
    }
    
    public void show() {        
        remSprites();
        moveSprites();
        addSprites();
        sortSprites();   
    }

    //if thg is in proximity, create sprite and add it to view. ONCE. 
    //if view has such a sprite already, don't add
    private void addSprites() {
        //System.out.println("addSprites()");
        boolean add = true;
        
        for(Thing thg: caller.proximity) {
            //System.out.println("checking proximity list");
            if(!view.isEmpty()) {
                add = true;
                
                for(Thing.Sprite s: view) {
                    if(thg.iD == s.iD) {
                        add = false;
                        break;
                        //System.out.println("add = false");
                    }
                }
            }       
        
            if(add) {
                Thing.Sprite sprite = thg.createSprite();
                
                distA = caller.distance(thg);
                
                convertDistA(sprite);
                
                sprite.setLocation(distA[0], distA[1]);   
                view.add(sprite);                            
                //System.out.println("Thing " + thg.iD + " visible, sprite is in view at " + sprite.location[0] + " " + sprite.location[1]);

                //sprite.setSize(screen.getPreferredSize());
                sprite.setSize(sprite.picSize, sprite.picSize);
                sprite.setOpaque(false);

                screen.add(sprite, new Integer(0));                      
                //                         System.out.println("Thing " + thg.iD + " sprite added to screen");
            }                
        }
    }

    //Convert distance to XOY centered coordinates on screen (X> Yv)
    private void convertDistA(Thing.Sprite sprite) {
        if(sprite.className == "Tree") {
            distA[0] += caller.viewRange - sprite.picSize/2;
            distA[1] += caller.viewRange - sprite.picSize/2;            
        } else {
            distA[0] += caller.viewRange - sprite.picSize/2;
            distA[1] += caller.viewRange - sprite.picSize/4*3;
        }
    }
    
    //Relocate visible Sprites
    private void moveSprites() {
        //System.out.println("moveSprites()");    
        
        for(Thing thg: caller.proximity) {
            distA = caller.distance(thg);
            
            outerloop:
            for(Thing.Sprite sprite: view) {
                if(sprite.iD == thg.iD) {
                    convertDistA(sprite);
                    
                    sprite.setLocation(distA[0], distA[1]);
                    
                    if(thg instanceof Mobile) {
                        sprite.animate();
                    }                    

                    break outerloop;
                }
            }
            
        }       
    }
    
    //sort sprites by Y on screen
    private void sortSprites() {  
        int position = 9999;
        
        for(Thing.Sprite sprite: view) {
            if(sprite.className != "Tree") {
                position--;
                 for(Thing.Sprite anotherSprite: view) {
                    if(sprite.getLocation().getY() + sprite.picSize < anotherSprite.getLocation().getY() + anotherSprite.picSize) {
                        position--;
                    }
                }
            }                                        

            screen.setLayer(sprite, new Integer(position));
            
            //             if(sprite.className == "Thug" || sprite.className == "Stone") {
            //                 System.out.println(sprite.className + " is put to " + position + " layer");
            //             }
            
            position = 9999;
        }
    }
    
    //Calculate Sprite locations, then add those that are out of range to remList
    private void remSprites() {
        //System.out.println("remSprites()");
        boolean remove = true;        
        
        //for each sprite if there's no such a thg that sprite.iD == thg.iD, remove it        
        for(Thing.Sprite sprite: view) {
            remove = true;
            
            outerloop:
            for(Thing thg: caller.proximity) {
                if(sprite.iD == thg.iD) {
                    remove = false;
                    break outerloop;
                }
            }
            
            if(remove) {
                view.remove(sprite);                          
                screen.remove(sprite);
                //                         System.out.println("Thing " + sprite.iD + " sprite removed from screen");
            }
        }
    }
    
    class PlayerControl extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent event) {    
            if(SwingUtilities.isRightMouseButton(event)) {
                //                 System.out.println("Screen tapped at " + event.getPoint());
                
                int[] p = {(int) event.getPoint().getX(), (int) event.getPoint().getY()};
                caller.setDestination(p);
            } 
            
            if(SwingUtilities.isLeftMouseButton(event)) {
                //                 System.out.println("Screen tapped at " + event.getPoint());
                int[] p = {(int) event.getPoint().getX(), (int) event.getPoint().getY()};
                //caller.attack();
                caller.shoot(p);
            }
        }
    }//inner    
}
