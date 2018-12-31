package project.game.server.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import project.game.server.Player;

public class PlayerTest {
	Player p;
	ServerSocket serverSocket;
	
	//nie wiem jak sockety testować...
	
	
	
	
	@Test
	public void gettingIdTest() {
		try { 
			serverSocket= new ServerSocket(4448);
	Socket s=serverSocket.accept();
	p= new Player(s,0);
	
	} 
	catch (IOException e) { e.printStackTrace(); }
		assertEquals(0,p.getId());
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void settingTriangleIdTest() {
		
		p.setIdTriangle(6);
		assertEquals(1,p.getIdTriangle());
	}
	@Test
	public void readingTest() {
		p.write("XD");
		assertEquals("XD",p.read());
	}

}
