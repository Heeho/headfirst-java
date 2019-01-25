import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;

public class ScrollGame implements Runnable {
	public static final int VIEW_SIZE = 600;
	public static final int MAP_SIZE = 2400;

	private String name;

	private Map m = new Map(MAP_SIZE, VIEW_SIZE);
	private PlayerGUI pg = new PlayerGUI(VIEW_SIZE);

	private JFrame f;
	private JLayeredPane screen;

	private HashSet<Point> treeLocationsInView;	//tree locations in range
	private HashSet<Tree> treesInView;			//reference array to hold visible Trees
	public static void main(String[] args) {
		ScrollGame game = new ScrollGame();
		game.go();
	}

	public void go() {
		f = new JFrame("ScrollGame");
		f.setSize(VIEW_SIZE, VIEW_SIZE);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.setResizable(false);

		screen = new JLayeredPane();
		screen.setOpaque(true);
		screen.setPreferredSize(f.getSize());	
		screen.setBackground(Color.decode("#defcec"));
		screen.addMouseListener(new PlayerControl());

		pg.setSize(screen.getPreferredSize());	
		pg.setOpaque(false);
		screen.add(pg, 2);

		treeLocationsInView = new HashSet<Point>();
		treesInView = new HashSet<Tree>();

		f.getContentPane().add(screen, BorderLayout.CENTER);
		f.setVisible(true);

		Thread viewFiller = new Thread(this);
		viewFiller.start();
	}

	class PlayerControl extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent event) {	
			if(SwingUtilities.isRightMouseButton(event)) {
				Point p = new Point(event.getPoint());
				m.setPlayerDestination(p);
			}
		}
	}//inner

	public void run () {	//check objects to add to view
		while(true) {
			//make Things array, instantiate if in view
			for(Point loc: m.getTreeLocations()) {
				if(loc.distance(m.getPlayerLocation()) < VIEW_SIZE/2) {
					if(!(treeLocationsInView.contains(loc))) {
						System.out.println("Tree in view at " + loc);
						treesInView.add(new Tree(loc));
					}
					treeLocationsInView.add(loc);
				} else {
					treeLocationsInView.remove(loc);
				}
			}//for
		}//while
	}
//make Things superclass for game objects
class PlayerGUI extends JPanel {
	private int viewSize;
	private int picSize = 20;
	private Color color = Color.BLACK;

	public PlayerGUI(int view) {
		viewSize = view;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(color);
		g2d.fillRect(viewSize/2 - picSize/2, viewSize/2 - picSize/2, picSize, picSize);
		this.getToolkit().sync();
	}

	//Might insert some Listeners for player character interaction

}//inner

	class Tree extends JPanel implements Runnable {
		private int size = 21;
		private Color color = new Color(0x8fbc8f);
		private Point treeLocation;

		public Tree(Point p) {
			treeLocation = new Point(p);

			this.setSize(screen.getPreferredSize());
			this.setOpaque(false);
			screen.add(this, 0);
			
			Thread rangeCheck = new Thread(this);
			rangeCheck.start();
		}

//		private void calcLocation() {
//			int x = (int) (VIEW_SIZE/2 - (m.getPlayerLocation().getX() - treeLocation.getX()));
//			int y = (int) (VIEW_SIZE/2 - (m.getPlayerLocation().getY() - treeLocation.getY()));
//			this.setLocation(x, y);
//		}

		//remake into Swing.Timer, so it throw events for EDT
		//apply Bresenham algorithm
		public void run() {
			int x, y;
			while(true) {
				if(this.getLocation().distance(m.getPlayerLocation()) < VIEW_SIZE) {
					x = (int) (VIEW_SIZE/2 - (m.getPlayerLocation().getX() - treeLocation.getX()));
					y = (int) (VIEW_SIZE/2 - (m.getPlayerLocation().getY() - treeLocation.getY()));

					this.setLocation(x, y);
				} else {
					treesInView.remove(this);
					break;
				}
			}
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(color);
			g2d.fillRect(0, 0, size, size);
			this.getToolkit().sync();
		}
	}//inner

	class MapBorder extends JPanel {}

}//scrollgame

class Map implements Runnable {
	private Point 	playerLocation, 
				playerDestination;

	private int 	mapSize,
				viewSize;

	public static final double WALK_STEP = 2*Math.sqrt(2);		
	//sqrt(2), 2*sqrt(2) for 8 and 16 possible moving directions

	private int speed = 200;
	//which is a number of WALK_STEP per 1000ms, so walker sleep is 1000/speed

	private ArrayList<Point> treeLocations;	//all the trees

	//generate Things and add to array instead of Points
	public Map(int map, int view) {
		mapSize = map;
		viewSize = view;

		playerLocation = new Point();
		playerLocation.setLocation(randLocation());
		System.out.println("playerLocation = " + playerLocation);
		playerDestination = new Point(playerLocation);

		int treeAmount = (int) Math.pow(mapSize/viewSize, 2)/2;

		treeLocations = new ArrayList<Point>();

		treeLocations.add(new Point(1200, 1200));
//		for(int i = 0; i < treeAmount; i++) {
//			treeLocations.add(randLocation());
//		}

		Thread walker = new Thread(this);
		walker.start();
	}

	public Point randLocation() {
		int x = (int) (Math.random()*mapSize);
		int y = (int) (Math.random()*mapSize);
		Point loc = new Point(x, y);
		return loc;
	}

	public void setPlayerDestination(Point p) {
		int a = -viewSize/2;
		int b = -viewSize/2;
		p.translate(a, b);
		playerDestination.setLocation(playerLocation.getX() + p.getX(), playerLocation.getY() + p.getY());
		System.out.println("playerDestination= " + playerDestination);
	}

	public Point getPlayerLocation() {
		return playerLocation;
	}

	public ArrayList<Point> getTreeLocations() {
		return treeLocations;
	}

	//remake into util.Timer
	//apply Bresenham algorithm
	public void run() {	
		System.out.println("walker starts");
		while(true) {
			if(
				(playerDestination.getX() > 0 && playerDestination.getX() < mapSize) &&
				(playerDestination.getY() > 0 && playerDestination.getY() < mapSize)
			) {
				double len = playerLocation.distance(playerDestination);
				double cosPhi = (playerDestination.getX() - playerLocation.getX()) / len;
				double sinPhi = (playerDestination.getY() - playerLocation.getY()) / len;

				int dX = (int) (WALK_STEP * cosPhi);
				int dY = (int) (WALK_STEP * sinPhi);

				playerLocation.setLocation(playerLocation.getX() + dX, playerLocation.getY() + dY);

				if(playerLocation.distance(playerDestination) < WALK_STEP) {
					playerLocation.setLocation(playerDestination);
					while(playerLocation == playerDestination) { }
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
}//map



