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

//TODO: WINDOW PRZECHOWUJE 'ID klienta' KLIENT MA SWOJ NUMEREK/KOLOREK/RAMIE 
//wiec treba tutaj zmienic do wszystkiego jesli field jest legal && czy jest  danego koloru (metoda get Client Color bedzie przechowywana u klienta



public class GameWind extends Thread{
	
	private Client client;

	private TextField text;
	private Board board;
	private boolean yourTurn;
	private ArrayList<Circle> winner = new ArrayList<>();
	private ArrayList<Text> t=new ArrayList<>();
	private int numPP;
	private int j;

	Group root;

	private Button skipb;
	GameWind(int num, int numb, Client client){
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
    root.getChildren().add(img);
    TextField name = new TextField("Player number "+(client.getId()+1)+" Color:"); 
    name.setEditable(false);
    text = new TextField("waiting  for players..."); 
    text.setEditable(false);
    VBox texts = new VBox(10);
    HBox xd=new HBox();
    xd.setCenterShape(true);
    Circle o=new Circle();
    o.setRadius(10);
    o.setFill(Paint.valueOf(FieldsColor.values()[client.getIdGame()].getColor()));
    o.setStroke(Paint.valueOf("BLACK"));
    name.setBackground(Background.EMPTY);
    name.setBorder(Border.EMPTY);
    text.setBackground(Background.EMPTY);
    text.setBorder(Border.EMPTY);
     skipb=new Button("Skip");
    xd.getChildren().add(name);
    xd.getChildren().add(o);
    
    texts.getChildren().add(xd); 
    texts.getChildren().add(text);
    texts.getChildren().add(skipb);

    root.getChildren().add(texts);
    
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
	                String ss="MOVE "+board.selected.getYCord()+" "+board.selected.getXCord()+" "+f.getYCord()+" "+f.getXCord();
	                System.out.println(ss);
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
    
    for(int i=0; i<=5; i++)
    {	
    	
    	winner.add(new Circle(10));
		t.add(new Text(Integer.toString((i+1))));
    	root.getChildren().add(winner.get(i));
		root.getChildren().add(t.get(i));
    }
    
    game.setScene(s);
    game.show();
	}
	
	
	public void run() {
		
		while(true) {
			
			//sprawdz kto sie rusza
			Communicator queue = client.getMessage();
			Communicator move;
			
			if( queue.getArg(0) == client.getId() ) {
				text.setText("Your turn");
				yourTurn=true;
				skipb.setDisable(false);
				//wykonaj ruch
			}
			else {
				text.setText("Player number " + (queue.getArg(0)+1) + " turn");
				yourTurn=false;
				//czekaj na ruch gracza
				move = client.getMessage();
				
				if(!move.getMessage().equals("SKIP")) {
					board.changeFieldsColor(board.getNode(move.getArg(2), move.getArg(3)), board.getNode(move.getArg(0), move.getArg(1)).getFieldColor());
					board.changeFieldsColor(board.getNode(move.getArg(0), move.getArg(1)), FieldsColor.NO_PLAYER);
				}
			}
			
			if(client.read().equals("WON")) {
				System.out.println("wygral gracz numer " + queue.getArg(0));
				
				int xd= setIdClientWon(numPP,queue.getArg(0));
				winner.get(j).setLayoutX(getWinnerX(xd)-5);
				winner.get(j).setLayoutY(getWinnerY(xd)-5);
				t.get(j).setX(getWinnerX(xd));
				t.get(j).setY(getWinnerY(xd));
				winner.get(j).setFill(Paint.valueOf("GOLD"));
				j++;

				//if client won do sth
			}
		}
	}
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
	public int setIdClientWon(int numP,int id) {
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
