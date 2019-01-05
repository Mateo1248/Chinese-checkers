package project.game.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	private ServerSocket serverSocket;
	private boolean isRunning;
	private int playersNo, botsNo;
	private Game game;
	private Player host;
		
	
	public Server() {
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
	public void start() {
		
		isRunning = true;
		
		System.out.println("server start");
		try {
			host = new  Player( serverSocket.accept(), 0 );
			
			this.playersNo = Integer.parseInt( host.read() );
	
			this.botsNo = Integer.parseInt( host.read() );
			
			game = new Game(serverSocket, host, playersNo, botsNo, this);
			game.start();
			
			while(true) {
				if(!isRunning)
					break;
			}
		} 
		catch (IOException e) {	e.printStackTrace(); }
	}
	
	public Player getHost() {
		return this.host;
	}
	
	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}
	
	public int getPlayersNo() {
		return this.playersNo;
	}
	
	
	public int getBotsNo() {
		return this.botsNo;
	}
	
	
	public Game getGame() {
		return this.game;
	}
	
	public void closeSocket() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/* 
	 * turn off server
	 */
	public void turnOff() {
		this.isRunning=false;
	}
}

