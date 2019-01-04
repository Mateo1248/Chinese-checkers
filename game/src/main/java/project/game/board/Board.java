package project.game.board;

import java.util.ArrayList;

import javafx.scene.paint.Paint;
import project.game.board.Field;
import project.game.board.FieldsColor;



/**
 * @author danieldrapala
 *
 */
public  class Board {
	public int[][] STAR_REPRESENTATION=
		 {
			{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1},

			{-1,6, 6, 6, 6,  0,  0,  0,  0,  0, 2, 2, 2, 2, -1},

			{-1,6, 6, 6,  0,  0,  0,  0,  0,  0, 2, 2, 2, -1, -1},

			{-1,-1, 6, 6,  0,  0,  0,  0,  0,  0,  0, 2, 2, -1, -1},

			{-1,-1, 6,  0,  0,  0,  0,  0,  0,  0,  0, 2, -1, -1, -1},

			{-1,-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1},

			{-1,-1, 5,  0,  0,  0,  0,  0,  0,  0,  0, 3, -1, -1, -1},

			{-1,-1, 5, 5,  0,  0,  0,  0,  0,  0,  0, 3, 3, -1, -1},

			{-1,5, 5, 5,  0,  0,  0,  0,  0,  0, 3, 3, 3, -1, -1},

			{-1,5, 5, 5, 5,  0,  0,  0,  0,  0, 3, 3, 3, 3, -1},

			{-1,-1, -1, -1, -1, 4, 4, 4, 4, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, 4, 4, 4, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, 4, 4, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, -1, -1},

			{-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
};
	public int HEIGHT=STAR_REPRESENTATION.length;;
	public int WIDTH=STAR_REPRESENTATION[0].length;;
	public Field board[][];
	public Field selected;
	public ArrayList<Field> highlighted;

	    /**
	     * @param num
	     * @return Board initialized depends on number of players
	     */
	    public static  Board initialize(int num){
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
	    

	    /**
	     * @param y
	     * @param x
	     * @return Node in Board with (y,x) position
	     */
	    public Field getNode(int y, int x) {
	        if (y >= HEIGHT || y < 0 || x >= WIDTH || x < 0)
	            throw new NullPointerException();
	        return board[y][x];
	    }

	    /**
	     * @param field
	     * @return Iffieldhiglighted==true
	     */
	    public boolean isPossible(Field field) {
	        return highlighted.contains(field);
	    }

		/**
		 * @param field
		 * @param fieldColor
		 * changing fields color
		 */
		public void changeFieldsColor(Field field, FieldsColor fieldColor) {
			  if (field.getXCord() != -1) {
		            this.getNode(field.getYCord(), field.getXCord()).setColor(fieldColor);
		            this.getNode(field.getYCord(), field.getXCord()).setFill(Paint.valueOf(fieldColor.getColor()));
		        }
		}
		
		/**
		 * 
		 */
		public void flushPossible() {
			for (Field field : highlighted) {
	            field.setStroke(Paint.valueOf("BLACK"));
	            field.setFill(Paint.valueOf(field.getColor()));
	        }
	        highlighted.clear();			
		}
		
		/**
		 * @param selected2
		 * Possible moves
		 */
		public void showPossbileMoves(Field selected2) {
	        if (!selected2.getColor().equals("WHITE")) {
	            int ySelected = selected2.getYCord();
	            int xSelected = selected2.getXCord();

	            if (ySelected % 2 == 1) {
	                highlightField(this.getNode(ySelected - 1, xSelected - 1));
	                highlightField(this.getNode(ySelected + 1, xSelected - 1));
	            } else {
	                highlightField(this.getNode(ySelected - 1, xSelected + 1));
	                highlightField(this.getNode(ySelected + 1, xSelected + 1));
	            }
	            highlightField(this.getNode(ySelected - 1, xSelected));
	            highlightField(this.getNode(ySelected + 1, xSelected));
	            highlightField(this.getNode(ySelected, xSelected - 1));
	            highlightField(this.getNode(ySelected, xSelected + 1));
		        findAHop(selected2);			

	        }

		}
		/**
		 * @param field
		 * 
		 */
		private void findAHop(Field field) {

	        int y = field.getYCord(), x = field.getXCord();
	        int parity;
	        if (y % 2 == 1) {
	            parity = -1;
	        }
	        else {
	            parity = 1;
	        }
	        // DLA PARZYSTYCH I NIE PARZYSTYCH WIERSZY SKOKI DZIAŁAJĄ INACZEJ 
	  /*    nieparzyste o
	   * 	 x x       x x 
	        x o x ==   x o x   
	         x x       x x	
	         parzyste o
	         x x         x x
	        x o x ==   x o x
	         x x         x x
	  */
	        // parzyste o    nieparzyste      x
  	        //           x						o
	        
	        try {
	            if (!this.getNode(y + 1*parity, x + 1*parity).getColor().equals("WHITE")) {
	                if (this.getNode(y + 2*parity, x + 1*parity).getColor().equals("WHITE") && !highlighted.contains(this.getNode(y + 2*parity, x + 1*parity))) {
	                    highlightField(this.getNode(y + 2*parity, x + 1*parity));
	                    findAHop(this.getNode(y + 2*parity, x + 1*parity));
	                }
	            }
	        } catch (NullPointerException exc) {}

	        // nieparzyste   o      parzyste     x
  	        //              x	    	       o
	        try {
	            if (!this.getNode(y + (-1*parity), x + 1*parity).getColor().equals("WHITE")) {
	                if(this.getNode(y + (-2*parity), x + 1*parity).getColor().equals("WHITE") && !highlighted.contains(this.getNode(y + (-2*parity), x + 1*parity))) {
	                    highlightField(this.getNode(y + (-2*parity), x + 1*parity));
	                    findAHop(this.getNode(y + (-2*parity), x + 1*parity));
	                }
	            }
	        } catch (NullPointerException exc) {}
	        
	        // nieparzyste  x      parzyste    x
  	        //              o	    	       o
	        try {
	            if (!this.getNode(y - 1, x).getColor().equals("WHITE")) {
	                if (this.getNode(y - 2, x + (-1*parity)).getColor().equals("WHITE") && !highlighted.contains(this.getNode(y - 2, x + (-1*parity)))) {
	                    highlightField(this.getNode(y - 2, x + (-1*parity)));
	                    findAHop(this.getNode(y - 2, x + (-1*parity)));
	                }
	            }
	        } catch (NullPointerException exc) {}
	        // nieparzyste  o      parzyste    o
  	        //              x	    	       x
	        try {
	            if (!this.getNode(y + 1, x).getColor().equals("WHITE")) {
	                if (this.getNode(y + 2, x + (-1*parity)).getColor().equals("WHITE") && !highlighted.contains(this.getNode(y + 2, x + (-1*parity)))) {
	                    highlightField(this.getNode(y + 2, x + (-1*parity)));
	                    findAHop(this.getNode(y + 2, x + (-1*parity)));
	                }
	            }
	        } catch (NullPointerException exc) {}

	        // nieparzyste i parzyste  x o 
  	        //              
	        try {
	        if (!this.getNode(y, x - 1).getColor().equals("WHITE")) {
	            if(this.getNode(y, x - 2).getColor().equals("WHITE") && !highlighted.contains(this.getNode(y, x - 2))) {
	                highlightField(this.getNode(y, x - 2));
	                findAHop(this.getNode(y, x - 2));
	            }
	        }
	        } catch (NullPointerException exc) {}
	        // nieparzyste i parzyste  o x

	        try {
	        if (!this.getNode(y, x + 1).getColor().equals("WHITE")) {
	            if(this.getNode(y, x + 2).getColor().equals("WHITE") && !highlighted.contains(this.getNode(y, x + 2))) {
	                    highlightField(this.getNode(y, x + 2));
	                    findAHop(this.getNode(y, x + 2));
	                }
	            }
	        }catch (NullPointerException exc) {}
	    }


	 /**
	 * @param node
	 * 
	 */
		private void highlightField(Field node) {
			if (node.getXCord() != -1 && node.getColor().equals("WHITE") && !(highlighted.contains(node))) {
	            highlighted.add(node);
	            this.getNode(node.getYCord(), node.getXCord()).setStroke(Paint.valueOf(FieldsColor.LEGAL.getColor()));
	        }			

		}


}
