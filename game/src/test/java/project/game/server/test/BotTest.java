package project.game.server.test;
import static org.junit.Assert.*;

import org.junit.Test;

import project.game.server.Server;

public class BotTest {

	@Test
	public void botConectionTest() {
		
	}
	
	
	private class GameTest implements Runnable  {

		private Server server;
		
		GameTest() {
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
