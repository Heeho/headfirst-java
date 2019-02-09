import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Player extends JLabel {
    JFrame f;
	JLayeredPane screen;
	
    Point pLoc;
    Point pDest;
    
    int picSize = 20;  
    Color color = Color.BLACK;
    
    int speed = 1;
    int viewRange = GameScratch.VIEW_SIZE / 2;
    
    public Player() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               gui();
            }
        });
                
        pLoc = Map.randLoc(GameScratch.MAP_XY);
        pDest = new Point(pLoc);

        Walker w = new Walker(viewRange, pLoc, pDest);
        
        Timer t1 = new Timer();
        TimerTask walker = new TimerTask() {
            public void run() {
                w.walk();
//                SwingUtilities.invokeLater(new Runnable() {
//                   public void run() {
                        for(Thing thg: w.addList) {
                            screen.add(thg, 5);
                        }

                        for(Thing thg: w.remList) {
                            screen.remove(thg);
                        }                        
//                    }
//                });
            }
        };
        t1.scheduleAtFixedRate(walker, 0L, speed);

        
    }

    //So this method is the player character's vision of this sophisticated toroid world.
    //Show us what you see dude!
    public void gui() {
		f = new JFrame("ScrollGame");
		f.setSize(2*viewRange, 2*viewRange);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.setResizable(false);

		screen = new JLayeredPane();
		screen.setLayout(null);
		screen.setOpaque(true);
		screen.setBackground(Color.decode("#defcec"));
		screen.setPreferredSize(f.getSize());
		
		screen.addMouseListener(new PlayerControl());
	
		int pLocOnScreen = viewRange - picSize/2;

		this.setSize(screen.getPreferredSize());
		this.setOpaque(false);
		this.setLocation(pLocOnScreen, pLocOnScreen);
		screen.add(this, JLayeredPane.DEFAULT_LAYER);

		f.getContentPane().add(screen, BorderLayout.CENTER);

		f.setVisible(true);
	}//gui
	
    class PlayerControl extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent event) {    
            if(SwingUtilities.isRightMouseButton(event)) {
                pDest.setLocation(new Point(event.getPoint()));
            }
        }
    }//inner
    
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);
        g2d.fillRect(0, 0, picSize, picSize);
    }
}
  



	
	