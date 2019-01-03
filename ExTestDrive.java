class MyEx extends Exception {
}

public class ExTestDrive {

	public static void main(String[] args) {

		String test = args[0];
		System.out.print("t");
		try {
		
			doRisky(test);
		}
		catch (MyEx e) {
			
			
		}
		finally {

				System.out.print("w");
				System.out.print("s");
		}
	}

	static void doRisky(String t) throws MyEx {

		System.out.print("h");
		if("yes".equals(t)) {

			System.out.print("a");
			throw new MyEx();
		} 
			
		System.out.print("r");
		System.out.print("o");

	} // doRisky
} //class