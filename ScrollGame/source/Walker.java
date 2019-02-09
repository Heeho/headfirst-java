/*Walker
 * 
 * screen is used to fill gui pane or
 * npc vision list
 * 
 * 3D calc would be possible
 * with copypaste for Z
 */

class Walker {
	ArrayList<Thing> screen = newArrayList<Thing>();

	ArrayList<Thing> map;	
	int mXY;
	int mS;
	
	int vR;
	Point loc, dest;
	
	int aX, aY, bX, bY;
	int dX, dY;
	int err; //set to 0 when dest changes? meh
	int distX, distY;
	int tX, tY;
	
	public Walker(int viewrange, Point location, Point destination){
		map = Map.thgMap;
		mXY = GameScratch.MAP_XY;
		mS = mXY + 1; // mapSize
		
		vR = viewrange;
		
		loc = location;
		dest = destination;
	}
	
	public void walk() {
		int deltaX, deltaY;
		
		aX = (int) loc.getX();
		aY = (int) loc.getY();
		
		bX = (int) dest.getX();
		bY = (int) dest.getY();
			
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
			aX -= mS;
			bX -= mS;
		}
		
		if(aX < 0) {
			aX += mS;
			bX += mS;
		}
		
		if(aY > mXY) {
			aY -= mS;
			bY -= mS;
		}
		
		if(aY < 0) {
			aY += mS;
			bY += mS;
		}
		loc.setLocation(aX, aY);
		dest.setLocation(bX, bY);
	}
	
	private void show() {
		moverem();
		addthg();
	}
	
	private void moverem() {
		ArrayList<Thing> remList = new ArrayList<Thing>(); 
		int x, y;

		for(Thing thg: screen) {	
			int x = thg.getLocation().getX();		
			int y = thg.getLocation().getY();
			thg.setLocation(x - dX, y - dY);

			tX = thg.tLoc.getX();
			tY = thg.tLoc.getY();

			distX = Math.abs(tX - aX);
			distY = Math.abs(tY - aY);

			if(
				(distX > vR && distX < (mS - vR)) ||
				(distY > vR && distY < (mS - vR))
			){
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

				if(
					Math.abs(distX) < viewRange &&
					Math.abs(distY) < viewRange
				){
					thg.setLocation(vR + distX, vR + distY);
				
					System.out.println("Thing " + thg.iD + " at " + thg.tx + " " + thg.ty + "( " + thg.x + " " + thg.y + " on screen)");
					
					screen.add(thg);
				}//if
			}//if
		}//for
	}//addthg
	
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