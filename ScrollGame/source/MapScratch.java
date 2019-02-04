class Map {
	ArrayList<Thing> thgMap= new ArrayList<Thing>();
	
	public static void fillMap(int mapSize, int viewSize) {
		int treeAmount = mapSize/15;

		while(treeAmount != 0) {
			thgMap.add(new Thing(randLoc()));
			treeAmount--;
		}
	}
	
	public static Point randLoc(int mapSize) {
		int x = (int) (Math.random()*mapSize);
		int y = (int) (Math.random()*mapSize);
		Point loc = new Point(x, y);
		System.out.println("Thing at " + loc);
		return loc;
	}
}

}
