package project.game.board;

public class Boardfor6 extends Board{
	public Boardfor6(){
		 final int[][] STAR_FOR6=
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
		super.STAR_REPRESENTATION=STAR_FOR6;
		super.HEIGHT = STAR_REPRESENTATION.length;
		super.WIDTH = STAR_REPRESENTATION[0].length;
		super.draw();

	}

}
