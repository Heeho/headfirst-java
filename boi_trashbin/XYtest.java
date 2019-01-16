import java.util.*;

public class XYtest {
	public static void main(String[] args) {
		int x;
		int y;
		for(int i = 0; i < 100; i++) {
			y = (int)(Math.abs(i/10));
			x = i - y*10;
			System.out.println(String.format("(%d, %d)", x, y));
		}
	}
}