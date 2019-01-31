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
		thgMap.add(new Thing(1, 2));	
		thgMap.add(new Thing(24, 0));
		thgMap.add(new Thing(0, 24));
		thgMap.add(new Thing(10, 14));
		
		//Timer
		for(int i = 0; i < 2; i++) {
			bX = aX + viewRange;
			bY = aY + viewRange;
			while (aX!=bX && aY!=bY) {
				walk();
				System.out.println(aX+" "+ aY);
			}
		}
	}
	//END TESTER

	int mapSize = 24;
	int viewRange = 4;
	int fadeRange = 5;
	
	//pLoc
	int aX = 0;
	int aY = 0;

	int bX, bY;
	
	//g.screen
	ArrayList<Thing> screen = new ArrayList<Thing>();
	
	ArrayList<Thing> thgMap = new ArrayList<Thing>();
	
	int deltaX, deltaY;
	
	int dX, dY;
	
	int distX, distY;
	
	int err;
		
	public void walk(/*g.screen*/) {		
		deltaX = bX - aX;
		deltaY = bY - aY;
		
		if(deltaX == 0) {
			dX = 0;
		} else {
			if(deltaX < 0) {
				dX = -1;
			} else {
				dX = 1;
			}
		}
		
		if(deltaY == 0) {
			dY = 0;
		} else {
			if(deltaY < 0) {
				dY = -1;
			} else {
				dY = 1;
			}
		}
		
		deltaX = Math.abs(deltaX);
		deltaY = Math.abs(deltaY);
		
		if(deltaX != deltaY) {
			if(deltaX > deltaY) {
				dY = dZ(deltaX, deltaY, dY);
			} else {
				dX = dZ(deltaY, deltaX, dX);
			}
		}
		
		if(dX != 0 || dY != 0) {
			aX = wrap(aX + dX);
			aY= wrap(aY + dY);
			show();
		}
	}
	
	private int wrap(int a) {
		if(a > mapSize) {
			a %= mapSize + 1;
		}
		return a;
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

	private void show() {
		int a, b;
		for(Thing thg: screen) {
			//thg.setLocation();
			thg.x -= dX;
			thg.y -= dY;
			System.out.println("Thing " + thg.iD + ": " + thg.x + " " + thg.y);
			
			distX = thg.tx - aX;
			distY = thg.ty - aY;
			
			if((Math.abs(distX) > fadeRange &&
				Math.abs(distX) < (mapSize - fadeRange)) ||
				(Math.abs(distY) > fadeRange &&
				Math.abs(distY) < (mapSize - fadeRange)))
			{
				System.out.println("Thing " + thg.iD + " fades at " + thg.x + " " + thg.y);
				screen.remove(thg);
			}

		}//for

		for(Thing thg: thgMap) {
			distX = thg.tx - aX;
			distY = thg.ty - aY;
			if(!screen.contains(thg)) {
				if(Math.abs(distX) < viewRange && 
					Math.abs(distY) < viewRange) 
				{
					System.out.println("Thing " + thg.iD + " at " + thg.tx + " " + thg.ty);
					//screen.add(thg, 5);
					thg.x = viewRange + distX;
					thg.y = viewRange + distY;
					screen.add(thg);
				}
				//gotta keep em separated
				if(Math.abs(distX) > (mapSize - viewRange) ||
					Math.abs(distY) > (mapSize - viewRange))
				{
					System.out.println("Thing " + thg.iD + " at " + thg.tx + " " + thg.ty);

					//screen.add(thg, 5);
					if(distX > 0) {
						a = thg.tx - mapSize;
					} else {
						a = thg.tx + mapSize;
					}
									
					if(distY > 0) {
						b = thg.ty - mapSize;
					} else {
						b = thg.ty + mapSize;
					}

					distX = a - aX;
					distY = b - aY;
					
					thg.x = viewRange + distX;
					thg.y = viewRange + distY;

					screen.add(thg);
				}
			}
		}
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