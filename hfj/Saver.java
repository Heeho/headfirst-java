import java.io.*;

class Saver implements Serializable {
	private int testVar = 5;
	transient Test testRef = new Test();
	
	public static void main(String[] args) {
		Saver save = new Saver();
		try {
			FileOutputStream fs = new FileOutputStream("file.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(save);
			os.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}	
}

class Test implements Serializable {
	private String testStr = "test";
}
