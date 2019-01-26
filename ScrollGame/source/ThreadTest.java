
public class ThreadTest implements Runnable {
	
	public static void main(String[] args) {
		Thread t = new Thread(new ThreadTest());
		t.start();
	}
	
	public void run() {
		System.out.println("Hello, World!");
	}
}
