package project.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;


/* TODO:
 * watek gry, tu sie dzieje
 * tworzymy plansze
 * czekamy na graczy
 * ustawiamy graczy i boty
 * lecimmy!!!!!!
 */


public class Game extends Thread {
	
	private Server server;
	private ServerSocket serverSocket;
	private ArrayList<Player> players;
	private ArrayList<Bot> bots;
	private int playersNo, botsNo;    
	private final int [][]boardPattern={
			{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1},

			{-1,6, 6, 6, 6,  0,  0,  0,  0,  0, 2, 2, 2, 2, -1},

			{-1,6, 6, 6,  0,  0,  0,  0,  0,  0, 2, 2, 2, -1, -1},

			{-1,-1, 6, 6,  0,  0,  0,  0,  0,  0,  0, 2, 2, -1, -1},

			{-1,-1, 6,  0,  0,  0,  0,  0,  0,  0,  0, 2, -1, -1, -1},

			{-1,-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1},

			{-1,-1, 5,  0,  0,  0,  0,  0,  0,  0,  0, 3, -1, -1, -1},

			{-1,-1, 5, 5,  0,  0,  0,  0,  0,  0,  0, 3, 3, -1, -1},

			{-1,5, 5, 5,  0,  0,  0,  0,  0,  0, 3, 3, 3, -1, -1},

			{-1,5, 5, 5, 5,  0,  0,  0,  0,  0, 3, 3, 3, 3, -1},

			{-1,-1, -1, -1, -1, 4, 4, 4, 4, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, 4, 4, 4, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, 4, 4, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
	};
	private int[][] gameBoard =new int[boardPattern.length][boardPattern[0].length];
	
	
	Game(ServerSocket serverSocket, Player host, int playersNo, int botsNo, Server server) {
		this.serverSocket = serverSocket;
		players = new ArrayList<Player>();
		this.players.add(host);
		this.players.get(0).setIdTriangle(playersNo);
		this.playersNo = playersNo;
		this.botsNo = botsNo;
		this.server = server;
		
		//kopiujemy tablice
		for(int i=0 ; i<gameBoard.length ; i++) {
			gameBoard[i] = (int[])boardPattern[i].clone();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public void run() {
		
		//polacz sie z botami
		createBots();
		
		
		//poczekaj na wymagana liczbe graczy
		waitForPlayers();
		
		//iterator
		int i=0;
		
		//petla gry
		while(true) {
			
			if(checkStateOfPlayers())
				continue;
			
			if(players.size()>0) {
				i%=players.size();
			}
			else {
				break; 
			}
			
			String move="";

			//wyslij wiadomosc czyja kolej
			sendMessageToPlayers("TURN " + players.get(i).getId(), -1);
			
			
			//czekaj na ruch gracza i wyslij do innych
			
			try {
				sendMessageToPlayers( (move=getMessageFromPlayer(players.get(i))), players.get(i).getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(move.equals("MOVE")) {
				Communicator com = Communicator.fromString(move);
				move(com.getArg(0), com.getArg(1), com.getArg(2), com.getArg(3));
			}
				
			if(isPlayerWon(players.get(i).getIdTriangle())) {
				sendMessageToPlayers("WON", -1);
				players.get(i).close();
				players.remove(players.get(i));
				if(players.size()>0) {
					i%=players.size();
				}
			}
			else {
				sendMessageToPlayers("NOTWON", -1);
			}			

			i++;
			if(players.size()>0) {
				i%=players.size();
			}
			else {
				break; 
			}
		}
		close();
		server.turnOff();
	}
	
	
	private void createBots() {
		for(int i=1 ; i< botsNo+1 ; i++) {
			try {
				bots.add(new Bot());
				players.add(new Player(serverSocket.accept(), i));
				players.get(i).setIdTriangle(playersNo);
				players.get(i).write(Integer.toString(playersNo));
				players.get(i).write(Integer.toString(botsNo));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private boolean checkStateOfPlayers() {
		boolean checked=false;
		
		for(int i=0 ; i<players.size() ; i++) {
			if(players.get(i).read()==null && !checked) {
				checked=true;
				int id = players.get(i).getId();
				players.get(i).close();
				players.remove(i);
				sendMessageToPlayers("CLOSED " + id, -1);
				return true;
			}
		}
		sendMessageToPlayers("ALLACTIVE", -1);
		return false;
	}
	
	
	private void waitForPlayers() {
		for(int i=players.size() ; i<playersNo-botsNo ; i++) {
			try { 
				players.add( new Player(serverSocket.accept(), i)); 
				players.get(i).setIdTriangle(playersNo);
				players.get(i).write(Integer.toString(playersNo));
				players.get(i).write(Integer.toString(botsNo));
			} 
			catch (IOException e) { e.printStackTrace(); }
		}
		server.closeSocket();
	}
	
	
	private void sendMessageToPlayers(String message, int without) {
		for(Player x : players) {
			if(x.getId() !=without)
				x.sendMessage(message);
		}
	} 
	
	
	private String getMessageFromPlayer(Player p) throws IOException {
		return p.read();
	}
	
	
	private void move(int x1, int y1, int x2, int y2) {
		gameBoard[x2][y2] = gameBoard[x1][y1];
		gameBoard[x1][y1]=0;
	}
	
	
	private boolean isPlayerWon(int pN) {
		int oppN=(pN+2)%6+1;
		for(int j=0 ; j<gameBoard.length ; j++) {
			for(int k=0 ; k<gameBoard[0].length ; k++) {
				if(boardPattern[j][k]==oppN) {
					if(gameBoard[j][k]==pN) {
						continue;
					}
					else {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	private void close() {
		for(Player x : players) {
			x.close();
		}
	}
 }
