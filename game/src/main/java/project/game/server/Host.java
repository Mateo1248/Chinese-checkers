package project.game.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * first client connected to the server
 * he has the option of choosing 
 * all number of players and
 * number of bots
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
	 * get a message with number of players an bot then init a game
	 */
	public void run() {
		
		try {
			playersNo = Integer.parseInt(input.readLine());
			botsNo = Integer.parseInt(input.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("host args parse error");
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		server.initGame(this, playersNo, botsNo);
		System.out.println("Game initialized, waiting for players");
	}
}
