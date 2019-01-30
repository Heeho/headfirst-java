import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;


public class ScrollGame {
	public static void main(String[] args) {
		ScrollGame game = new ScrollGame();
		game.go();
	}

	public void go() {
		private GUI gui = new GUI();
	}
}//scrollgame

class GUI {
	public static final int VIEW_SIZE = 600;
	public static final int MAP_SIZE = 2400;

	private int showerSpeed = 1000/15;
	//as in FPS = 1000/x

	private String name;

	private Player p1 = new Player();
	private Map m = new Map(MAP_SIZE, VIEW_SIZE);

	private JFrame f;
	private JLayeredPane gui;

	private HashSet<Thing> thingsInView;

	public void go() {
		f = new JFrame("ScrollGame");
		f.setSize(VIEW_SIZE, VIEW_SIZE);
		f.setBackground(Color.decode("#defcec"));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.setResizable(false);

		gui = new JLayeredPane();
		gui.setLayout(null);
		gui.setOpaque(false);
		gui.setPreferredSize(f.getSize());	
//		gui.setBackground(Color.decode("#defcec"));
		gui.addMouseListener(new PlayerControl());

		p1.setSize(gui.getPreferredSize());	
		p1.setOpaque(false);
		gui.add(p1, 2);

		f.getContentPane().add(gui, BorderLayout.CENTER);

		Timer thingsInViewCalc = new Timer(showerSpeed, new Shower());
		thingsInViewCalc.start();
		System.out.println("shower starts");

		f.setVisible(true);
	}

	class PlayerControl extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent event) {	
			if(SwingUtilities.isRightMouseButton(event)) {
				Point p = new Point(event.getPoint());
				m.setPlayerDestination(p);
			}
		}
	}//inner

	class Player extends JLabel {
		private int picSize = 20;
		private Color color = Color.BLACK;

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(color);
			g2d.fillRect(VIEW_SIZE/2 - picSize/2, VIEW_SIZE/2 - picSize/2, picSize, picSize);
//			this.getToolkit().sync();		//will be handy for player sprite
		}

		//Might insert some Listeners for player character interaction

	}//inner

	class Shower implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Point thgLoc = new Point();
			for(Thing thg: m.getThingsSet()) {
				thgLoc = thg.getMapLocation();
				if(thgLoc.distance(m.getPlayerLocation()) < VIEW_SIZE/2) {
					System.out.println("Thing in view at " + thgLoc);
					thingsInView.add(thg);
				} else {
					thingsInView.remove(thg);
				}
			}//for
		}//method
	}//timer

class Thing extends JLabel {
	private int size = 21;
	private Color color = new Color(0x8fbc8f);
	private Point mapLocation;

	public Thing(Point p) {
		mapLocation = new Point(p);

//		this.setSize(gui.getPreferredSize());
//		this.setOpaque(false);
//		gui.add(this, 0);
			
//		Thread rangeCheck = new Thread(this);
//		rangeCheck.start();

		Timer drawInView = new Timer(15, new Relocation());
		drawInView.start();
	}

	public Point getMapLocation() {
		return mapLocation;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(color);
		g2d.fillRect(0, 0, size, size);
	}

	//apply Bresenham algorithm in Relocation
	//maybe better to place it in Timer@Map

	class Relocation implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			int x, y;
			while(true) {
				if(this.getLocation().distance(m.getPlayerLocation()) < VIEW_SIZE) {
					x = (int) (VIEW_SIZE/2 - (m.getPlayerLocation().getX() - mapLocation.getX()));
					y = (int) (VIEW_SIZE/2 - (m.getPlayerLocation().getY() - mapLocation.getY()));

					this.setLocation(x, y);
				} else {
					thingsInView.remove(this);
					drawInView.stop();
					break;
				}
			}
		}
	}//inner
}//inner
	class MapBorder extends JPanel {}
}//gui

class Map {
	private Point 	playerLocation, 
				playerDestination;

	private int 	mapSize,
				viewSize;

	public static final double WALK_STEP = 2*Math.sqrt(2);		
	//sqrt(2), 2*sqrt(2) for 8 and 16 possible moving directions

	private HashSet<Thing> thingsSet;	//all the things on map

	private int walkerSpeed = 1000/200;
	//as in FPS = 1000/x

	public Map(int map, int view) {
		mapSize = map;
		viewSize = view;

		playerLocation = new Point();
		playerLocation.setLocation(900, 900);
//		playerLocation.setLocation(randLocation());
		System.out.println("playerLocation = " + playerLocation);

		playerDestination = new Point(playerLocation);

		int treeAmount = (int) Math.pow(mapSize/viewSize, 2)/2;

		thingsSet = new HashSet<Thing>();

		Point testLoc = new Point(1200, 1200);
		thingsSet.add(new Thing(testLoc));

//		for(int i = 0; i < treeAmount; i++) {
//			thingsSet.add(randLocation());
//		}

		Timer playerLocationCalc = new Timer(walkerSpeed, new Walker());
		playerLocationCalc.start();
		System.out.println("walker starts");
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

	public HashSet<Thing> getThingsSet() {
		return thingsSet;
	}


	//apply Bresenham algorithm below

	class Walker implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			//playerLocation calculator, responds to MouseListener on gui
			if(
				(playerDestination.getX() > 0 && playerDestination.getX() < mapSize) &&
				(playerDestination.getY() > 0 && playerDestination.getY() < mapSize)
			) {
				double len = playerLocation.distance(playerDestination);
				double cosPhi = (playerDestination.getX() - playerLocation.getX()) / len;
				double sinPhi = (playerDestination.getY() - playerLocation.getY()) / len;

				int dX = (int) (WALK_STEP * cosPhi);
				int dY = (int) (WALK_STEP * sinPhi);

				playerLocation.translate(dX, dY);
//				playerLocation.setLocation(playerLocation.getX() + dX, playerLocation.getY() + dY);	

				if(playerLocation.distance(playerDestination) < WALK_STEP) {
					playerLocation.setLocation(playerDestination);
					while(playerLocation == playerDestination) { }
				} else {
					System.out.println("Arrived to " + playerLocation);
				}				
			}//if
		}//method
	}//timer
}//map



