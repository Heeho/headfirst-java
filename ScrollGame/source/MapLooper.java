
public class MapLooper {
	
	public static void main(String[] args) {
		MapLooper test = new MapLooper();
		for(int i = 0; i < 19; i++) {
			System.out.println(test.wrap(i));
		}
	}
	
	int mapSize = 10; //0-10
	
	public int wrap(int a) {
		if(a > mapSize) {
			a %= mapSize + 1;
		}		
		return a;
	}
}
