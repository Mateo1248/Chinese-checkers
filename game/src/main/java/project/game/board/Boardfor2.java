package project.game.board;

public class Boardfor2 extends Board {
		public Boardfor2(){
			
			 final int[][] STAR_FOR2=
				{
	            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
	            {-1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1},
	            {-1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1},
	            {-1, -1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, -1},
	            {-1, -1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, -1},
	            {0, 0, 0, 0,  0,  0,  0,  0,  0, 0, 0, 0, 0},
	            {0, 0, 0,  0,  0,  0,  0,  0,  0, 0, 0, 0, -1},
	            {-1, 0, 0,  0,  0,  0,  0,  0,  0,  0, 0, 0, -1},
	            {-1, 0,  0,  0,  0,  0,  0,  0,  0,  0, 0, -1, -1},
	            {-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1},
	            {-1, 0,  0,  0,  0,  0,  0,  0,  0,  0, 0, -1, -1},
	            {-1, 0, 0,  0,  0,  0,  0,  0,  0,  0, 0, 0, -1},
	            {0, 0, 0,  0,  0,  0,  0,  0,  0, 0, 0, 0, -1},
	            {0, 0, 0, 0,  0,  0,  0,  0,  0, 0, 0, 0, 0},
	            {-1, -1, -1, -1, 2, 2, 2, 2, -1, -1, -1, -1, -1},
	            {-1, -1, -1, -1, -1, 2, 2, 2, -1, -1, -1, -1, -1},
	            {-1, -1, -1, -1, -1, 2, 2, -1, -1, -1, -1, -1, -1},
	            {-1, -1, -1, -1, -1, -1, 2, -1, -1, -1, -1, -1, -1},
	            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
	    };
			super.STAR_REPRESENTATION=STAR_FOR2;
			super.HEIGHT = STAR_REPRESENTATION.length;
			super.WIDTH = STAR_REPRESENTATION[0].length;
			super.draw();

		}

}
