import java.util.*;

//instantiated in GUI as w
public class Walker {
	//TESTER
	public static void main(String[] args) {
		Walker test = new Walker();
		System.out.println("walker starts");
		test.go();	
	}
	
	public void go() {
		thgMap.add(new Thing(3, 4));	
		thgMap.add(new Thing(24, 0));
		thgMap.add(new Thing(0, 24));
		thgMap.add(new Thing(24, 24));
		thgMap.add(new Thing(10, 14));

		//Timer
		for(int i = 0; i < 33; i++) {
			bX += viewRange;
			bY += viewRange;
			walk();
			show();
			System.out.println(aX+" "+ aY);
		}
	}
	//END TESTER

	int mapXY = 24; //as in x=(0.. 24)
	int mapSize = mapXY + 1; 
	int viewRange = 4;
	
	//generated with Map.fillMap(*)
	ArrayList<Thing> thgMap = new ArrayList<Thing>();
	
	//Point pLoc, pDist
	int aX, aY, bX, bY;
	private int dX, dY, err;
	
	//pass to g.screen or NPC array later
	ArrayList<Thing> screen = new ArrayList<Thing>();
		
	public void walk() {		
		//todo: make a single method
		//with X and Y args
		
		//arrange aX and the rest;
		//deltaX/Y = pDist.getX/Y()
		//- pLoc.getX/Y();
		int deltaX = bX - aX;
		if(deltaX == 0) {
			dX = 0;
		} else {
			if(deltaX < 0) {
				dX = -1;
			} else {
				dX = 1;
			}
		}		
		deltaX = Math.abs(deltaX);

		int deltaY = bY - aY;
		if(deltaY == 0) {
			dY = 0;
		} else {
			if(deltaY < 0) {
				dY = -1;
			} else {
				dY = 1;
			}
		}				
		deltaY = Math.abs(deltaY);
		
		if(deltaX != deltaY) {
			if(deltaX > deltaY) {
				dY = dZ(deltaX, deltaY, dY);
			} else {
				dX = dZ(deltaY, deltaX, dX);
			}
		}
		
		if(dX != 0 || dY != 0) {
			//pLoc.translate(dX, dY);
			aX += dX;
			aY += dY;	
			
			wrap();
		}
	}
	
	private int dZ(int a, int b, int dz) {
		int add = 0;
		err += b;
		if(2*err >= a) {
			add = dz;
			err -= a;
		}
		return add;
	}

	private void wrap() {
		if(aX > mapXY) {
			aX -= mapSize;
			bX -= mapSize;
		}
		if(aX < 0) {
			aX += mapSize;
			bX += mapSize;
		}
		
		if(aY > mapXY) {
			aY -= mapSize;
			bY -= mapSize;
		}
		if(aY < 0) {
			aY += mapSize;
			bY += mapSize;
		}
	}

	private void show() {
		int a, b;
		//thg.tLoc.getX/Y() - pLoc.getX/Y();
		int distX, distY;
	
		ArrayList<Thing> remList = new ArrayList<Thing>(); 

		for(Thing thg: screen) {
			//thg.x/y = thg.getLocation().getX/Y();		
			//thg.setLocation(thg.x - dX, thg.y - dY);
			thg.x -= dX;
			thg.y -= dY;
			System.out.println("Thing " + thg.iD + ": " + thg.x + " " + thg.y + " on screen");
			
			//distX/Y = thg.tLoc.getX/Y() - pLoc.getX/Y();
			distX = thg.tx - aX;
			distY = thg.ty - aY;
			
			if((Math.abs(distX) > viewRange &&
				Math.abs(distX) < (mapSize - viewRange)) ||
				(Math.abs(distY) > viewRange &&
				Math.abs(distY) < (mapSize - viewRange)))
			{
				System.out.println("Thing " + thg.iD + " fades at " + thg.x + " " + thg.y + " on screen");

				//screen.remove(thg);
				//concurrent removal if >1 thgs
				//when iterating the ArrayList
				remList.add(thg);
			}
		}//for
		
		for (Thing thg: remList) {
			screen.remove(thg);
		}
		remList.clear();
		
		for(Thing thg: thgMap) {			
			if(!screen.contains(thg)) {
	
				distX = thg.tx - aX;		
				
				if(Math.abs(distX) > (mapSize - viewRange)) {
					if(distX > 0) {
						a = thg.tx - mapSize;
					} else {
						a = thg.tx + mapSize;
					}
					distX = a - aX;
				}

				distY = thg.ty - aY;
				
				if(Math.abs(distY) > (mapSize - viewRange)) {
					if(distY > 0) {
						b = thg.ty - mapSize;
					} else {
						b = thg.ty + mapSize;
					}
					distY = b - aY;
				}
			
				if(Math.abs(distX) < viewRange &&
					Math.abs(distY) < viewRange)
				{
					thg.x = viewRange + distX;
					thg.y = viewRange + distY;
					//thg.setLocation(thg.x, thg.y);
				
					System.out.println("Thing " + thg.iD + " at " + thg.tx + " " + thg.ty + "( " + thg.x + " " + thg.y + " on screen)");
					//screen.add(thg, 5);
					screen.add(thg);
				}
			}//if
		}//for
		//screen.validate();
	}
}

class Thing {
	static int count;
	int iD;
//Point tLoc;
	int tx;
	int ty;
//get/setLocation();
	int x;
	int y;
	
	public Thing(int a, int b) {
		iD = count++;
		tx = a;
		ty = b;
	}
}