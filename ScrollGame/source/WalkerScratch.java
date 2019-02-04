import java.util.*;
/*Walker
 * 
 * screen is used to fill gui or
 * npc vision list
 * 
 * 3D calc would be possible
 * with copypaste for Z
 */
public class Walker {
	ArrayList<Thing> screen = new ArrayList<Thing>();

	ArrayList<Thing> map;	
	int mXY;
	int mS;
	
	int spd;
	int vR;
	Point loc, dest;
	
	int aX, aY, bX, bY;

	int dX, dY;
	int err; //set 0 when dest changes? meh

	int distX, distY;
	int tX, tY;
	
	public Walker(
	
	ArrayList<Thing> thgmap,
	int mapxy,
	int speed,
	int viewrange,
	Point location,
	Point destination
	
	) {
		map = thgmap;
		mXY = mapxy;
		mS = mXY + 1; // mapSize
		
		spd = speed;
		vR = viewrange;
		
		loc = location;
		dest = destination;

	}
	
	private void walk() {
		int deltaX, deltaY;
		
		aX = loc.getX();
		aY = loc.getY();
		
		bX = dest.getX();
		bY = dest.getY();
			
		deltaX = bX - aX;
		deltaY = bY - aY;

		dX = calc(deltaX);
		dY = calc(deltaY);
	
		deltaX = Math.abs(deltaX);
		deltaY = Math.abs(deltaY);

		recalcd(deltaX, deltaY);
		
		move();
		
		show();
	}
	
	private int calc(int delta) {
		int d = 0;
		if(delta != 0) {
			if(delta < 0) {
				d = -1;
			} else {
				d = 1;
			}
		}		
		return d;
	}

	/*3D appears, what we do?
	 *keep calm & copypaste shit
	 */	
	private void recalcd(int delta1, int delta2) {
		if(delta1 != delta2) {
			if(delta1 > delta2) {
				dY = dO(delta1, delta2, dY);
			} else {
				dX = dO(delta2, delta1, dX);
			}
		}
	}
	
	private int dO(int a, int b, int d) {
		int add = 0;
		err += b;
		if(2*err >= a) {
			add = d;
			err -= a;
		}
		return add;
	}

	private void move() {
		if(dX != 0) {
			aX += dX;
			aY += dY;	
			wrap();
		}
	}
	
	private void wrap () {
		if(aX > mXY) {
			loc.setX(aX - mS);
			dest.setX(bX - mS);
		}
		
		if(aX < 0) {
			loc.setX(aX + mS);
			dest.setX(bX + mS);
		}
		
		if(aY > mXY) {
			loc.setY(aY - mS);
			dest.setY(bY - mS);
		}
		
		if(aY < 0) {
			loc.setY(aY+ mS);
			dest.setY(bY + mS);
		}
	}
	
	private void show() {
		moverem();
		addthg();
	}
	
	private void moverem() {
		ArrayList<Thing> remList = new ArrayList<Thing>(); 
		int x, y;
		
		for(Thing thg: screen) {
			x = thg.getLocation().getX();		
			y = thg.getLocation().getY();
	
			thg.setLocation(x - dX, y - dY);

			tX = thg.tLoc.getX();
			tY = thg.tLoc.getY();

			distX = Math.abs(tX - aX);
			distY = Math.abs(tY - aY);

			if((distX > vR &&
				distX < (mS - vR)) ||
				(distY > vR &&
				distY < (mS - vR)))
			{
				System.out.println("Thing " + thg.iD + " fades at " + x + " " + y + " on screen");
				remList.add(thg);
			}
		}//for
		
		for (Thing thg: remList) {
			screen.remove(thg);
		}	
	}
	
	private void addthg() {
		for(Thing thg: map) {			
			if(!screen.contains(thg)) {
				distX = tX - aX;		
				distY = tY - aY;
				
				distX = wrapthg(distX);
				distY = wrapthg(distY);
			}
		}
	}
	
	private int wrapthg(int dist) {
		if(Math.abs(dist) > (mS - vR)) {
			if(dist > 0) {
				dist -= mS;
			} else {
				dist += mS;
			}
		}
		return dist;
	}
}

class Thing extends JLabel {
	static int count;
	int iD;
	Point tLoc;
	
	public Thing(Point p) {
		iD = count++;
		tLoc = p;
	}
}