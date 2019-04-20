import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class ScrollGame {
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

	JFrame f;
	JLayeredPane screen;

	Player p1;

	private long speed = 1;
	Walker w;
	
	public GUI() {
		go();
	}
	
	public void go() {
		f = new JFrame("ScrollGame");
		f.setSize(VIEW_SIZE, VIEW_SIZE);

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
		
		p1 = new Player();
		p1.setSize(screen.getPreferredSize());
		p1.setOpaque(false);
		screen.add(p1, JLayeredPane.DEFAULT_LAYER);

		f.getContentPane().add(screen, BorderLayout.CENTER);

		w = new Walker(screen, VIEW_SIZE, MAP_SIZE);
		Map.fillMap(MAP_SIZE, w.thgMap);

		Timer t1 = new Timer();
		TimerTask walker = new TimerTask() {
			public void run() {
				w.walk();
			}
		};
		t1.scheduleAtFixedRate(walker, 0L, speed);

		f.setVisible(true);
	}//go
	
	class PlayerControl extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent event) {	
			if(SwingUtilities.isRightMouseButton(event)) {
				Point p = new Point(event.getPoint());
				w.setPDest(p);
			}
		}
	}//inner

	//player character sprite
	class Player extends JLabel {
		private int picSize = 20;
		private Color color = Color.BLACK;

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
	private int viewRange, fadeRange;
	private int mapSize;

	Point pLoc, pDest;

	JLayeredPane screen;
	ArrayList<Thing> thgMap;
	ArrayList<Thing> thgSet;

	int dX, dY;
	int err;
	
	public Walker(JLayeredPane s, int vR, int mS) {
		pLoc = new Point(0, 0);
		pDest = new Point(pLoc);

		thgMap = new ArrayList<Thing>();
		thgSet = new ArrayList<Thing>();
		viewRange = vR;
		fadeRange = viewRange + 100;
		screen = s;
	}

	public void setPDest(Point p) {
		int a = -viewRange/2;
		int b = -viewRange/2;
		p.translate(a, b);
		pDest.setLocation(pLoc.getX() + p.getX(), pLoc.getY() + p.getY());
		System.out.println("playerDestination= " + pDest);
	}

	public void walk() {
		int deltaX = (int) (pDest.getX() - pLoc.getX());
		int deltaY = (int) (pDest.getY() - pLoc.getY());
	
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

		if(dX != 0 || dY != 0) {
			pLoc.translate(dX, dY);
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					show();
				}
			});
		}

		if(pLoc.getX() > mapSize) {pLoc.translate(-mapSize, 0);}
		if(pLoc.getX() < 0) {pLoc.translate(mapSize, 0);}
		if(pLoc.getY() > mapSize) {pLoc.translate(0, -mapSize);}
		if(pLoc.getY() < 0) {pLoc.translate(0, mapSize);}

		System.out.println("Arrived to " + pLoc);
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

		for(Thing thg: thgSet) {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					int x = (int) (thg.getLocation().getX());
					int y = (int) (thg.getLocation().getY());
					thg.setLocation(x - dX, y - dY);
				}
			});

//			System.out.println("Thg " + thg.iD + ": " + thg.getTLoc().getX() + " " + thg.getTLoc().getY());
//			System.out.println("Thing fades at " + thg.getTLoc().getX() + " " + thg.getTLoc().getY());

			dist = thg.getTLoc().distance(pLoc);
			if(dist > fadeRange) {
				screen.remove(thg);
				thgSet.remove(thg);
			}
		}//for

		for(Thing thg: thgMap) {
			dist = thg.getTLoc().distance(pLoc);
			if(!thgSet.contains(thg) && dist < viewRange) {
				thgSet.add(thg);
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						int x = (int) (viewRange/2 - pLoc.getX() + thg.getTLoc().getX());
						int y = (int) (viewRange/2 - pLoc.getY() + thg.getTLoc().getY());	
						thg.setSize(screen.getPreferredSize());
						thg.setOpaque(false);
						screen.add(thg, 5);
						thg.setLocation(x, y);
//						screen.validate();
					}
				});
			}
		}//for
	}//show
}

class Map {
	public static void fillMap(int mapSize, ArrayList<Thing> thgMap) {
		int treeAmount = mapSize/15;
		Point p = new Point();
		boolean success = false;

		while(treeAmount != 0) {
			p = randLoc(mapSize);
			thgMap.add(new Thing(p));
			treeAmount--;
		}
	}

	public static Point randLoc(int mapSize) {
		int x = (int) (Math.random()*mapSize);
		int y = (int) (Math.random()*mapSize);
		Point loc = new Point(x, y);
		System.out.println("Thing at " + loc);
		return loc;
	}
}
