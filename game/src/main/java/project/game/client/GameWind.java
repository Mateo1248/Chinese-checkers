package project.game.client;


import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.game.board.Board;
import project.game.board.Field;
import project.game.board.FieldsColor;
import project.game.server.Communicator;




/**
 * @author danieldrapala
 * @author mateo1248
 */

public class GameWind extends Thread{
	
	private Client client;

	private TextField text;
	private Board board;
	private boolean yourTurn;
	private ArrayList<Circle> winner = new ArrayList<>();
	private ArrayList<Text> t=new ArrayList<>();
	private int numPP;
	private int j;
	private boolean isRunning=true;

	Group root;

	private Button skipb;
	/**
	 * @param num
	 * @param numb
	 * @param client
	 * @throws InterruptedException
	 */
	
	GameWind(int num, int numb, Client client) throws InterruptedException{
		this.numPP= num;
	this.client = client;
	client.setIdGame(num);
	final int DISPLAY_WIDTH = 500, DISPLAY_HEIGHT = 500;
    final double RADIUS = 13.0;

    Stage game= new Stage();
    
    root = new Group();
    Image image = new Image("file:src/assets/Image004.png");
    ImageView img = new ImageView();
    img.setFitHeight(DISPLAY_HEIGHT);
    img.setFitWidth(DISPLAY_WIDTH);
    img.setImage(image);
    
    Scene s = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    
    TextField name = new TextField("Player number "+(client.getId()+1)+" Color:"); 
    Circle o=new Circle(); 
    VBox texts = new VBox(10);
    HBox xd=new HBox();
    skipb=new Button("Skip");

    root.getChildren().add(img);
   
    text = new TextField("waiting  for players..."); 
    text.setEditable(false);
    text.setBackground(Background.EMPTY);
    text.setBorder(Border.EMPTY);
    
   
    
    o.setRadius(10);
    o.setFill(Paint.valueOf(FieldsColor.values()[client.getIdGame()].getColor()));
    o.setStroke(Paint.valueOf("BLACK"));
    
    name.setEditable(false);
    name.setBackground(Background.EMPTY);
    name.setBorder(Border.EMPTY);
   
    xd.setCenterShape(true);
    xd.getChildren().add(name);
    xd.getChildren().add(o);
    
    texts.getChildren().add(xd); 
    texts.getChildren().add(text);
    texts.getChildren().add(skipb);

    board =  Board.initialize(num);
    
    skipb.setDisable(true);
    
    skipb.setOnAction(e->{
    	if(yourTurn) {
    		client.sendMessage("SKIP");
    		skipb.setDisable(true);
    	}
    });
    
    s.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
    	System.out.print(evt.getSceneX() + " "+evt.getSceneY());

    	if(yourTurn) {
	        try {
	        	System.out.print(((Field) evt.getPickResult().getIntersectedNode()).getYCord() + " ");
	            System.out.print(((Field) evt.getPickResult().getIntersectedNode()).getXCord() + " ");
	            System.out.println(((Field) evt.getPickResult().getIntersectedNode()).getColor());
	            Field f= (Field) evt.getPickResult().getIntersectedNode();	   
	            if (board.isPossible(f)/*f.getFieldColor()==FieldsColor.values()[0]*/) {
	                board.changeFieldsColor(f, board.selected.getFieldColor());
	                board.changeFieldsColor(board.selected, FieldsColor.NO_PLAYER);
	                board.flushPossible();
	                evt.consume();
	                client.sendMessage("MOVE "+board.selected.getYCord()+" "+board.selected.getXCord()+" "+f.getYCord()+" "+f.getXCord());
	                skipb.setDisable(true);
	               
	            }
	            else if(f.getFieldColor()==FieldsColor.values()[client.getIdGame()]){
	                board.flushPossible();
	                board.selected = ((Field) evt.getPickResult().getIntersectedNode());
	                board.showPossbileMoves(board.selected);
	            }
	            else {
	            	board.flushPossible();
	            }
	
	        } catch (NullPointerException exc) {
	            System.out.println("No Field clicked.");
	        }
	
	       catch (Exception exc) {
	            System.out.println("No Field clicked.");
	       }	   	
    	}
    });
    
    for (int y = 0; y < board.HEIGHT; ++y) {
        for (int x = 0; x < board.WIDTH; ++x) {
            board.getNode(y, x).setCenterY(DISPLAY_HEIGHT*(y)/(board.HEIGHT-1));
            board.getNode(y, x).setCenterX(DISPLAY_WIDTH*(x+1-0.5*(y%2))/board.WIDTH);
            board.getNode(y, x).setRadius(RADIUS);
            board.getNode(y, x).setFill(Paint.valueOf(board.getNode(y, x).getColor()));
            root.getChildren().add(board.getNode(y, x));
        }
    }
    root.getChildren().add(texts);

    for(int i=0; i<=5; i++)
    {	
    	
    	winner.add(new Circle(10));
		t.add(new Text(Integer.toString((i+1))));
    	root.getChildren().add(winner.get(i));
		root.getChildren().add(t.get(i));
    }
    game.setOnCloseRequest(ex->{
    	if(yourTurn) {
    		client.sendMessage("EXIT");
    	}
    	game.close();
    	isRunning=false;
    });
    game.setScene(s);
    game.show();
}
	
	

public void run() {
		
		while(true) {
			if (!isRunning) {
				break;
			}
			
			
			client.sendMessage("ACTIVE");
			
			String temp=client.read();
			//usun pola nieaktywnych graczy
			Communicator c = Communicator.fromString(temp);
			if(c.getMessage().equals("CLOSED")) {
				for (int y = 0; y < board.HEIGHT; ++y) {
			        for (int x = 0; x < board.WIDTH; ++x) {
			           	if(board.getNode(y, x).getFieldColor().equals(FieldsColor.values()[getIdClientWon(numPP,c.getArg(0))])) {
			           		board.getNode(y, x).setFill(Paint.valueOf("WHITE"));
			           		board.getNode(y, x).setColor(FieldsColor.NO_PLAYER);
			           	}
			        }
			    }
				skipb.setDisable(true);
				continue;
			}
			
			
			//sprawdz kto sie rusza
			Communicator queue = client.getMessage();
			
			Communicator message;			
			
			if(  queue.getArg(0) == client.getId() ) {
				text.setText("Your turn");
				yourTurn=true;
				skipb.setDisable(false);
			}
			else {
				text.setText("Player number " + (queue.getArg(0)+1) + " turn");
				yourTurn=false;
						
				message = client.getMessage();
				

				if(message.getMessage().equals("MOVE")) {
					board.changeFieldsColor(board.getNode(message.getArg(2), message.getArg(3)), board.getNode(message.getArg(0), message.getArg(1)).getFieldColor());
					board.changeFieldsColor(board.getNode(message.getArg(0), message.getArg(1)), FieldsColor.NO_PLAYER);
				}	
			}
			

			if(client.read().equals("WON")) {
				System.out.println("wygral gracz numer " + queue.getArg(0));
				
				int xd= getIdClientWon(numPP,queue.getArg(0));
				winner.get(j).setLayoutX(getWinnerX(xd)-5);
				winner.get(j).setLayoutY(getWinnerY(xd)-5);
				t.get(j).setLayoutX(getWinnerX(xd)-10);
				t.get(j).setLayoutY(getWinnerY(xd));
				winner.get(j).setFill(Paint.valueOf("GOLD"));
				j++;
			}
			
		}
		client.close();
	}
	
	
	/**
	 * @param winner
	 * @return coordinates
	 */
	private double getWinnerX(int winner) {
		switch (winner) {
		case 1:
			return 329.0;
		case 2:
			return 460;
		case 3:
			return 460;
		case 4:
			return 205.0;
		case 5:
			return 52.0;
		case 6:
			return 52.0;
		}
		return -1;
	}
	
	
	/**
	 * @param winner
	 * @return coordinates
	 */
	private double getWinnerY(int winner) {
		switch (winner) {
		case 1:
			return 21.0;
			
		case 2:
			return 97.0;
		case 3:
			return 400.0;
		case 4:
			return 460.0;
		case 5:
			return 20.0;
		case 6:
			return 20.0;
		}
		return -1;
	}
	
	
	/**
	 * @param numP
	 * @param id
	 * @return GameId(FieldColor)
	 */
	private int getIdClientWon(int numP,int id) {
		switch(numP) {
		case 2:
			if(id==0)
				return 1;
			if(id==1)
				return 4;
			
		case 3:
			return (id+1)*2;
			
		case 4:
			if(id==0)
				return 2;
			if(id==1)
				return 3;
			if(id==2)
				return 5;
			if(id==3)
				return 6;
			
		case 6:
			return id+1;
		}
		return 0;
	}
}
