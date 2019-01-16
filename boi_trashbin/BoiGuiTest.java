import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class BoiGuiTest {
	
	ArrayList<Integer> shipList = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		BoiGuiTest a = new BoiGuiTest();
		a.initGame();
		a.makeGui();
	}

	public void initGame() {
		for(int i = 4; i > 0; i--) {
			for(int j = 5 - i; j > 0; j--) {
				makeShip(i);
			}
		}
	}

	public void makeShip(int size) {

		boolean success = false;
		boolean finished = false;
		int vH = 0;			//ship orientation: 1 if vertical, 0 if horizontal
		int x = 0;
		int y = 0;
		int shipX;
		int shipY;
		int xyToN;

		while(!success) {	
			vH = (int)(Math.random()*2);
			x = (int)(Math.random()*(10-(size-1)*(1-vH)));
			y = (int)(Math.random()*(10-(size-1)*vH));
	
			if(shipList.isEmpty()) {
				for(int i = 0; i < size; i++) {
					xyToN = (y+i*vH)*10+(x+i*(1-vH));
					shipList.add(xyToN);
					System.out.println("ship at " + xyToN);
				}
				success = true;
				finished = true;
			} else {
				for(int ship: shipList) {
					shipX = ship % 10;
					shipY = (int)(Math.abs(ship/10));
					if( ((shipX >= x-1) && (shipX <= x+1+(size-1)*(1-vH)) ) && ( (shipY >= y-1) && (shipY <= y+1+(size-1)*vH) ) ) {		
					} else {
						success = true;
					}	
				}
				
			}
		}//while
		
		if(!finished) {
			for(int i = 0; i < size; i++) {
				xyToN = (y+i*vH)*10+(x+i*(1-vH));
				shipList.add(xyToN);
				System.out.println("ship at " + xyToN);
			}
			finished = true;
		}

	}//method

	public void makeGui() {

//test
//		shipList.add(23);
//		shipList.add(24);
//		shipList.add(25);

//		shipList.add(66);
//		shipList.add(67);

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

		TileListener tlistener = new TileListener();
		for(int i = 0; i < 100; i++) {
			Tile field = new Tile();
			for(int ship: shipList) {
				if(i == ship) {
					field.isShip();
					System.out.println("Tile " + i + " is a ship");
				}
			}
			field.addActionListener(tlistener);
			background.add(field);
		}

		f.getContentPane().add(BorderLayout.CENTER, background);
		f.setVisible(true);
	}

	class TileListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Tile b = (Tile)(event.getSource());
			if(b.isChecked == false) {
				if(b.isShip) {System.out.println("hit");}
				b.isChecked();
				b.repaint();
			}
		}
	}

	
	class Background extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0,0, this.getWidth(), this.getHeight());
		}
	}

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
			}
		}//paint
	}//inner
}//outer
