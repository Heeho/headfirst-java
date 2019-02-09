class Map {
	public static ArrayList<Thing> thgMap= new ArrayList<Thing>();
	
	public static void fillMap(int mapXY) {
		int treeAmount = mapXY/15;

		while(treeAmount != 0) {
			thgMap.add(new Tree(randLoc(mapXY)));
			treeAmount--;
		}
	}
	
	public static Point randLoc(int area) {
		int x = (int) (Math.random()*area);
		int y = (int) (Math.random()*area);
		Point loc = new Point(x, y);
		System.out.println("Thing at " + loc);
		return loc;
	}
}
