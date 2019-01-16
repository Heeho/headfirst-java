import java.net.*;
import java.io.*;

public class ClientServerTest {
	public static void main(String[] args) {

	}

	public void read() {
		try {
			Socket chatSocket = new Socket("127.0.0.1", 1337);
			InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
			BufferedReader reader = new BufferedReader(stream);
			String message = reader.readLine();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void write() {
		try {
			Socket chatSocket = new Socket("127.0.0.1", 1337);
			PrintWriter writer = new PrintWriter(chatSocket.getOutputStream());
			writer.println("message to send");
			writer.print("next message");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}