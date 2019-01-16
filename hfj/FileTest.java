import java.io.*;

public class FileTest {
	public static void main(String[] args) {
		try{ 
			File dir = new File("TestDir");
			dir.mkdir();
			File file = new File(dir, "Text.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("Test message.");
			writer.close();
			BufferedReader reader;
			File readableFile;
			String line;

			if(dir.isDirectory()) {
				String [] dirContents = dir.list();
				for(int i = 0; i < dirContents.length; i++) {
					System.out.println(dir + " contains:");
					readableFile = new File(dir, dirContents[i]);
					if(!readableFile.isDirectory()) {
						System.out.println(dirContents[i] + " that contains:");
						reader = new BufferedReader(new FileReader(readableFile));
						while( ((line = reader.readLine()) != null) && (!readableFile.isDirectory()) ) {
							System.out.println(line);
						}
						reader.close();
					} else {
						System.out.println(dirContents[i] + " that is a directory");
					}
				}//for
			}//if
			wipeDir(dir);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}//method

	public static void wipeDir(File dir) {
		System.out.println("\nwiper works in " + dir);
		if(dir.list() == null) {
			dir.delete();
		} else {
			System.out.println("\nwiper loops through " + dir + " contents\n");
			for(File file: dir.listFiles()) {
				if (!file.isDirectory()) {
					System.out.println("deleting " + file);
					file.delete();
				} else {
					wipeDir(file);
				}
			}
			System.out.println("deleting " + dir);
			dir.delete();
		}//else
	}//method
}//class