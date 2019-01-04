package project.game.board;

import java.util.ArrayList;

public class Boardfor2 extends Board {
		public Boardfor2(){
			draw();

		}
		/**
		 * 	{@link #initialize(int)} 
		 */
		public void draw() {
	        board = new Field[HEIGHT][WIDTH];
	        highlighted = new ArrayList<>();
	        for (int y = 0; y < board.length; ++y) {
	            for (int x = 0; x < board[0].length; ++x) {
	                if (STAR_REPRESENTATION[y][x] != -1) {
	                	if(STAR_REPRESENTATION[y][x]==2||STAR_REPRESENTATION[y][x]==3||STAR_REPRESENTATION[y][x]==5||STAR_REPRESENTATION[y][x]==6)
	                		board[y][x] = new Field(0, y, x);
	                	else 
	                		board[y][x] = new Field(STAR_REPRESENTATION[y][x], y, x);
	                } else {
	                    board[y][x] = Field.getNullField();
	                }
	            }
	        }
	    }


}
