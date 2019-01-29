import java.util.*;

//instantiated in GUI as w
public class WalkerTest {
	//pLoc
	int aX = 0;
	int aY = 0;
	
	//pDest
	int bX = 20;
	int bY = 35;

	//g.screen
	ArrayList<Thing> screen = new ArrayList<Thing>();
	
	ArrayList<Thing> thgMap = new ArrayList<Thing>();
	
	int deltaX = bX - aX;
	int deltaY = bY - aY;
	
	int dX, dY;
	
	int err;
	
//tester
	public static void main(String[] args) {
		WalkerTest test = new WalkerTest();
		System.out.println("walker starts");
		test.go();	
	}
	
	public void go() {
		thgMap.add(new Thing(3, 5));
		thgMap.add(new Thing(10, 14));
		
		//Timer
		while(aX!=bX && aY!=bY) {
			walk();
			System.out.println(aX+" "+ aY);
		}
	}
//tester
	
	public void walk(/*g.screen*/) {		
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
		aX += dX;
		aY += dY;	

//EDT, envokeLater
		show();
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

//EDT, envokeLater
	private void show() {
		double dist;
		
		for(Thing thg: thgMap) {
			//= tLoc.distance(pLoc)
			dist = Math.sqrt(Math.pow((thg.x - aX), 2) + Math.pow((thg.y - aY), 2));
			if(!screen.contains(thg)) {
				if(dist < 7) {		
					System.out.println("Thing at " + thg.x + " " + thg.y);
					//screen.add(thg, 5);
					screen.add(thg);
				}
			} else {
				if(dist < 7) {
					//thg.setLocation();
					thg.x -= dX;
					thg.y -= dY;
					System.out.println("Thg " + thg.iD + ": " + thg.x + " " + thg.y);
				} else {
					System.out.println("Thing fades at " + thg.x + " " + thg.y);
					screen.remove(thg);
				}
			}
		}//for
		//screen.validate();
	}


class Thing {
	static int count;
	int iD;
//Point tLoc;
	int x;
	int y;
	
	public Thing(int a, int b) {
		iD = count++;
		x = a;
		y = b;
	}
}
