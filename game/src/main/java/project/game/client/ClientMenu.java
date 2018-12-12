package project.game.client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientMenu extends Application {
    private int numb;
    private int num;

    // Player player=new PLayer();
    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage) {
    	
    	/* tu powinno być coś co odróżnia clienta od hosta 
    	 *  wrzucić ifa a wspólne dlaklientaa jest tylko i wyłącznie okienko waitfor...
    	 */
// if client= host myk
        primaryStage.setTitle("Chinese-Checkers Client");
        Group root = new Group();
        //if(player.isAdmin())
        VBox but=new VBox();

        Button b = new Button("choose players number");
        Label b3 = new Label("Waiting for Game");
        TextArea cos= new TextArea("");
        //if (signal from server GAMESTARTED)
        // GameWindow gameWindow=GameWindow();
        but.getChildren().addAll(b,b3,cos);
        root.getChildren().add(but);
        b.setOnAction(event -> {
            PlayersNumWindow pn = new PlayersNumWindow();
            this.num=pn.getnumP();
            this.numb=pn.getnumB();
            cos.setText(num+" "+numb);
            // new WaitWindow();
            primaryStage.close();
        });
        primaryStage.setScene(new Scene(root,200,200));
        primaryStage.show();
        //else {Waitingwindow();
    }
}