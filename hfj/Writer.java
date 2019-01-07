import java.io.*;

public class Writer {
	
	public static void main(String[] args) {
		try {
			FileWriter wr = new FileWriter("textfile.txt");
			wr.write("Hello, World!");
			wr.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
