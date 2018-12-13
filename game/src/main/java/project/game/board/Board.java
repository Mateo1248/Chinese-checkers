package project.game.board;

import java.util.ArrayList;

public class Board {
	
	    static private final int[][] STAR_REPRESENTATION = {
	            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},

	            {-1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1},

	            {-1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1},

	            {-1, -1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, -1},

	            {-1, -1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, -1},

	            {6, 6, 6, 6,  0,  0,  0,  0,  0, 2, 2, 2, 2},

	            {6, 6, 6,  0,  0,  0,  0,  0,  0, 2, 2, 2, -1},

	            {-1, 6, 6,  0,  0,  0,  0,  0,  0,  0, 2, 2, -1},

	            {-1, 6,  0,  0,  0,  0,  0,  0,  0,  0, 2, -1, -1},

	            {-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1},

	            {-1, 5,  0,  0,  0,  0,  0,  0,  0,  0, 3, -1, -1},

	            {-1, 5, 5,  0,  0,  0,  0,  0,  0,  0, 3, 3, -1},

	            {5, 5, 5,  0,  0,  0,  0,  0,  0, 3, 3, 3, -1},

	            {5, 5, 5, 5,  0,  0,  0,  0,  0, 3, 3, 3, 3},

	            {-1, -1, -1, -1, 4, 4, 4, 4, -1, -1, -1, -1, -1},

	            {-1, -1, -1, -1, -1, 4, 4, 4, -1, -1, -1, -1, -1},

	            {-1, -1, -1, -1, -1, 4, 4, -1, -1, -1, -1, -1, -1},

	            {-1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, -1},

	            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
	    };

	    public final int HEIGHT = STAR_REPRESENTATION.length, WIDTH = STAR_REPRESENTATION[0].length;
	    private Field board[][];
	    public Field selected;
	    private ArrayList<Field> highlighted;

	    public Board() {
	        board = new Field[HEIGHT][WIDTH];
	        highlighted = new ArrayList<>();
	        for (int y = 0; y < board.length; ++y) {
	            for (int x = 0; x < board[0].length; ++x) {
	                if (STAR_REPRESENTATION[y][x] != -1) {
	                    board[y][x] = new Field(STAR_REPRESENTATION[y][x], y, x);
	                } else {
	                    board[y][x] = Field.getNullField();
	                }
	            }
	        }
	    }

	    public Field getNode(int y, int x) {
	        if (y >= HEIGHT || y < 0 || x >= WIDTH || x < 0)
	            throw new NullPointerException();
	        return board[y][x];
	    }

	    public boolean isLegal(Field field) {
	        return highlighted.contains(field);
	    }


}
