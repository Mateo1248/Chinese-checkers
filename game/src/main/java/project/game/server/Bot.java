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
	private int numofP;
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
	private int gameBoardH = boardPattern.length;
	private int gameBoardL = boardPattern[0].length;
	private ArrayList<Field> possiblemoves;
	private ArrayList<Field> myFields;
    private ArrayList<Field> bettermoves;
    private ArrayList<Field> allFields;
	
	/**
	 * constructor for Bot 
	 */
	Bot(int numofp) {
		isRunning=true;
		allFields=new ArrayList<Field>();
		for(int i=0 ; i<boardPattern.length ; i++) {
			gameBoard[i] = (int[])boardPattern[i].clone();
			for(int j=0 ; j<boardPattern[0].length ; j++) {
				allFields.add(new Field(i,j));
				}
			}
		possiblemoves = new ArrayList<Field>();
		myFields= new ArrayList<Field>();
		bettermoves=new ArrayList<Field>();
		rand = new Random();
		this.numofP=numofp;
		
		
	}
	
	
public void run() {
		
	try {
		this.client = new Client();
		this.client.setIdGame(numofP);
		
	} 
	catch (SocketTimeoutException e) {
		e.printStackTrace();
	}
	
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
		int count =0;
		
		myFields = getMyField();
		
		while(count<myFields.size()) {
		Field f=myFields.get(rand.nextInt(myFields.size()));
		move[0]=f.getY();
		move[1]=f.getX();
		
		possiblemoves.clear();
		bettermoves.clear();
		
		showPossbileMoves(f);
		
		if(possiblemoves.size()>0) {
		for(Field heh:possiblemoves) {
			if(getDistance(heh)-getDistance(f)<=0) {
				bettermoves.add(heh);
			}
		}
		if(bettermoves.size()>0) {
		f = bettermoves.get(rand.nextInt(bettermoves.size()));
		move[2]=f.getY();
		move[3]=f.getX();
		break;}
		else {
		f= possiblemoves.get(rand.nextInt(possiblemoves.size()));
		move[2]=f.getY();
		move[3]=f.getX();
		break;
		}
		}
		else
		{ 
			myFields.remove(f);
			count++;
		}
		}
		
		System.out.println(move[0]+" "+ move[1]+" "+ move[2]+" "+ move[3]);
		updateBoard(move[0],move[1] , move[2],move[3] );
		client.sendMessage("MOVE "  +move[0]+" "+ move[1]+" "+ move[2]+" "+ move[3]);
	}
	
	/**
	 * updating mocked board
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * 
	 */
	private void updateBoard(int y1, int x1, int y2, int x2) {
		gameBoard[y2][x2] = gameBoard[y1][x1];
		gameBoard[y1][x1]=0;
	}
	
	
	/**
	 * turning oFF Bot
	 */
	public void turnOff() {
		isRunning=false;
	}
	
	
	private ArrayList<Field> getMyField() {
		ArrayList<Field> fields=new ArrayList<Field>();
		for (int y = 0 ; y < gameBoardH ; y++) {
		        for (int x = 0 ; x < gameBoardL ; x++) {
		        	if(gameBoard[y][x]==client.getIdGame()) {
		        	fields.add(getField(y,x));
		        	}
		        }
		}
		
		return fields;
		
	}
	
	
	private double getDistance(Field f) {
		return Math.pow(Math.abs(f.getX()-goalField.getX()), Math.abs(f.getY()-goalField.getY()));
	}
	
	
	public  void  showPossbileMoves(Field f) {
		if (f.getY() % 2 == 1) {
        	Field f1=getField(f.getY()-1,f.getX()-1);
            higlightPoint(f1);
        	Field f2=getField(f.getY()+1,f.getX()-1);

            higlightPoint(f2);
        } else {
        	Field f1=getField(f.getY()-1,f.getX()+1);
        	higlightPoint(f1);
        	Field f2=getField(f.getY()+1,f.getX()+1);
        	higlightPoint(f2);
        }
    	Field f3=getField(f.getY()+1,f.getX());
        higlightPoint(f3);
    	Field f4=getField(f.getY()-1,f.getX());
        higlightPoint(f4);
    	Field f5=getField(f.getY(),f.getX()+1);
        higlightPoint(f5);
        Field f6=getField(f.getY(),f.getX()-1);
        higlightPoint(f6);
        
        showPossibleMovesMoreThan2(f);	
        }

	private void showPossibleMovesMoreThan2(Field ftemp) {
        int parity;
        int y=ftemp.getY();
        int x=ftemp.getX();
        if (y % 2 == 1) {
            parity = -1;
        }
        else {
            parity = 1;
        }
        Field f;
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
            if (!(gameBoard[y + 1*parity][x + 1*parity]==0)&&!(gameBoard[y + 1*parity][x + 1*parity]==-1)) {
                f=getField(y + 2*parity, x + 1*parity);
                if (gameBoard[y + 2*parity][ x + 1*parity]==0 &&!(possiblemoves.contains(f))) {
                	
                	higlightPoint(f);
                	showPossibleMovesMoreThan2(f);
                }
            }
        } catch (NullPointerException exc) {}

        // nieparzyste   o      parzyste     x
	    //              x	    	       o
        try {
            if (!(gameBoard[y + (-1*parity)][ x + 1*parity]==0)&&!(gameBoard[y+ (-1*parity)][ x + 1*parity]==-1)) {
               
                	f=getField(y + (-2*parity), x + 1*parity); 
                	if(gameBoard[y + (-2*parity)][x + 1*parity]==0&&!(possiblemoves.contains(f)) ) {
                	higlightPoint(f);
                	showPossibleMovesMoreThan2(f);
                }
            }
        } catch (NullPointerException exc) {}
        
        // nieparzyste  x      parzyste    x
	    //              o	    	       o
        try {
            if (!(gameBoard[y - 1][x]==0&&!(gameBoard[y-1][ x]==-1))) {
                
                	f=getField(y - 2, x + (-1*parity));
                	if(gameBoard[y - 2][x + (-1*parity)]==0&&!(possiblemoves.contains(f)) ) {
                	higlightPoint(f);
                	showPossibleMovesMoreThan2(f);
                }
            }
        } catch (NullPointerException exc) {}
        // nieparzyste  o      parzyste    o
	    //              x	    	       x
        try {
            if (!(gameBoard[y + 1][x]==0)&&!(gameBoard[y+1][ x]==-1)) {
            	f=getField(y + 2, x + (-1*parity));
                if(gameBoard[y + 2][x + (-1*parity)]==0 &&!(possiblemoves.contains(f))) {
                	
                	higlightPoint(f);
                	showPossibleMovesMoreThan2(f);
                }
            }
        } catch (NullPointerException exc) {}

        // nieparzyste i parzyste  x o 
	        //              
        try {
	        if (!(gameBoard[y][ x - 1]==0)&&!(gameBoard[y][ x - 1]==-1)) {
	        	f=getField(y, x - 2);
	            if(gameBoard[y][x - 2]==0 &&!(possiblemoves.contains(f))) {
	            	
	            	higlightPoint(f);
	            	showPossibleMovesMoreThan2(f);
	            }
	        }
        } catch (NullPointerException exc) {}
        // nieparzyste i parzyste  o x

        try {
        	if (!(gameBoard[y][ x + 1]==0)&&!(gameBoard[y][ x + 1]==-1)) {f=getField(y, x + 2);
        		if(gameBoard[y][x + 2]==0&&!(possiblemoves.contains(f))) {
        			
	            	higlightPoint(f);
	            	showPossibleMovesMoreThan2(f);
        		}
	        }
        }
        catch (NullPointerException exc) {}
    }
	private class Field {
		private int X;	
		private int Y;
		
		Field(int y, int x) {
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
	Field getField(int y,int x){
		
		for(Field f:allFields)
		{
			if(f.getX()==x&&f.getY()==y) {
				return f;
			}
		}
			return new Field(2,2);
		
		}
private void higlightPoint(Field f) {
	
	if (gameBoard[f.getY()][f.getX()]==0 && !(myFields.contains(f))){
		if(!(possiblemoves.contains(f)))
		possiblemoves.add(f);
		}
}
private void printBoards() {
	for(int i=0 ; i<boardPattern.length ; i++) {
		for(int j=0 ; j<boardPattern[0].length ; j++) {
			if(boardPattern[i][j]>=0)
				System.out.print(" ");
			System.out.print(boardPattern[i][j]);
		}
		System.out.println();
	}
	System.out.println();
	System.out.println();
	for(int i=0 ; i<gameBoard.length ; i++) {
		for(int j=0 ; j<gameBoard[0].length ; j++) {
			if(gameBoard[i][j]>=0)
				System.out.print(" ");
			System.out.print(gameBoard[i][j]);
		}
		System.out.println();
	}
}
}