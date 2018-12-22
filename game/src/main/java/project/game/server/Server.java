package project.game.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	private ServerSocket serverSocket;
	private Game game;
	private boolean isRunning;
		
	
	Server() {
		try { serverSocket = new ServerSocket(4444); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	
	/*
	 * turn on server
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
	
	
	/*
	 * wait for the host, get init param from him
	 * then start a game
	 */
	void start() {
		
		isRunning = true;
		
		System.out.println("server start");
		try {
			Player host = new  Player( serverSocket.accept(), 0 );
			
			int players = Integer.parseInt( host.read() );
	
			int bots = Integer.parseInt( host.read() );
			
			Game game = new Game(serverSocket, host, players, bots);
			game.start();
			
			while(true) {
				if(!isRunning)
					break;
			}
		} 
		catch (IOException e) {	e.printStackTrace(); }
		finally { 
			try { serverSocket.close(); } 
			catch (IOException e) {	e.printStackTrace(); } 
		}
	}
	
	
	/*
	 * turn off server
	 */
	public void turnOff() {
		this.isRunning=false;
	}
}

