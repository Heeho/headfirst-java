import java.io.*;
import java.net.*;
import java.util.*;

public class VerySimpleChatServer {
	ArrayList<PrintWriter> clientOutputStreams;

	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;

		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}//constructor

		public void run() {
			String message;
			String bye = "User disconnected";
			try {
				while((message = reader.readLine()) != null) {
					System.out.println("read " + message);
					tellEveryone(message);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}//run
	}//inner

	public static void main(String[] args) {
		new VerySimpleChatServer().go();
	}

	public void go() {
		clientOutputStreams = new ArrayList<PrintWriter>();

		try {
			ServerSocket serverSock = new ServerSocket(5000);

			while(true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);

				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("got a connection");
			}

		} catch(Exception ex) {			
				ex.printStackTrace();
		}
	}

	public void tellEveryone(String message) {
		Iterator<PrintWriter> it = clientOutputStreams.iterator();

		while(it.hasNext()) {
			try {
				PrintWriter writer = it.next();
				writer.println(message);
				writer.flush();
				System.out.println("sending " + message + " to everyone");
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}