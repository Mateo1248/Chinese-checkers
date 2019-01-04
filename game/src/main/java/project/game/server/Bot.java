package project.game.server;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Random;

import project.game.client.Client;

/**
 * @author danieldrapala
 * @author mateo1248
 */
public class Bot extends Thread {

	private Client client;
	private Random rand;
	private Field goalField;
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
	private int gameBoardH = gameBoard.length;
	private int gameBoardL = gameBoard[0].length;
	private ArrayList<Field> possiblemoves;
	
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
			possiblemoves = new ArrayList<Field>();
			rand = new Random();
		} 
		catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
	}
	
	
public void run() {
		
		goalField = getGoalField();
		
		while(true) {
			if (!isRunning) {
				break;
			}
			
			
			client.sendMessage("ACTIVE");
			
			String temp=client.read();
			//usun pola nieaktywnych graczy
			Communicator c = Communicator.fromString(temp);
			if(c.getMessage().equals("CLOSED")) {
				for (int y = 0 ; y < gameBoardH ; ++y) {
			        for (int x = 0 ; x < gameBoardL ; ++x) {
			           	if(gameBoard[x][y]==c.getArg(0)) {
			           		gameBoard[x][y]=0;
			           	}
			        }
			    }
				continue;
			}
			
			
			//sprawdz kto sie rusza
			Communicator queue = client.getMessage();
			
			Communicator message;			
			
			if(  queue.getArg(0) == client.getId() ) {
				//wykonaj ruch
				makeMove();
			}
			else {				
				message = client.getMessage();
			
				if(message.getMessage().equals("MOVE")) {
					updateBoard(message.getArg(0), message.getArg(1), message.getArg(2), message.getArg(3));
				}	
			}
			

			if(client.read().equals("WON")) {
				System.out.println("wygral gracz numer " + queue.getArg(0));
				//do nothing
			}
			
		}
		client.close();
	}


	private Field getGoalField() {
		switch (client.getIdGame()) {
		case 1 :
			return new Bot.Field(17,7);
		case 2 :
			return new Bot.Field(13,1);
		case 3:
			return new Bot.Field(5,1);
		case 4 :
			return new Bot.Field(1,7);
		case 5 :
			return new Bot.Field(5,13);
		case 6 :
			return new Bot.Field(13,13);
		}
		return null;
	}
	
	
	/**
	 * bot move thanks to this method
	 */
	private void makeMove() {
		int []move = new int[4];
		
		Field field = getRandomField();
		
		move[0]=field.getX();
		move[1]=field.getY();
		
		possiblemoves.clear();
		
		showPossibleMoves(field.getX(), field.getY());
		
		field = possiblemoves.get(rand.nextInt(possiblemoves.size()));
		
		updateBoard(move[0], move[1], move[2], move[3]);
		
		client.sendMessage("MOVE " + move[0] + " " + move[1] + " " + move[2] + " " + move[3]);
		possiblemoves.clear();
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
	
	
	private Field getRandomField() {
		ArrayList<Field> botfields = new ArrayList<Field>();
		
		for (int y = 0 ; y < gameBoardH ; ++y) {
	        for (int x = 0 ; x < gameBoardL ; ++x) {
	           	if(gameBoard[x][y]==client.getIdGame()) {
	           		possiblemoves.clear();
	           		showPossibleMoves(x,y);
	           		if(possiblemoves.size()>0) {
	           			botfields.add(new Field(x, y));
	           		}
	           		possiblemoves.clear();
	           	}
	        }
	    }
		return botfields.get(rand.nextInt(botfields.size()));
		
	}
	
	
	private double getDistance(Field f) {
		return Math.pow(Math.abs(f.getX()-goalField.getX()), Math.abs(f.getY()-goalField.getY()));
	}
	
	
	private void showPossibleMoves(int x, int y) {
        int parity;
        if (y % 2 == 1) {
            parity = -1;
        }
        else {
            parity = 1;
        }
        // DLA PARZYSTYCH I NIE PARZYSTYCH WIERSZY SKOKI DZIAŁAJĄ INACZEJ 
  /*    nieparzyste o
   * 	 x x       x x 
        x o x ==   x o x   
         x x       x x	
         parzyste o
         x x         x x
        x o x ==   x o x
         x x         x x
  */
        // parzyste o    nieparzyste      x
	        //           x						o
        
        try {
            if (!(gameBoard[y + 1*parity][x + 1*parity]==0)) {
                if (gameBoard[y + 2*parity][ x + 1*parity]==0 && !possiblemoves.contains(new Field(y + 2*parity, x + 1*parity))) {
                	possiblemoves.add(new Field(y + 2*parity, x + 1*parity));
                	showPossibleMoves(y + 2*parity, x + 1*parity);
                }
            }
        } catch (NullPointerException exc) {}

        // nieparzyste   o      parzyste     x
	        //              x	    	       o
        try {
            if (!(gameBoard[y + (-1*parity)][ x + 1*parity]==0)) {
                if(gameBoard[y + (-2*parity)][x + 1*parity]==0 && !possiblemoves.contains(new Field(y + (-2*parity), x + 1*parity))) {
                	possiblemoves.add(new Field(y + (-2*parity), x + 1*parity));
                	showPossibleMoves(y + (-2*parity), x + 1*parity);
                }
            }
        } catch (NullPointerException exc) {}
        
        // nieparzyste  x      parzyste    x
	        //              o	    	       o
        try {
            if (!(gameBoard[y - 1][x]==0)) {
                if(gameBoard[y - 2][x + (-1*parity)]==0 && !possiblemoves.contains(new Field(y - 2, x + (-1*parity)))) {
                	possiblemoves.add(new Field(y - 2, x + (-1*parity)));
                	showPossibleMoves(y - 2, x + (-1*parity));
                }
            }
        } catch (NullPointerException exc) {}
        // nieparzyste  o      parzyste    o
	        //              x	    	       x
        try {
            if (!(gameBoard[y + 1][x]==0)) {
                if(gameBoard[y + 2][x + (-1*parity)]==0 && !possiblemoves.contains(new Field(y + 2, x + (-1*parity)))) {
                	possiblemoves.add(new Field(y + 2, x + (-1*parity)));
                	showPossibleMoves(y + 2, x + (-1*parity));
                }
            }
        } catch (NullPointerException exc) {}

        // nieparzyste i parzyste  x o 
	        //              
        try {
	        if (!(gameBoard[y][ x - 1]==0)) {
	            if(gameBoard[y][x - 2]==0 && !possiblemoves.contains(new Field(y, x - 2))) {
	            	possiblemoves.add(new Field(y, x - 2));
	            	showPossibleMoves(y, x - 2);
	            }
	        }
        } catch (NullPointerException exc) {}
        // nieparzyste i parzyste  o x

        try {
        	if (!(gameBoard[y][ x + 1]==0)) {
	            if(gameBoard[y][x + 2]==0 && !possiblemoves.contains(new Field(y, x + 2))) {
	            	possiblemoves.add(new Field(y, x + 2));
	            	showPossibleMoves(y, x + 2);
	            }
	        }
        }
        catch (NullPointerException exc) {}
    }
	
	private class Field {
		private int X;	
		private int Y;
		
		Field(int x, int y) {
			this.X=x;
			this.Y=y;
		}
		
		public int getX() {
			return X;
		}
		
		public void setX(int x) {
			X = x;
		}
		
		public int getY() {
			return Y;
		}
		
		public void setY(int y) {
			Y = y;
		}
	}
}
