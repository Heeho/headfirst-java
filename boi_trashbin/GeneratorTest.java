import java.util.*;
import java.awt.geom.Point2D;
import java.awt.Point;

class GeneratorTest {				
	ArrayList<Ship> shipList = new ArrayList<Ship>();

	public static void main(String[] args) {
		GeneratorTest g = new GeneratorTest();
		g.makeMap(10,10,4);
		System.out.println("success!");
	}

	public void makeMap(int mapX, int mapY, int n) {		//generates 1xN, 2x(N-1),.. , Nx1 ships on (MAP_X, MAP_Y) map
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

	public boolean placeShip(int mapX, int mapY, int size) {

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
					for(Point cell: ship.getCells()) {
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
				System.out.println(String.format("vH = %d ship of size %d starting at (%d, %d)", vH, size, startX, startY));
			}
			
			if(count++ == 200) {
				failure = true;
				System.out.println("reached deadend");
				break;
			}
		}//while
		return failure;
	}//method

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
		}//method
	
		public ArrayList<Point> getCells() {
			return cells;
		}

		public boolean isIn(Point p) {
			boolean isIn = false;
			for(Point cell: cells) {
				if(cell.getLocation().equals(p.getLocation())) {
					cells.remove(cell);
					if(cells.isEmpty()) {
//						System.out.println("It's a kill!");
					}
					isIn = true;
				}//if
			}//for
			return isIn;	
		}//method
	}//inner
}//outer
