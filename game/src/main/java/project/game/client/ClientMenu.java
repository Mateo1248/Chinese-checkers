package project.game.client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.game.server.Communicator;

public class ClientMenu extends Application {

	/*
	 * za pomoca obiektu klienta bedziemy komunikowac sie z serverem
	 */
	Client client;
	
	
    public static void main(String[] args){
        launch(args);
    }

    
    public void start(Stage primaryStage) {
    	
    	client = new Client();
    	
    	/*
    	 * jesli pierwszy klient to pokaz mu okienko i pobierz informacje
    	 */
    	if(client.isHost()) {
	        primaryStage.setTitle("Chinese-Checkers Client");
	        Group root = new Group();
	        VBox but=new VBox();	        
		    Button b = new Button("choose players number");
		    b.setOnAction(event -> {
		        PlayersNumWindow pn = new PlayersNumWindow();
		          
		        // new WaitWindow();
		        primaryStage.close();
		        client.write(Integer.toString(pn.getnumP()));
		        client.write(Integer.toString(pn.getnumB()));
		        new GameWind(pn.getnumP(), pn.getnumB(), client);
		    });
		    but.getChildren().add(b);
	        root.getChildren().add(but);
	        primaryStage.setScene(new Scene(root,200,200));
	        primaryStage.show();
    	}
    	/*
    	 * jesli nie pobierz info od servera wlacz okno gry i czekaj na jej rozpoczecie
    	 */
    	else {
    		new GameWind(Integer.parseInt(client.read()), Integer.parseInt(client.read()), client);
    	}
    }
    
    
}