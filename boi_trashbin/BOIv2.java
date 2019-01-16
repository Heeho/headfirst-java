/*
TODO:
1. SAVE/LOAD A PATTERN (MENU BAR -> FILE -> NEW, SAVE, LOAD)
2. ANOTHER FIELD FOR PLAYER SHIPS
3. MANUAL SETUP
4. AI
5. MAYBE
*/

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class BOI {
	final int MAP_X;
	final int MAP_Y;
	final int SHIP_N;

	public static void main(String[] args) {
		BOI game = new BOI();
		Generator gen = new Generator();

		game.makeGui(gen.makeMap(MAP_X, MAP_Y, SHIP_N));
	}
	
	public void makeGui(ArrayList<Ship> shipList) {
		JFrame f = new JFrame("BOI!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600,600);
		f.setResizable(false);
		
		GridLayout grid = new GridLayout(10,10);
		grid.setVgap(4);
		grid.setHgap(4);

		Background background = new Background();
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		background.setLayout(grid);

		TileListener tl = new TileListener();
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				Tile field = new Tile(i, j);
				for(Ship ship: shipList) {
					if(ship.contains(field.XY.getLocation())) {
						field.isShip();
					}
				}
				field.addActionListener(tl);
				background.add(field);
			}//j
		}//i

		f.getContentPane().add(BorderLayout.CENTER, background);
		f.setVisible(true);
	}//method

	class TileListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Tile t = (Tile)(event.getSource());
			if(t.isChecked == false) {
				t.isChecked();
				t.repaint();
			}
		}
	}//inner

	class Background extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0,0, this.getWidth(), this.getHeight());
		}
	}//inner

	class Tile extends JButton {
		boolean isShip = false;
		boolean isChecked = false;
		Point XY;

		public Tile(int x, int y) {
			XY = new Point(x, y);
		}

		public void isShip() {
			isShip = true;
		}

		public void isChecked() {
			isChecked = true;
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			if(!isChecked) {
				g2d.setColor(Color.BLUE);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			} else {
				if(isShip) {
					g2d.setColor(Color.RED);
					g2d.fillRect(0,0, this.getWidth(), this.getHeight());
				} else {
					g2d.setColor(Color.GRAY);
					g2d.fillRect(0,0, this.getWidth(), this.getHeight());
				}
			}//else
		}//paint
	}//inner
}//outer

class Generator {				
	public static ArrayList<Ship> makeMap(int mapX, int mapY, int n) {		//generates 1xN, 2x(N-1),.. , Nx1 ships on (MAP_X, MAP_Y) map
		ArrayList<Ship> shipList = new ArrayList<Ship>();
		boolean startAnew = true;
		while(startAnew) {
			shipList.clear();
			for(int i = n; i > 0; i--) {							//iterates through ship size
				for(int j = i - 1; j < n; j++) {					//iterates through number of ships
					startAnew = placeShip(mapX, mapY, i);
					if(startAnew) {break;}
				}
				if(startAnew) {break;}
			}//for i
		}//while
		return shipList;
	}//method

	public boolean placeShip(int mapX, int mapY, int size) {

		boolean placed = false;
		boolean spare = false;
		boolean failure = false;

		int vH;					//ship orientation, 1 for vertical, 0 for horisontal
		int startX = 0;
		int startY = 0;

		int count = 0;

		while(!placed) {
			vH = (int)(Math.random()*2);	
			x = (int)(Math.random()*(mapX-(size-1)*(1-vH)));
			y = (int)(Math.random()*(mapY-(size-1)*vH));
			if(shipList.isEmpty()) {
				shipList.add(new Ship(vH, size, startX, startY));
				placed = true;
//				System.out.println(String.format("vH = %d ship of size %d starting at (%d, %d)", vH, size, x, y);
			} else {
				for(Ship ship: shipList) {
					for(Point cell: cells) {
						if( ((cell.getX() >= startX-1) && (cell.getX() <= startX+1+(size-1)*(1-vH)) ) && ( (cell.getY() >= startY-1) && (cell.getY() <= startY+1+(size-1)*vH) ) ) {		
							spare = false;			//generate ship anew
						}//if
					}//for cell
				}//for ship
				if(spare) {
					shipList.add(new Ship(vH, size, startX, startY));
					placed = true;
//					System.out.println(String.format("vH = %d ship of size %d starting at (%d, %d)", vH, size, x, y);
				}//if
			}//else
			
			if(count++ == 200) {
				failure = true;
//				System.out.println("reached deadend");
				break;
			}
		}//while
		return failure;
	}//method
}

class Ship {
	private ArrayList<Point> cells;
	
	public Ship(int vH, int size, int startX, int startY) {
		int x;
		int y; 
		cells = new ArrayList<Point>();

		for(int i = 0; i < size; i++) {
			x = startX + i*(1-vH);
			y = startY + i*vH;
			cells.add(new Point(x, y));
		}
	}
	
	public boolean isIn(Tile t) {
		for(Point cell: cells) {
			if(cell.getLocation().equals(t.XY.getLocation())) {
				cells.remove(cell);
			}
			if(cells.isEmpty()) {
//				System.out.println("It's a kill!");
			}
		}
	}//method
}//class