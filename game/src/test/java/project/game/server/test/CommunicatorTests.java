package project.game.server.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import project.game.server.Communicator;

public class CommunicatorTests {
	
	//fromString to metoda statyczna uzywajaca get Args insertArgs get Message
	@Test
	public void fromStringTest() {
		Communicator proba=Communicator.fromString("Test 1 2 3");
		ArrayList<Integer> array=new ArrayList<>();
		array.add(1);		
		array.add(2);
		array.add(3);

		for(int i=0; i<=2;i++) {
		assertEquals(array.get(i),proba.getArgs().get(i));
		}
		assertEquals("Test",proba.getMessage());
	}
	@Test
	public void insertGetArgTest() {
		Communicator C=Communicator.fromString("Test 1 2 3");
		assertEquals(1,C.getArg(0));
	}
	
	@Test
	public void toStringTest() {
		Communicator proba2=Communicator.fromString("Test 2 3 4");
		assertEquals("Test 2 3 4",proba2.toString());
	}
	

	

}
