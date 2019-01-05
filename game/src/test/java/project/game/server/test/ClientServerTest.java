package project.game.server.test;
import static org.junit.Assert.*;
import java.net.SocketTimeoutException;
import org.junit.Test;
import project.game.client.Client;
import project.game.server.Game;
import project.game.server.Server;



public class ClientServerTest {
	
	
	@Test
	public void hostConnectionTest() {		
		
		try {
		//create server and send id to player
		ServerTest servertest = new ServerTest();
		Thread serverthread = new Thread(servertest);
		serverthread.start();
		
		//create client and get id
		Client host = new Client();
		
		host.write(Integer.toString(3));
		host.write( "" + 1);
		
		//test sending id
		assertEquals(0,host.getId());
		
		//check if first client is host
		assertEquals(true,host.isHost());
		
		Thread.sleep(1000);
		
		//check number of players
		assertEquals(3,servertest.getServer().getPlayersNo());
		
		//check number of bots
		assertEquals(1,servertest.getServer().getBotsNo());
				
		//check if server start a game
		assertNotNull(servertest.getServer().getGame());
		
		//add the rest of players
		Client client = new Client();
		
		//check if bot was created
		assertEquals(1, servertest.getServer().getGame().getBots().size());
		
		//check player triangle id
		assertNotNull(servertest.getServer().getGame().getPlayers().get(0).getIdTriangle());
		assertNotNull(servertest.getServer().getGame().getPlayers().get(1).getIdTriangle());
		assertNotNull(servertest.getServer().getGame().getPlayers().get(2).getIdTriangle());
		
		} catch (SocketTimeoutException | InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	
	private class ServerTest implements Runnable  {

		private Server server;
		
		ServerTest() {
			server = new Server(); 
		}
		
		public void run() {
			server.start();
		}
		
		public Server getServer() {
			return this.server;
		}
	}
}