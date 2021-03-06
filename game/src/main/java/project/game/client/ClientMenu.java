package project.game.client;

import java.net.SocketTimeoutException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author danieldrapala
 *
 */
public class ClientMenu extends Application {

	
	Client client;
	
	
    /**
     * Launching fx application
     * @param args
     * 
     */
    public static void main(String[] args){
    	
        launch(args);
    }

    
    
    public void start(Stage primaryStage) {
    	
    	try {
			client = new Client();
		} catch (SocketTimeoutException e) {
			System.out.println("nie mozna sie polaczyc");
			System.exit(-1);
		}
    	
    	/*
    	 * jesli pierwszy klient to pokaz mu okienko i pobierz informacje
    	 */
    	if(client.isHost()) {
	        primaryStage.setTitle("Chinese-Checkers Client");
	        GridPane root = new GridPane();
	        VBox but=new VBox();
	        
	        
	        Image image = new Image("file:src/assets/menu1.png");
	        ImageView img = new ImageView();
	        img.setImage(image); 
	        
	        TextField txt= new TextField("Welcome in chinese-checkers.");
	        TextField txt2= new TextField("You are host pick players and bots number.");
	        TextField txt3= new TextField("Game will start after.");
	        txt.setEditable(false);
	        txt2.setEditable(false);
	        txt3.setEditable(false);
	        
	        txt.setAlignment(Pos.CENTER);
	        txt2.setAlignment(Pos.CENTER);
	        txt3.setAlignment(Pos.CENTER);
	        
	        txt.selectAll();
	        txt.deselect();
	        
	        txt.setBackground(Background.EMPTY);
	        txt.setBorder(Border.EMPTY);
	        txt.setEditable(false);
	        
	        txt2.setBackground(Background.EMPTY);
	        txt2.setBorder(Border.EMPTY);
	        txt2.setEditable(false);
	        
	        txt3.setBackground(Background.EMPTY);
	        txt3.setBorder(Border.EMPTY);
	        txt3.setEditable(false);
		    
	        Button b = new Button("choose players number");
		    b.setOnAction(event -> {
		        PlayersNumWindow pn = new PlayersNumWindow();
		          
		        // new WaitWindow();
		        if(pn.getnumP()<=6&&pn.getnumP()>0&&pn.getnumB()>=0&&pn.getnumB()<=5) {
		        client.write(Integer.toString(pn.getnumP()));
		        client.write(Integer.toString(pn.getnumB()));
		        GameWind gw;
				try {
					gw = new GameWind(pn.getnumP(), pn.getnumB(), client);
					 gw.start();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
		        primaryStage.close();
		        }

		    });
		    but.getChildren().add(txt);
		    but.getChildren().add(txt2);
		    but.getChildren().add(txt3);
		    but.getChildren().add(b);
	        but.setPrefSize(img.getImage().getWidth(), img.getImage().getHeight());

		    root.getChildren().add(img);
	        root.getChildren().add(but);

	        but.setAlignment(Pos.CENTER);
	        Scene s= new Scene(root,img.getImage().getWidth(),img.getImage().getHeight());
	        
	        primaryStage.setScene(s);
	        primaryStage.show();
    	}
    	else {
    		GameWind gw;
			try {
				gw = new GameWind(Integer.parseInt(client.read()), Integer.parseInt(client.read()), client);    		
				gw.start();

			} catch (NumberFormatException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    
}