package project.game.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * pierwszy ktory wbije do serwera otrzyma to miano oraz przywilej do wybrania liczby graczy  i botow
 * brakuje parsera kmend i pomyslu na ich wysylanie
 * w zasadzie wystarczy tylko dokonczyc metode run
 */

public class Host extends Thread{
	
	Socket socket;
	Server server;
	BufferedReader input;
	PrintWriter output;
	int playersNo, botsNo;
	
	public Host(Socket socket, Server server) {
		this.socket=socket;
		this.server=server;
		
		try {
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            output = new PrintWriter(this.socket.getOutputStream(), true);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/*
	 * get a message with number of players an bot then start a game
	 */
	public void run() {
		
		/*
		 * parser,parser, dajcie parsera....
		 */
		//playerNo = 
		//botNo = 
		
		server.initGame(this, playersNo, botsNo);
		
	}
}
