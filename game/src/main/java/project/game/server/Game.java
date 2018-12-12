package project.game.server;

import java.net.ServerSocket;
import java.net.Socket;

/*
 * watek gry, tu sie dzieje
 * tworzymy plansze
 * czekamy na graczy
 * ustawiamy graczy i boty
 * lecimmy!!!!!!
 */
public class Game extends Thread{
	
	ServerSocket serversocket;
	int playersNo;
	int botsNo;
	Host host;

	Game(ServerSocket serversocket, Host host, int playersNo, int botsNo) {
		this.serversocket=serversocket;
		this.host=host;
		this.playersNo=playersNo;
		this.botsNo=botsNo;
	}
	
	public void run() {
		
	}
	
	private void waitForPlayers(int playersNo) {
		//TO DO
	}
	
	private void createBots(int botsNo) {
		//TO DO
	}
	
}
