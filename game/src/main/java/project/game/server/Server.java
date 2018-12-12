package project.game.server;

import java.io.IOException;
import java.net.ServerSocket;

/*
 * klasa w zasadzie skonczona trzeba dodac tylko metode do rozpoczecia gry
 * bedzie ona wywolywana z poziomu hosta po pobraniu liczby graczy oraz botow
 */

public class Server {
	
	private static ServerSocket serversocket;
	/*
	 * Game thread
	 */
    private Game game = null;
    private boolean isRunning;

    
    public static void main (String[] args) {
        Server server = new Server();
        server.runServer();
    }
    
    
    private void runServer() {
    	isRunning=true;
    	try {
    		serversocket = new ServerSocket(9999);
			
			//first player is a host, he has to set number of players and bot then start a game
			Host host = new Host(serversocket.accept(), this);	
			System.out.println("Host has been connected");
			host.run();
		} 
    	catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
    	finally {
    		try {
				serversocket.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
    	}
    	
    	//keep server running by the time isRunning will be set as false
    	while(true) {
    		if(!isRunning) break;
    	}
    }
    
    public void initGame(Host host, int playersNo, int botsNo) {
    	game = new Game(serversocket, host, playersNo, botsNo);
    	game.run();
    }
    
    
    public void turnOff() {
    	isRunning=false;
    }
}
