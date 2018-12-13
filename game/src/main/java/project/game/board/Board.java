package project.game.board;

import java.util.ArrayList;

public  class Board {
	public int[][] STAR_REPRESENTATION;
	public int HEIGHT;
	public int WIDTH;
	private Field board[][];
	public Field selected;
	private ArrayList<Field> highlighted;

	    public static Board initialize(int num){
	    	switch(num){
	    	case 2:
	    			return new Boardfor2();
	    	case 3:
	    		return new Boardfor3();
	    			
	    	case 4:
	    		return new Boardfor4();
	    	case 6: 
	    		return new Boardfor6();
	    	}
	    	return null;
	    }
	    public void draw() {
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
