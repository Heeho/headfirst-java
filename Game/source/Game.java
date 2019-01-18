import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Game {
	public static final double WALK_STEP = 2*Math.sqrt(2);		
	//sqrt(2), 2*sqrt(2) for 8 and 16 possible moving directions

	private JFrame f;
	private Player p1;
	private Tree t1;
	private int treeAmount = 7;
	private JLayeredPane screen;

	public static void main(String[] args) {
		Game game = new Game();
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

		p1 = new Player("One");
		p1.setSize(screen.getPreferredSize());
		screen.add(p1, 4);

		for(int i = 0; i < treeAmount; i++) {
			t1 = new Tree();
			t1.setSize(screen.getPreferredSize());
			screen.add(t1, 0);
			System.out.println("tree at " + t1.getLocation());
		}

		f.getContentPane().add(screen, BorderLayout.CENTER);
		f.setVisible(true);
	}

	class PlayerControl extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent event) {	
			if(SwingUtilities.isRightMouseButton(event)) {
				Point p = new Point(event.getPoint());
				int a = (-p1.picSize())/2;
				p.translate(a, a);
				p1.setLocN(p);
			}
		}
	}//inner

	class Tree extends JPanel {
		private int size = 21;

		public Tree() {
			int x = (int) (Math.random()*f.getWidth());
			int y = (int) (Math.random()*f.getHeight());
			this.setLocation(x, y);
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(Color.GREEN);
			g2d.fillRect(0, 0, size, size);
			this.getToolkit().sync();
		}
	}//inner

	class Player extends JPanel implements Runnable {
		private String name;

		private Color color = Color.RED;
		private int size = 20;
		private int speed;		//which is a number of WALK_STEP per 1000ms, so sleep is 1000/speed

		private Point locN;

		public Player(String n) {
			name = n;
			speed = 200;

			this.setLocation(300, 300);
			locN = new Point(this.getLocation());

			Thread walker = new Thread(this);
			walker.start();
		}

		public void setLocN(Point p) {
			locN.setLocation(p);
			System.out.println("locN= " + locN);
		}	

		public int picSize() {
			return size;
		}

		public void run() {
			System.out.println("walker starts");
			while(true) {
				double len = this.getLocation().distance(locN);

				double cosPhi = (locN.getX() - this.getX()) / len;
				double sinPhi = (locN.getY() - this.getY()) / len;

				int dX = (int) (WALK_STEP * cosPhi);
				int dY = (int) (WALK_STEP * sinPhi);

				this.setLocation(this.getX() + dX, this.getY() + dY);

				if(this.getLocation().distance(locN) < WALK_STEP) {
					this.setLocation(locN);
					while(this.getLocation() == locN) {}
				} else {
					System.out.println("moving to " + this.getLocation());
				}

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
			g2d.fillRect(0, 0, size, size);
			this.getToolkit().sync();
		}
	}//inner
}//game

//	Socket sock;
//	PrintWriter sendLoc;
//	BufferedReader getLoc;
//
//	public void shareLoc() {
//		try {
//			sendLoc.println(name + "/" + loc);
//			sendLoc.flush();
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	public void acquireLoc() {
//		String[] data;
//		String loc;
//		try {
//			while((loc = getLock.readLine()) != null) {
//				data = loc.split("/");
//			}
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	public void setUpNetworking() {
//		try {
//			sock = new Socket("127.0.0.1", 1337);
//
//			InputStreamReader locReader = new InputStreamReader(sock.getInputStream());
//
//			getLoc = new BufferedReader(locReader);
//			sendLoc = new PrintWriter(sock.getOutputStream());
//
//			System.out.println("networking established");
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//	}//setupnetwork
