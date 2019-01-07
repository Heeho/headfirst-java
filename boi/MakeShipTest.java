import java.util.*;

public class MakeShipTest{
	ArrayList<Integer> shipList = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		MakeShipTest a = new MakeShipTest();
		a.initGame();
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
}