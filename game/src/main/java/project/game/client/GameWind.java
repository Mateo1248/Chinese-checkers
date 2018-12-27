package project.game.client;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import project.game.board.Board;
import project.game.board.Field;
import project.game.board.FieldsColor;

//TODO: WINDOW PRZECHOWUJE 'ID klienta' KLIENT MA SWOJ NUMEREK/KOLOREK/RAMIE 
//wiec treba tutaj zmienic do wszystkiego jesli field jest legal && czy jest  danego koloru (metoda get Client Color bedzie przechowywana u klienta



public class GameWind {
	
	private Client client;
	
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
    TextField name = new TextField("Player number "+(client.getId()+1)); 
    TextField text = new TextField(""); 
    VBox texts = new VBox(10);
    name.setBackground(Background.EMPTY);
    name.setBorder(Border.EMPTY);
    text.setBackground(Background.EMPTY);
    text.setBorder(Border.EMPTY);
    texts.getChildren().add(name);
    texts.getChildren().add(text);
    root.getChildren().add(texts);
    
    Board board =  Board.initialize(num);

    //if( Client.getMessege()=='MYTURN')
    text.setText("Your turn");
    
    s.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
        try {
        	   System.out.print(((Field) evt.getPickResult().getIntersectedNode()).getYCord() + " ");
            System.out.print(((Field) evt.getPickResult().getIntersectedNode()).getXCord() + " ");
            System.out.println(((Field) evt.getPickResult().getIntersectedNode()).getColor());

            if (board.isPossible((Field) evt.getPickResult().getIntersectedNode())) {
                board.changeFieldsColor((Field) evt.getPickResult().getIntersectedNode(), board.selected.getFieldColor());
                board.changeFieldsColor(board.selected, FieldsColor.NO_PLAYER);
                board.flushPossible();
                evt.consume();
                text.setText("Turn player number "+(client.getId()+2)%6);
                //
            }
            else {
                board.flushPossible();
                board.selected = ((Field) evt.getPickResult().getIntersectedNode());
                board.showPossbileMoves(board.selected);
            }

        } catch (NullPointerException exc) {
            System.out.println("No Field clicked.");
        }

        catch (Exception exc) {
            System.out.println("No Field clicked.");
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

}
