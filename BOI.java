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
	static final int MAP_X = 10;
	static final int MAP_Y = 10;
	static final int SHIP_N = 4;
	static ArrayList<Ship> shipList = new ArrayList<Ship>();

	public static void main(String[] args) {
		BOI game = new BOI();

		Generator.makeMap(MAP_X, MAP_Y, SHIP_N);
		System.out.println("Generator.MakeMap() successful.");
		shipList = Generator.getShipList();
		game.makeGui(shipList);
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
					if(ship.isIn(field.XY)) {
						field.isShip();
					}
				}
				field.addMouseListener(tl);
				background.add(field);
			}//j
		}//i

		f.getContentPane().add(BorderLayout.CENTER, background);
		f.setVisible(true);
	}//method

	class TileListener extends MouseAdapter implements MouseListener {
		public void mouseReleased(MouseEvent event) {
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

	class Tile extends JPanel {
		boolean isShip = false;
		boolean isChecked = false;
		Point XY;

		Color field = Color.decode("#5b5266");
		Color ship = Color.decode("#FF6565");
		Color checked = Color.decode("#2D4C81");

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
				g2d.setColor(field);
				g2d.fillRect(-0, 0, this.getWidth(), this.getHeight());
			} else {
				if(isShip) {
					g2d.setColor(ship);
					g2d.fillRect(-0, 0, this.getWidth(), this.getHeight());
				} else {
					g2d.setColor(checked);
					g2d.fillRect(-0, 0, this.getWidth(), this.getHeight());
				}
			}//else
		}//paint
	}//inner
}//outer

abstract class Generator {				
	private static ArrayList<Ship> shipList = new ArrayList<Ship>();
	
	public static ArrayList<Ship> getShipList() {
		return shipList;
	}

	public static void makeMap(int mapX, int mapY, int n) {		//generates 1xN, 2x(N-1),.. , Nx1 ships on (MAP_X, MAP_Y) map
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
	}//method

	public static boolean placeShip(int mapX, int mapY, int size) {

		boolean placed = false;
		boolean spare = true;
		boolean failure = false;

		int vH;					//ship orientation, 1 for vertical, 0 for horisontal
		int startX = 0;
		int startY = 0;

		int count = 0;

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
				System.out.println(String.format("\nvH = %d ship of size %d starting at (%d, %d)\n", vH, size, startX, startY));
			}
			
			if(count++ > 200) {
				failure = true;
				System.out.println(String.format("\nGENERATION FAILED at 200 steps, starting anew..\n"));
				break;
			}
		}//while
		return failure;
	}//method
}//class

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

	public boolean isIn(Point p) {
		boolean isIn = false;
		for(Point cell: cells) {
			if(cell.equals(p)) {
				isIn = true;
			}
		}
		return isIn;	
	}//method
}

