import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Game {
	JFrame f;
	GUI g;
	Player p1;

	public static final double WALK_STEP = 2*Math.sqrt(2);		
		//sqrt(2), 2*sqrt(2) for 8 and 16 possible moving directions

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
		f.setBackground(Color.decode("#defcec"));

		f.addMouseListener(new PlayerControl());
		p1 = new Player("One");

		g = new GUI();
		f.getContentPane().add(BorderLayout.CENTER, g);
		
		Timer animator = new Timer(1000/60, new Animation());
		animator.start();

		f.setVisible(true);
	}

	class PlayerControl extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent event) {
			Point loc = new Point(event.getX(), event.getY());
			p1.setLocN(loc);
		}
	}//inner

	class Animation implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			g.repaint();
		}
	}

	class GUI extends JPanel {
		boolean started;
		private Color playerColor;
		private Dimension playerSize;

		public GUI() {
			playerSize = new Dimension(20, 20);
			playerColor = Color.RED;

		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			
			if(!started) {
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, f.getWidth(), f.getHeight());
				g2d.clearRect(0 + 20, 0 + 20, f.getWidth() - 40, f.getHeight() - 40);	
				started = true;
			}
			
			g2d.clearRect((int) (p1.prevLoc.getX() - playerSize.getWidth()/2), (int) (p1.prevLoc.getY() - playerSize.getHeight()/2), (int) playerSize.getWidth() , (int) playerSize.getHeight());
			g2d.setColor(playerColor);
			g2d.fillRect((int) (p1.loc.getX() - playerSize.getWidth()/2), (int) (p1.loc.getY() - playerSize.getHeight()/2), (int)playerSize.getWidth() , (int) playerSize.getHeight());
			p1.prevLoc.setLocation(p1.loc);
			this.getToolkit().sync();
		}
	}//gui

	class Player implements Runnable {		
		private String name;

		private int speed;		//which is a number of WALK_STEP per 1000ms, so sleep is 1000/speed

		private Point loc;
		private Point prevLoc;
		private Point locN;

		Socket sock;
		PrintWriter sendLoc;
		BufferedReader getLoc;

		public Player(String n) {
			name = n;
			speed = 200;

			loc = 		new Point(f.getWidth()/2, f.getHeight()/2);
			prevLoc = 	new Point(loc);
			locN = 		new Point(loc);

			setUpNetworking();

			Thread walker = new Thread(this);
			walker.start();
		}

		public void setLocN(Point p) {
			locN.setLocation(p);
			System.out.println("locN= " + locN);
		}

		public void run() {
			System.out.println("walker starts");
			while(true) {
				double nextX = locN.getX();
				double nextY = locN.getY();
				double currentX = loc.getX();
				double currentY = loc.getY();

				double len = Math.sqrt( Math.pow((nextX - currentX), 2) + Math.pow((nextY - currentY), 2));
				double cosPhi = (nextX - currentX) / len;
				double sinPhi = (nextY - currentY) / len;

				int dX = (int) (WALK_STEP * cosPhi);
				int dY = (int) (WALK_STEP * sinPhi);

				loc.translate(dX, dY);
				shareLoc();

				if(Math.abs(loc.getX()-locN.getX()) < 2 && Math.abs(loc.getY()-locN.getY()) < 2 ) {
				loc.setLocation(locN);
				shareLoc();
				while(loc==locN) {}
				} else {
					System.out.println("moving to " + loc);
				}
				try {
					Thread.sleep(1000/speed);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}//while
		}//run
		
		public void shareLoc() {
			try {
				sendLoc.println(name + "/" + loc);
				sendLoc.flush();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

//		public void acquireLoc() {
//			String[] data;
//			String loc;
//			try {
//				while((loc = getLock.readLine()) != null) {
//					data = loc.split("/");
//				}
//		}

		public void setUpNetworking() {
			try {
				sock = new Socket("127.0.0.1", 1337);

				InputStreamReader locReader = new InputStreamReader(sock.getInputStream());

				getLoc = new BufferedReader(locReader);
				sendLoc = new PrintWriter(sock.getOutputStream());

				System.out.println("networking established");
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}//setupnetwork
	}//class
}//game