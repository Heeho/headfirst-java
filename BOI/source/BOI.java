/*
TODO:
1. RESULT/HIGHSCORE FRAME
2. HIGHLIGHT FOR KILLS
*/

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BOI {
	static final int MAP_X = 10;
	static final int MAP_Y = 10;
	static final int SHIP_N = 4;
	
	ArrayList<Ship> shipList;
	ArrayList<Tile> tileList;
	HighScore highScore;

	boolean gameInactive;
	int guessCount;

	JFrame f = new JFrame("BOI!");

	public static void main(String[] args) {
		BOI game = new BOI();
		game.makeMap(MAP_X, MAP_Y, SHIP_N);
		game.go();

//		try {
//			Runtime.getRuntime().addShutdownHook(new highScore());
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}

	private void makeMap(int mapX, int mapY, int n) {		//generates 1xN, 2x(N-1),.. , Nx1 ships on (MAP_X, MAP_Y) map
		shipList = new ArrayList<Ship>();
		boolean startAnew = true;

		while(startAnew) {
			shipList.clear();
			outerloop:	
			for(int i = n; i > 0; i--) {							//iterates through ship size
				for(int j = i - 1; j < n; j++) {					//iterates through number of ships
					startAnew = placeShip(mapX, mapY, i);
					if(startAnew) {break outerloop;}
				}
			}
		}

		System.out.println("Generator.MakeMap() successful.");
	}//method

	private boolean placeShip(int mapX, int mapY, int size) {

		boolean placed = false;
		boolean spare = true;
		boolean failure = false;

		int vH = 0;					//ship orientation, 1 for vertical, 0 for horisontal
		int startX = 0;
		int startY = 0;

		int loopCount = 0;
		int failureCount = 0;

		while(!placed) {
			vH = (int)(Math.random()*2);	
			startX = (int)(Math.random()*(mapX-(size-1)*(1-vH)));
			startY = (int)(Math.random()*(mapY-(size-1)*vH));

			if(shipList.isEmpty() == false) {
				outerloop:
				for(Ship ship: shipList) {
					for(Point cell: ship.cells) {
						if( ( (cell.getX() >= startX-1) && (cell.getX() <= startX+1+(size-1)*(1-vH)) ) && ( (cell.getY() >= startY-1) && (cell.getY() <= startY+1+(size-1)*vH) ) ) {		
							spare = false;			//generate ship anew
							break outerloop;
						}
					}//for cell
				}//for ship
			}

			if(spare) {
				shipList.add(new Ship(vH, size, startX, startY));
				placed = true;
			}
			
			if(loopCount++ > (BOI.MAP_X * BOI.MAP_Y * 2)) {
				failure = true;
				System.out.println(String.format("\nGENERATION FAILED at %d loop of %d attempt, starting anew..\n", loopCount, ++failureCount));
				break;
			}
		}//while
		return failure;
	}//method

	public void go() {
		highScore = new HighScore();
//		JFrame f = new JFrame("BOI!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600,600);
		f.setLocationRelativeTo(null);
		f.setUndecorated(true);
		f.setResizable(false);
	
		GridLayout grid = new GridLayout(MAP_X, MAP_Y);
		grid.setVgap(4);
		grid.setHgap(4);

		Background background = new Background();
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		background.setLayout(grid);

		GuessListener tl = new GuessListener();

		f.getContentPane().add(BorderLayout.CENTER, background);
		JMenuBar menuBar = new JMenuBar();
		JMenuItem restartMenuItem = new JMenuItem("Restart");
		JMenuItem highScoreMenuItem = new JMenuItem("High Scores");

		restartMenuItem.addActionListener(new RestartListener());
		highScoreMenuItem.addActionListener(new highScoreListener());

		menuBar.add(restartMenuItem);
		menuBar.add(highScoreMenuItem);

		f.setJMenuBar(menuBar);

		tileList = new ArrayList<Tile>();

		for(int j = 0; j < MAP_Y; j++) {
			for(int i = 0; i < MAP_X; i++) {
				Tile tile = new Tile(i, j);
				for(Ship ship: shipList) {
					if(ship.cells.contains(tile.location)) {
						tile.hasShip();
						break;
					}
				}
				tileList.add(tile);
				tile.addMouseListener(tl);
				background.add(tile);
			}//j
		}//i

		f.setVisible(true);

	}//method

	public void finishGame() {
		gameInactive = true;

		for(Tile tile: tileList) {
			tile.open();
		}
		System.out.println(String.format("GameOver! It took you %d guesses.\n", guessCount));

		highScore.add(guessCount);
		highScore.show(guessCount);
	}

	public class highScoreListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			highScore.show(guessCount);
		}//method
	}//inner

	class Background extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0,0, this.getWidth(), this.getHeight());
		}

	public class Tile extends JPanel {
		boolean isChecked;
		boolean hasShip;
		Point location;

		Color field = Color.decode("#5B5266");
		Color ship = Color.decode("#FF6565");
		Color checked = Color.decode("#2D4C81");

		public Tile(int x, int y) {
			location = new Point(x, y);
		}
		public void reset() {
			isChecked = false;
			hasShip = false;
			repaint();
		}
		
		public void open() {
			isChecked = true;
			repaint();
		}

		public void hasShip() {
			hasShip = true;
		}

		public void isChecked() {
			isChecked = true;
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			if(isChecked) {
				if(hasShip) {
					g2d.setColor(ship);
					g2d.fillRect(-0, 0, this.getWidth(), this.getHeight());
				} else {
					g2d.setColor(checked);
					g2d.fillRect(-0, 0, this.getWidth(), this.getHeight());
				}
			} else {
				g2d.setColor(field);
				g2d.fillRect(-0, 0, this.getWidth(), this.getHeight());
			}
		}//paint
	}//inner

	public class RestartListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			gameInactive = true;
			guessCount = 0;
			makeMap(MAP_X, MAP_Y, SHIP_N);
			for (Tile tile: tileList) {
				tile.reset();
				for(Ship ship: shipList) {
					if(ship.cells.contains(tile.location)) {
						tile.hasShip();
						break;
					}
				}
			}
			gameInactive = false;
		}//listener
	}//class

	public class GuessListener extends MouseAdapter implements MouseListener {
		public void mouseClicked(MouseEvent event) {
			String result = "miss";
			if(!gameInactive) {
				Tile t = (Tile)(event.getSource());
				if(t.isChecked == false) {
					guessCount++;
					t.isChecked();
					t.repaint();

					for(Ship ship: shipList) {
						result = ship.hitCheck(t.location);
						if(result.equals("hit")) {
							break;
						}
						if(result.equals("kill")) {
							shipList.remove(ship);
							break;
						}
					}
					System.out.println(result);

					if(shipList.isEmpty()) {
						finishGame();
					}
				}//if
			}//if active
		}//method
	}//inner
}//outer

class Ship {
	ArrayList<Point> cells;
	
	public Ship(int vH, int size, int startX, int startY) {
		int x;
		int y; 
		cells = new ArrayList<Point>();
		System.out.println("Placed ship at:");
		for(int i = 0; i < size; i++) {
			x = startX + i*(1-vH);
			y = startY + i*vH;
			System.out.println(String.format("(%d, %d)", x, y));
			cells.add(new Point(x, y));
		}
	}//method

	public String hitCheck(Point p) {
		String result = "miss";
		if(cells.contains(p)) {
			cells.remove(p);
			if(cells.isEmpty() != true) {
				result = "hit";
			} else {
				result = "kill";
			}
		}
		return result;
	}//method
}

class HighScore 
{//extends Thread {
	private static ArrayList<Integer> highScoreList = new ArrayList<Integer>();
	private File file = new File("highscore.txt");
	private int highScoreDepth = 7;
	private int lastScore;

	public HighScore() {
		load();
	}

	public void load() {
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while((line = reader.readLine()) != null) {
				highScoreList.add(Integer.parseInt(line));
			} 
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void show(int guessCount) {
		boolean found = false;
		int i = 0;
		String text = "\nHIGHSCORE:\n";
		for(int score: highScoreList) {
			text += String.format("\n%d. %d", ++i, score);
			if((guessCount == score) && !found) {
				text += " <<<YOU";
				found = true;
			}
		}	
		System.out.println(text);
	}

	public void add(int guessCount) {
		highScoreList.add(guessCount);
		Collections.sort(highScoreList);
		if(highScoreList.size() > highScoreDepth) {
			highScoreList.subList(highScoreDepth, highScoreList.size()).clear();
		}
		save();
	}

//	public void run() {
//		save();
//	}

	public void save() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int score: highScoreList) {
				writer.write(score + "\n");
			}
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}