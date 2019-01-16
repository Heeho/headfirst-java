
public class RunThreads implements Runnable {
	int count;

	public RunThreads(String s) {
		count = Integer.parseInt(s);
	}

	public static void main(String[] args) {
		if(args.length == 1) {
			RunThreads runner = new RunThreads(args[0]);

			Thread alpha = new Thread(runner);
			Thread beta = new Thread(runner);

			alpha.setName("Alpha thread");
			beta.setName("Beta thread");

			alpha.start();
			beta.start();
		} else {
			System.out.println("Usage: java RunThreads [times to iterate]");
		}
}

	public void run() {
		for(int i = 0; i < count; i++) {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + " is running.");
			try {
				Thread.sleep(1);
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}