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
	
	public static void main(String[] args) {
		BOI a = new BOI();	
		a.makeGui(a.initGame());
	}
	
	//generating 1x4, 2x3, 3x2, 4x1 ships
	public static boolean[x, y] genMap(int x, int y) {
		boolean[,] map;
		boolean startOver = true;
		
		while(startOver) {
			shipList.clear();
			for(int i = 4; i > 0; i--) {
				for(int j = 5 - i; j > 0; j--) {
					startOver = makeShip(i);
					if(startOver) {break;}
				}
				if(startOver) {break;}
			}
		}//while
	}//method

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
					if(ship.isIn(field)) {
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
			Tile b = (Tile)(event.getSource());
			if(b.isChecked == false) {
				b.isChecked();
				b.repaint();
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

class Rand {
	
	private void placeShip(int vH, int size, int x, int y) {
		shipList.add(new Ship(vH, size, x, y));
		success = true;
//		System.out.println(String.format("vH = %d ship of size %d at (%d, %d)", vH, size, x, y);
	}
	
	public boolean makeShip(int size) {

		boolean success = false;
		boolean failure = false;
		boolean deadend = false;
		int vH = 0;			//ship orientation: 1 if vertical, 0 if horizontal
		int x = 0;
		int y = 0;
		int count = 0;

		while(!success) {
			vH = (int)(Math.random()*2);
			x = (int)(Math.random()*(10-(size-1)*(1-vH)));
			y = (int)(Math.random()*(10-(size-1)*vH));
	
			if(shipList.isEmpty()) {
				placeShip(vH, size, x, y);
			} else {
				for(Ship ship: shipList) {
					if( ((ship.getX() >= x-1) && (ship.getX() <= x+1+(size-1)*(1-vH)) ) && ( (ship.getY() >= y-1) && (ship.getY() <= y+1+(size-1)*vH) ) ) {		
						failure = true;//generate ship anew
					}//if
				}//for
				
				if(!failure) {
					placeShip();
				}//if
			}//else
			
			if(count++ == 200) {
				deadend = true;
//				System.out.println("reached deadend");
				break;
			}
		}//while
		return deadend;
	}//method
}

class Ship {
	ArrayList<Integer> cells = new ArrayList<Integer>();
	
	public Ship(int vH, int size, int startX, int startY) {
		for(int i = 0; i < size; i++) {
			cells.add();
		}
	}
	
	public boolean isIn(Tile t) {
		for(cell: cells)
		if(t.getX() t.getY() )
	}
}