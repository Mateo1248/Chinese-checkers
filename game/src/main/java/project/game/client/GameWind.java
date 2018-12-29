package project.game.client;


import javafx.scene.Group;
import javafx.scene.Scene;
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
	
	GameWind(int num, int numb, Client client){
		
	this.client = client;
	final int DISPLAY_WIDTH = 500, DISPLAY_HEIGHT = 500;
    final double RADIUS = 13.0;

    Stage game= new Stage();
    
    Group root = new Group();
    Image image = new Image("file:src/assets/Image004.png");
    ImageView img = new ImageView();
    img.setFitHeight(DISPLAY_HEIGHT);
    img.setFitWidth(DISPLAY_WIDTH);
    img.setImage(image);
    Scene s = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    root.getChildren().add(img);
    TextField name = new TextField("Player number "+(client.getId()+1)+" Color:"); 
    text = new TextField("waiting  for players..."); 
    VBox texts = new VBox(10);
    HBox xd=new HBox();
    xd.setCenterShape(true);
    Circle o=new Circle();
    o.setRadius(10);
    o.setFill(Paint.valueOf(FieldsColor.values()[client.getId()+1].getColor()));
    o.setStroke(Paint.valueOf("BLACK"));
    name.setBackground(Background.EMPTY);
    name.setBorder(Border.EMPTY);
    text.setBackground(Background.EMPTY);
    text.setBorder(Border.EMPTY);
    
    xd.getChildren().add(name);
    xd.getChildren().add(o);

    texts.getChildren().add(xd); 
    texts.getChildren().add(text);
    
    root.getChildren().add(texts);
    
    board =  Board.initialize(num);
    //TODO:enum dla communicatora ktory bedzie posiadal nazwy sygnalow Turn, Move, Win, Quit i w communicatorze 
    //w zaleznosci od tego 1szego wyrazu beda  te argumenty poszczegolnie inaczej parsowane
    
    
    
    s.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
    	if(yourTurn) {
	        try {
	        	System.out.print(((Field) evt.getPickResult().getIntersectedNode()).getYCord() + " ");
	            System.out.print(((Field) evt.getPickResult().getIntersectedNode()).getXCord() + " ");
	            System.out.println(((Field) evt.getPickResult().getIntersectedNode()).getColor());
	            Field f= (Field) evt.getPickResult().getIntersectedNode();
	            if (board.isPossible(f)) {
	                board.changeFieldsColor(f, board.selected.getFieldColor());
	                board.changeFieldsColor(board.selected, FieldsColor.NO_PLAYER);
	                board.flushPossible();
	                evt.consume();
	                client.sendMessage("MOVE "+board.selected.getYCord()+" "+board.selected.getXCord()+" "+f.getYCord()+" "+f.getXCord());
	                String ss="MOVE "+board.selected.getYCord()+" "+board.selected.getXCord()+" "+f.getYCord()+" "+f.getXCord();
	                System.out.println(ss);
	            }
	            else if(f.getFieldColor()==FieldsColor.values()[client.getId()+1]){
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
				//wykonaj ruch
			}
			else {
				text.setText("Player number " + queue.getArg(0)+1 + " turn");
				yourTurn=false;
				//czekaj na ruch gracza
				move = client.getMessage();
				
				
				board.changeFieldsColor(board.getNode(move.getArg(2), move.getArg(3)), board.getNode(move.getArg(0), move.getArg(1)).getFieldColor());
				board.changeFieldsColor(board.getNode(move.getArg(0), move.getArg(1)), FieldsColor.NO_PLAYER);
			}
		}
	}
}
