package project.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;


/* TODO:
 * watek gry, tu sie dzieje
 * tworzymy plansze
 * czekamy na graczy
 * ustawiamy graczy i boty
 * lecimmy!!!!!!
 */


public class Game extends Thread {
	
	private ServerSocket serverSocket;
	private ArrayList<Player> players;
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
	private int[][] gameBoard = new int[boardPattern.length][boardPattern[0].length];
	
	
	Game(ServerSocket serverSocket, Player host, int playersNo, int botsNo) {
		this.serverSocket = serverSocket;
		players = new ArrayList<Player>();
		this.players.add(host);
		this.playersNo = playersNo;
		this.botsNo = botsNo;
		
		//kopiujemy tablice
		for(int i=0 ; i<gameBoard.length ; i++) {
			gameBoard[i] = (int[])boardPattern[i].clone();
		}
	}
	
	
	public void run() {
		
		//poczekaj na wymagana liczbe graczy
		waitForPlayers();
		
		//ustal losowa kolejnosc graczy
		ArrayList<Integer> playerSequence = randomPlayerSequence();
		
		//iterator
		int i=0;
		
		//petla gry
		while(true) {
			String move;
			players.get(playerSequence.get(i)).setIdTriangle(playersNo);

			//wyslij wiadomosc czyja kolej
			sendMessageToPlayers("TURN " + playerSequence.get(i), -1);
			
			if(playerSequence.get(i) >= players.size()) {
				//bot robi ruch
				int []coordinates = botMakeMove();
				move(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
			}
			else {
				//czekaj na ruch gracza i wyslij do innych
				sendMessageToPlayers( (move=getMessageFromPlayer(playerSequence.get(i))), playerSequence.get(i));
				Communicator com = Communicator.fromString(move);
				move(com.getArg(0), com.getArg(1), com.getArg(2), com.getArg(3));
			}

			
			if(isPlayerWon(playerSequence.get(i))) {
				sendMessageToPlayers("WON", -1);
				playerSequence.remove(i);
			}
			else {
				sendMessageToPlayers("NOTWON", -1);
			}			

			i++;
			i=i%playerSequence.size();
		}
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
	
	
	private ArrayList<Integer> randomPlayerSequence() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		Random rnd = new Random();
		
		int i = rnd.nextInt(playersNo);
		
		for(int j=i ; j<playersNo ; j++) {
			temp.add(j);
		}
		for(int j=0 ; j<i ; j++) {
			temp.add(j);
		}
		return temp;
	}
	
	
	private void sendMessageToPlayers(String message, int without) {
		for(Player x : players) {
			if(x.getId() !=without)
				x.sendMessage(message);
		}
	} 
	
	
	private String getMessageFromPlayer(int id) {
		return players.get(id).read();
	}
	
	
	private void move(int x1, int y1, int x2, int y2) {
		gameBoard[x2][y2] = gameBoard[x1][y1];
		gameBoard[x1][y1]=0;
	}
	
	
	private int[] botMakeMove() {
		int []x = new int[4];
		
		return x;
	}
	
	
	private boolean isPlayerWon(int id) {
		int pN=players.get(id).getIdTriangle();
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
 }
