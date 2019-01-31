
public class MapLooper {
	
	public static void main(String[] args) {
		MapLooper test = new MapLooper();
		int a = 0;
		for(int i = 0; i < 9; i++) {
			System.out.println(a);
			a++;
			test.wrap(a);
		}
	}
	
	int mapSize = 4;
	
	public void wrap(int a) {
		if(a > mapSize) {
			a %= mapSize + 1;
		}		
	}
}
