public class Wrap {
	public static void main(String[] args) {
		Wrap test = new Wrap();
		test.go();
	}

	public void go() {
		wrap(getAX(), getBX());
		System.out.println(getAX() + " " + getBX());

	}
	static int aX = -3;
	static int bX = -6;
	
	int mapXY = 10; //0-10
	int mapSize = mapXY + 1;

	//wrap(getAX/Y, getBX/Y);
	public void wrap(int a, int b) {
		if(a > mapXY) {
			a -= mapSize;
			b -= mapSize;
		}		
		
		if(a < 0) {
			a += mapSize;
			b += mapSize;
		}
	}
/* in show:
				if(Math.abs(distX) > (mapSize - viewRange)) {
					if(distX > 0) {
						a = thg.tx - mapSize;
					} else {
						a = thg.tx + mapSize;
					}
					distX = a - aX;
				}
*/
}
