// Code Magnets exercise on p.524

public class TestThreads {
	boolean threadOneNow = true;

	public synchronized void setThreadOneNow() {
		if(threadOneNow) {
			threadOneNow = false;
		} else {
			threadOneNow = true;
		}
	}

	public static void main(String[] args) {
		TestThreads test = new TestThreads();
		test.go();
	}

	public void go() {
		ThreadOne t1 = new ThreadOne();
		Thread one = new Thread(t1);

		ThreadTwo t2 = new ThreadTwo();
		Thread two = new Thread(t2);

		one.start();
		two.start();
	}

	class ThreadOne implements Runnable {
		Accum a = Accum.getAccum();

		public void run() {

			for(int x = 0; x < 98; x++) {
				while(!threadOneNow) {
				}
				a.updateCounter(1000);
				setThreadOneNow();
			}
			setThreadOneNow();
			System.out.println("one " + a.getCount());

		}
	}

	class ThreadTwo implements Runnable {
		Accum a = Accum.getAccum();

		public void run() {

			for(int x = 0; x < 99; x++) {
				while(threadOneNow) {
				}
				a.updateCounter(1);
				setThreadOneNow();
			}

			System.out.println("two " + a.getCount());
		}
	}
}

class Accum {
	private static Accum a = new Accum();
	private int counter = 0;

	private Accum() { }

	public static Accum getAccum() {
		return a;
	}

	public synchronized void updateCounter(int add) {
		counter += add;
	}
	
	public int getCount() {
		return counter;
	}
}
