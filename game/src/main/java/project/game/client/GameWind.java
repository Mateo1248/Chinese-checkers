package project.game.client;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import project.game.board.Board;
import project.game.board.Field;
import project.game.board.FieldsColor;


public class GameWind {
	GameWind(){
	final int DISPLAY_WIDTH = 500, DISPLAY_HEIGHT = 500;
    final double RADIUS = 10.0;

    Stage game= new Stage();
    
    Group root = new Group();
    Image image = new Image("file:src/assets/Image004.png");
    ImageView img = new ImageView();
    img.setFitHeight(DISPLAY_HEIGHT);
    img.setFitWidth(DISPLAY_WIDTH);
    img.setImage(image);
    Scene s = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    root.getChildren().add(img);
    Board board = new Board();

    s.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
       /* try {
            System.out.print(((Field) evt.getPickResult().getIntersectedNode()).getYCord() + " ");
            System.out.print(((Field) evt.getPickResult().getIntersectedNode()).getXCord() + " ");
            System.out.println(((Field) evt.getPickResult().getIntersectedNode()).getColor());

            if (board.isLegal((Field) evt.getPickResult().getIntersectedNode())) {
                board.changeFieldColor((Field) evt.getPickResult().getIntersectedNode(), board.selected.getFieldColor());
                board.changeFieldColor(board.selected, FieldColor.NO_PLAYER);
                board.flushHighlighted();
            }
            else {
                board.flushHighlighted();
                board.selected = ((Field) evt.getPickResult().getIntersectedNode());
                board.highlightLegalMoves(board.selected);
            }

        } catch (NullPointerException exc) {
            System.out.println("No Field clicked.");
        }
    */});

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
