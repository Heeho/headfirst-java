import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ScrollGame {
	public static final double WALK_STEP = 2*Math.sqrt(2);		
	//sqrt(2), 2*sqrt(2) for 8 and 16 possible moving directions

	private JFrame f;
	private PlayerGUI g;
//	private Map m;
	private JLayeredPane screen;

	public static void main(String[] args) {
		ScrollGame game = new ScrollGame();
		game.go();
	}

	public void go() {
		f = new JFrame("Game");

		f.setSize(600,600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.setResizable(false);

		screen = new JLayeredPane();
		screen.setOpaque(true);
		screen.setPreferredSize(f.getSize());	
		screen.setBackground(Color.decode("#defcec"));
		screen.addMouseListener(new PlayerControl());

		g = new PlayerGUI("One", Color.BLACK);
		g.setSize(screen.getPreferredSize());	
		screen.add(g, 0);

		f.getContentPane().add(screen, BorderLayout.CENTER);
		f.setVisible(true);
	}

	class PlayerControl extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent event) {	
			if(SwingUtilities.isRightMouseButton(event)) {
				Point p = new Point(event.getPoint());
				g.setDestination(p);
			}
		}
	}//inner

	class PlayerGUI extends JPanel implements Runnable {
		private int speed;		//which is a number of WALK_STEP per 1000ms, so sleep is 1000/speed

		private Color color;
		private int picSize;
		private String name;
		Map m;

		private Point playerLocation, destination;

		public PlayerGUI (String n, Color c) {
			name = n;
			color = c;
			speed = 200;
			picSize = 20;

			m = new Map(2400);

			playerLocation = new Point();
			playerLocation.setLocation(m.randLocation());
			System.out.println("playerLocation = " + playerLocation);
			destination = new Point(playerLocation);

			Thread mapCalc = new Thread(m);
			mapCalc.start();

			Thread walker = new Thread(this);
			walker.start();
		}

		public void setDestination(Point p) {
			int a = -f.getWidth()/2;
			int b = -f.getHeight()/2;
			p.translate(a, b);
			destination.setLocation(playerLocation.getX() + p.getX(), playerLocation.getY() + p.getY());
			System.out.println("destination= " + destination);
		}

		public Point getPlayerLocation() {
			return playerLocation;
		}

		public void run() {
			System.out.println("walker starts");
			while(true) {
				if(
					(destination.getX() > 0 && destination.getX() < m.getMapSize()) &&
					(destination.getY() > 0 && destination.getY() < m.getMapSize())
				) {

					double len = playerLocation.distance(destination);

					double cosPhi = (destination.getX() - playerLocation.getX()) / len;
					double sinPhi = (destination.getY() - playerLocation.getY()) / len;

					int dX = (int) (WALK_STEP * cosPhi);
					int dY = (int) (WALK_STEP * sinPhi);

					playerLocation.setLocation(playerLocation.getX() + dX, playerLocation.getY() + dY);

					if(playerLocation.distance(destination) < WALK_STEP) {
						playerLocation.setLocation(destination);
						while(playerLocation == destination) { }
					} else {
						System.out.println("Arrived to " + playerLocation);
					}
				
				}//if

				try {
					Thread.sleep(1000/speed);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}//while
		}//run

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(color);
			g2d.fillRect(f.getWidth()/2 - picSize/2, f.getHeight()/2 - picSize/2, picSize, picSize);
			this.getToolkit().sync();
		}
	}//class

	class Tree extends JPanel {
		private int size = 21;
		private Color color;
		String[] hexColorGreen = {"#228b22", "#006400", "#9acd32", "#8fbc8f", "#556b2f", "6b8e23"};

		public Tree(Point p) {
			int i = (int) (Math.random()*6);
			color = Color.decode(hexColorGreen[i]);
			p.translate(-size/2, -size/2);
			this.setLocation(p);
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(color);
			g2d.fillRect(0, 0, size, size);
			this.getToolkit().sync();
		}
	}//inner

class Map implements Runnable {
	private int mapSize;
	private int visibleRange = 200;
	private ArrayList<Point> treeList;	//trees in range
	private ArrayList<Point> totalList;	//all the trees
	private Point randLocation;
	
	private int treeAmount;

	public Map(int x) {
		mapSize = x;
		treeAmount = (int) Math.pow(mapSize/f.getWidth(), 2)/2 * 3;

		totalList = new ArrayList<Point>();
		treeList = new ArrayList<Point>();

		for(int i = 0; i < treeAmount; i++) {
			totalList.add(randLocation());
		}
	}

	public Point randLocation() {
		int x = (int) (Math.random()*mapSize);
		int y = (int) (Math.random()*mapSize);
		Point loc = new Point(x, y);
		return loc;
	}

	public ArrayList<Point> getTreeList() {
		return treeList;
	}

	public int getMapSize() {
		return mapSize;
	}

	public void run () {
		while(true) {
			for(Point tree: totalList) {
				if(tree.distance(g.getPlayerLocation()) < f.getWidth()) {
					treeList.add(tree);
				} else {
					treeList.remove(tree);
				}
			}//for
		}//while
	}//run
}
}//game