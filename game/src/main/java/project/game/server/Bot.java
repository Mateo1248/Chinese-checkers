package project.game.server;

import java.net.SocketTimeoutException;

import javafx.scene.paint.Paint;
import project.game.board.FieldsColor;
import project.game.client.Client;

/**
 * @author danieldrapala
 * @author mateo1248
 */
public class Bot extends Thread {
	
	private Client client;
	private boolean isRunning;
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
	
	/**
	 * constructor for Bot 
	 */
	Bot() {
		try {
			this.client = new Client();
			isRunning=true;
			for(int i=0 ; i<gameBoard.length ; i++) {
				gameBoard[i] = (int[])boardPattern[i].clone();
			}
		} 
		catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		while(true) {
			if (!isRunning) {
				break;
			}
			//potwierdz swoja katywnosc
			client.sendMessage("ACTIVE");
			
			while(!client.read().equals("DONE")) {
				//nic nie rob bo bot			
			}
			
			//sprawdz kto sie rusza
			Communicator queue = client.getMessage();
			
			Communicator move;
			System.out.println(queue.getMessage());
			
			
			if(  queue.getArg(0) == client.getId() ) {
				makeMove();				
			}
			else {
				move = client.getMessage();
				
				System.out.println(move.getMessage());

				if(move.getMessage().equals("MOVE")) {
					updateBoard(move.getArg(0), move.getArg(1), move.getArg(2), move.getArg(3));
				}			
			}
			
			
			if(client.read().equals("WON")) {
				System.out.println("wygral gracz numer " + queue.getArg(0));
				if(queue.getArg(0) == client.getId()) {
					//jesli wygrales zakoncz dzialanie
					break;
				}
			}
		}
		client.close();
	}
	
	
	/**
	 * bot move thanks to this method
	 */
	private void makeMove() {
		int []move = new int[4];
		
		/*
		 *sprawdz gdzie mozesz sie ruszyc 
		 *wylosuj jakies wspolrzedne i
		 *zapisz je do tablicy move
		 */
		
		client.sendMessage("MOVE " + move[0] + " " + move[1] + " " + move[2] + " " + move[3]);
	}
	
	/**
	 * updating mocked board
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * 
	 */
	private void updateBoard(int x1, int y1, int x2, int y2) {
		gameBoard[x2][y2] = gameBoard[x1][y1];
		gameBoard[x1][y1]=0;
	}
	
	
	/**
	 * turning oFF Bot
	 */
	public void turnOff() {
		isRunning=false;
	}
}
