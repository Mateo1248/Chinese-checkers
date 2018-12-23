package project.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/* TODO:
 * watek gry, tu sie dzieje
 * tworzymy plansze
 * czekamy na graczy
 * ustawiamy graczy i boty
 * lecimmy!!!!!!
 */


public class Game extends Thread{
	
	private ServerSocket serverSocket;
	private ArrayList<Player> players;
	private int playersNo, botsNo;
	
	
	Game(ServerSocket serverSocket, Player host, int playersNo, int botsNo) {
		this.serverSocket = serverSocket;
		players = new ArrayList<Player>();
		this.players.add(host);
		this.playersNo = playersNo;
		this.botsNo = botsNo;
	}
	
	
	public void run() {
		System.out.println("liczba graczy " + playersNo + "liczba botow " + botsNo);
		waitForPlayers();
		//LOT TODO
	}
	
	
	private void waitForPlayers() {
		for(int i=1 ; i<playersNo-botsNo ; i++) {
			try { 
				players.add( new Player(serverSocket.accept(), i)); 
				players.get(i).write(Integer.toString(playersNo));
				players.get(i).write(Integer.toString(botsNo));
			} 
			catch (IOException e) { e.printStackTrace(); }
		}
	}
 }
