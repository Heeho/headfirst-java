import java.util.*;

public class WalkerTest {
	int aX = 0;
	int aY = 0;
	
	int bX = 2;
	int bY = 8;
	
	int err;
	int dX, dY;
	
	public static void main(String[] args) {
		WalkerTest test = new WalkerTest();
		System.out.println("walker starts");
		while(test.aX!=test.bX && test.aY!=test.bY) {
			test.walker();
			System.out.println(test.aX+" "+ test.aY);
		}
	}
	
	public void walker() {
		int deltaX = bX - aX;
		int deltaY = bY - aY;
		
		if(deltaX == 0) {
			dX = 0;
		} else {
			if(deltaX < 0) {dX = -1;}
			if(deltaX > 0) {dX = 1;}
		}
		
		if(deltaY == 0) {
			dY = 0;
		} else {
			if(deltaY < 0) {dY = -1;}
			if(deltaY > 0) {dY = 1;}
		}
		
		deltaX = Math.abs(deltaX);
		deltaY = Math.abs(deltaY);
		
		if(deltaX > deltaY) {
			aX += dX;
			aY += dZ(deltaX, deltaY, dY);
		} else {
			aY += dY;
			aX += dZ(deltaY, deltaX, dX);
		}
	}
	
	private int dZ(int a, int b, int dz) {
		int inc =0;
		err += b;
		if(2*err >= a) {
			inc = dz;
			err -= a;
		}
		return inc;
	}
}
