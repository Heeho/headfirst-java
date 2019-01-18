import java.util.ArrayList;
import java.awt.Point;
import java.awt.Dimension;

public class Map implements Runnable {
	private Dimension mapSize;
	
	private ArrayList<Point> treeRepaintList;//make getter
	private ArrayList<Point> totalList;
	
	private int treeAmount = 21;
	
	public static void main(String[] args) {
		Map m = new Map();
		Thread mapCalc = new Thread(m);
		mapCalc.start();
	}
	
	public Map() {
		mapSize = new Dimension(1200, 1200);
	
		for(int i = 0; i < treeAmount; i++) {
			int x = (int) (Math.random()*mapSize.getWidth());
			int y = (int) (Math.random()*mapSize.getHeight());
			totalList.add(new Point(x, y));
		}
	}
	public void run () {
		while(true) {
			for(Point tree: totalList) {
				x0 = p1.loc.getX() - f.getWidth()/2;
				x1 = p1.loc.getX() + f.getWidth()/2;
			
				y0 = p1.loc.getY() - f.getHeight()/2;
				y1 = p1.loc.getY() + f.getHeight()/2;
				//gotta make a reference to Player obj in Map
				
				if(
				((tree.getX() > x0) && (tree.getX() > x0))
				&& ((tree.getY() > y0) && (tree.getY() <y0))
				) {
					treeRepaintList.add(tree);
				} else {
					treeRepaintList.remove(tree);
				}
			}//for
		}//while
	}//run
}
