
class Map implements Runnable {
	private Dimension mapSize;
	private int visibleRange = 200;
	private ArrayList<Point> treeList;	//trees in range
	private ArrayList<Point> totalList;	//all the trees
	
	private int treeAmount = 21;
	
	Player p1;
	JFrame f;

	public Map(JFrame frame, Player player) {
		mapSize = new Dimension(1200, 1200);	

		p1 = player;
		f = frame;
		totalList = new ArrayList<Point>();
		treeList = new ArrayList<Point>();

		int x, y;

		for(int i = 0; i < treeAmount; i++) {
			x = (int) (Math.random()*mapSize.getWidth());
			y = (int) (Math.random()*mapSize.getHeight());
			totalList.add(new Point(x, y));
		}

		Thread mapCalc = new Thread(this);
		mapCalc.start();
	}

	public ArrayList<Point> getTreeList() {
		return treeList;
	}

	public void run () {
		double x0, x1, y0, y1;

		while(true) {
			for(Point tree: totalList) {
				x0 = p1.getLoc().getX() - (f.getWidth()/2 + visibleRange);
				x1 = p1.getLoc().getX() + (f.getWidth()/2 + visibleRange);
			
				y0 = p1.getLoc().getY() - (f.getHeight()/2 + visibleRange);
				y1 = p1.getLoc().getY() + (f.getHeight()/2 + visibleRange);
				
				if(
					((tree.getX() > x0) && (tree.getX() > x0)) && 
					((tree.getY() > y0) && (tree.getY() <y0))
				) {
					treeList.add(tree);
				} else {
					treeList.remove(tree);
				}
			}//for
		}//while
	}//run
}