import java.util.Timer;
import java.util.TimerTask;

public class GameScratch {
	public static void main(String[] args) {
		GUI gui = new GUI();
	}
}

class GUI {
	int MAP_SIZE, VIEW_SIZE;
	
	int playerLoc, playerDest;
	int[] thingsSet;
	
	public GUI() {
		Map m = new Map(MAP_SIZE, VIEW_SIZE);
		
		Timer t1 = new Timer();
		TimerTask shower = new TimerTask() {
			public void run() {
				//uses thingsMap, playerLoc
				for(int thg: m.thingsMap) {
					if(true) {
						thingsSet.add(thg);
					} else {
						thingsSet.remove(thg);
					}
				}
			}
		};
		t1.shedule(shower, 0, 15);
	
		Timer t2 = new Timer();
		TimerTask walker = new TimerTask() {
			public void run() {
				//uses playerLoc, playerDest
				playerLoc.translate(dX, dY);
				for(Thing thg: thingsSet) {
					thg.setLocation(-dX, -dY);
				}
			}
		};
		t2.shedule(walker, 0, 200);
	}//constructor
	
	class PlayerControl extends MouseAdapter implements MouseListener {
		public void actionPerformed(ActionEvent ev) {
			Point p = new Point();
			playerDest.setLocation(p);
		}	
	}
	
	class Thing extends JLabel {
		Point mapLoc;
		
		public Thing(Point p) {
			mapLoc = p;
		}
	}
}

class Map {
	int mapSize;
	int viewSize;
	int[] thingsMap;
	int playerLoc;
	
	public Map(int m, int v) {
		mapSize = m;
		viewSize = v;
		fillThingsMap();
	}
	
	public void fillThingsMap() {
		//generate obj's and their loc's
		//into hashmap?
	}
}
