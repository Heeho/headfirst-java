import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
	ArrayList<PrintWriter> clientOutputStreams;

	public class ClientHandler implements Runnable {
		BufferedReader getLoc;
		PrintWriter sendLoc;
		Socket sock;

		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader locReader = new InputStreamReader(sock.getInputStream());
				getLoc = new BufferedReader(locReader);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}//constructor

		public void run() {
			String loc;
			try {
				while((loc = getLoc.readLine()) != null) {
					System.out.println("read " + loc);
					shareLoc(loc);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}//run
	}//inner

	public static void main(String[] args) {
		new GameServer().go();
	}

	public void go() {
		clientOutputStreams = new ArrayList<PrintWriter>();

		try {
			ServerSocket serverSock = new ServerSocket(1337);

			while(true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter sendLoc = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(sendLoc);

				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();

				System.out.println("got a connection");
			}

		} catch(Exception ex) {			
				ex.printStackTrace();
		}
	}

	public void shareLoc(String loc) {
		Iterator<PrintWriter> it = clientOutputStreams.iterator();

		while(it.hasNext()) {
			try {
				PrintWriter writer = it.next();
				writer.println(loc);
				writer.flush();
				System.out.println("sending " + loc + " to everyone");
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}