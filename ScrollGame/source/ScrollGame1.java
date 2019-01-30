import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class ScrollGame1 {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
			}
		});
	}
}

class GUI {
	public static final int 	MAP_SIZE = 2400, 
						VIEW_SIZE = 600;

	int picSize = 20;
	private Color color = Color.BLACK;

	JFrame f;
	JLayeredPane screen;

	Player p1;

	private long speed = 200;
	Walker w;
	
	public GUI() {
		go();
	}
	
	public void go() {
		f = new JFrame("ScrollGame");
		f.setSize(VIEW_SIZE, VIEW_SIZE);
		f.setBackground(Color.decode("#defcec"));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.setResizable(false);

		screen = new JLayeredPane();
		screen.setLayout(null);
		screen.setOpaque(false);
		screen.setPreferredSize(f.getSize());	
		screen.addMouseListener(new PlayerControl());
		
		p1 = new Player();
		p1.setSize(p1.getPreferredSize());
		p1.setOpaque(false);
		screen.add(p1, 2);

		f.getContentPane().add(screen, BorderLayout.CENTER);

		w = new Walker(screen, VIEW_SIZE);
		Map.fillMap(MAP_SIZE, w.thgMap);

		f.setVisible(true);

		Timer t1 = new Timer();
		TimerTask walker = new TimerTask() {
			public void run() {
				w.walk();
			}
		};
		t1.scheduleAtFixedRate(walker, 0L, speed);
	}//go
	
	class PlayerControl extends MouseAdapter implements MouseListener {
		public void actionPerformed(ActionEvent ev) {
			Point p = new Point();
			w.pDest.setLocation(p);
		}	
	}

	//player character sprite
	class Player extends JLabel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(color);
			g2d.fillRect(VIEW_SIZE/2 - picSize/2, VIEW_SIZE/2 - picSize/2, picSize, picSize);
//			this.getToolkit().sync();		//will be handy for player sprite animation
		}
	}//inner
}

//trees for now
class Thing extends JLabel {
	static int count;
	int iD;

	private int size = 21;
	private Color color = new Color(0x8fbc8f);

	private Point tLoc;
		
	public Thing(Point p) {
		iD = count++;
		tLoc = p;
	}

	public Point getTLoc() {
		return tLoc;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(color);
		g2d.fillRect(0, 0, size, size);
	}
}

class Walker {
	private int viewRange;
	Point pLoc, pDest;

	JLayeredPane screen;
	ArrayList<Thing> thgMap;
	ArrayList<Thing> thgSet;

	int dX, dY;
	int err;
	
	public Walker(JLayeredPane s, int vR) {
		pLoc = new Point();
		pDest = new Point();

		thgMap = new ArrayList<Thing>();
		thgSet = new ArrayList<Thing>();
		viewRange = vR;
		screen = s;
	}
	
	public void walk() {
		int deltaX = (int) (pLoc.getX() - pDest.getX());
		int deltaY = (int) (pLoc.getY() - pDest.getY());
	
		if(deltaX == 0) {
			dX = 0;
		} else {
			if(deltaX < 0) {
				dX = -1;
			} else {
				dX = 1;
			}
		}
		
		if(deltaY == 0) {
			dY = 0;
		} else {
			if(deltaY < 0) {
				dY = -1;
			} else {
				dY = 1;
			}
		}
		
		deltaX = Math.abs(deltaX);
		deltaY = Math.abs(deltaY);
		
		if(deltaX != deltaY) {
			if(deltaX > deltaY) {
				dY = dZ(deltaX, deltaY, dY);
			} else {
				dX = dZ(deltaY, deltaX, dX);
			}
		}
		pLoc.translate(dX, dY);

		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				show();
			}
		});
	}

	private int dZ(int a, int b, int dz) {
		int add = 0;
		err += b;
		if(2*err >= a) {
			add = dz;
			err -= a;
		}
		return add;
	}
	
	private void show() {
		double dist;
		int x, y;

		for(Thing thg: thgMap) {
			dist = thg.getTLoc().distance(pLoc);
			if(!thgSet.contains(thg)) {
				if(dist < viewRange) {		
					System.out.println("Thing at " + thg.getTLoc().getX() + " " + thg.getTLoc().getY());
					thg.setSize(thg.getPreferredSize());
					thg.setOpaque(false);
					screen.add(thg, 5);
					thgSet.add(thg);
				}
			} else {
				if(dist < viewRange) {
					x = (int) (thg.getTLoc().getX());
					y = (int) (thg.getTLoc().getY());
					thg.setLocation(x - dX, y - dY);
					System.out.println("Thg " + thg.iD + ": " + thg.getTLoc().getX() + " " + thg.getTLoc().getY());
				} else {
					System.out.println("Thing fades at " + thg.getTLoc().getX() + " " + thg.getTLoc().getY());
					screen.remove(thg);
					thgSet.remove(thg);
				}
			}
		}//for
		screen.validate();
	}
}

class Map {
	public static void fillMap(int mapSize, ArrayList<Thing> thgMap) {
		int treeAmount = mapSize / 15;
		while(treeAmount != 0) {
			thgMap.add(new Thing(randLoc(mapSize)));
			treeAmount--;
		}
	}

	public static Point randLoc(int mapSize) {
		int x = (int) (Math.random()*mapSize);
		int y = (int) (Math.random()*mapSize);
		Point loc = new Point(x, y);
		return loc;
	}
}
