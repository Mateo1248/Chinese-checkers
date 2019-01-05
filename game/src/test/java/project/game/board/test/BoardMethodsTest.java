package project.game.board.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import project.game.board.Board;
import project.game.board.Field;
import project.game.board.FieldsColor;

public class BoardMethodsTest {
	//testy działają dla planszy dla nullów nie działają :c nie wiem
	Board b= Board.initialize(6);
	Field f= new Field(1,2,2);
	Field f2= new Field(3,1,1);
	@Test
    //public void drawTest() nie poniewaz board jest private; 
	public void getNodeTest() {
       assertEquals(b.board[1][1],b.getNode(1, 1));
    }
	
	@Test 
	public void xyGettersTest() {
		assertEquals(4,f.getXCord()+f.getYCord());
	}
	@Test 
	public void changingColorTest() {
		b.changeFieldsColor(b.getNode(6, 6),FieldsColor.PLAYER3);
		assertEquals(f2.getColor(),b.getNode(6,6).getColor());
		
	}
	ArrayList<Field> arr=new ArrayList<Field>();

	@Test
	public void flushingTest() {
		b.flushPossible();
		assertEquals(arr,b.highlighted);
	}
	@Test
	public void possibilityTest() {
		assertEquals(false,b.isPossible(f));
	}
		
	//metody zmieniajace gui nie zrobie testu findahop i show possible moves

}
