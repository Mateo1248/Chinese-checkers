package project.game.board;

public class Boardfor4 extends Board{
	public Boardfor4(){
		 final int[][] STAR_FOR4=
			 {
					{-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		            {-1,-1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1},
		            {-1,-1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1},
		            {-1,-1, -1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, -1, -1},
		            {-1,-1, -1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1},
		            {-1,4, 4, 4, 4,  0,  0,  0,  0,  0, 0, 0, 0, 0, -1},
		            {-1,4, 4, 4,  0,  0,  0,  0,  0,  0, 0, 0, 0, -1, -1},
		            {-1,-1, 4, 4,  0,  0,  0,  0,  0,  0,  0, 0, 0, -1, -1},
		            {-1,-1, 4,  0,  0,  0,  0,  0,  0,  0,  0, 0, -1, -1, -1},
		            {-1,-1, -1,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1},
		            {-1,-1, 0,  0,  0,  0,  0,  0,  0,  0,  0, 2, -1, -1, -1},
		            {-1,-1, 0, 0,  0,  0,  0,  0,  0,  0,  0, 2, 2, -1, -1},
		            {-1,0, 0, 0,  0,  0,  0,  0,  0,  0, 2, 2, 2, -1, -1},
		            {-1,0, 0, 0, 0,  0,  0,  0,  0,  0, 2, 2, 2, 2, -1},
		            {-1,-1, -1, -1, -1, 3, 3, 3, 3, -1, -1, -1, -1, -1, -1},
		            {-1,-1, -1, -1, -1, -1,3, 3, 3, -1, -1, -1, -1, -1, -1},
		            {-1,-1, -1, -1, -1, -1, 3, 3, -1, -1, -1, -1, -1, -1, -1},
		            {-1,-1, -1, -1, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1},
		            {-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
  };
		super.STAR_REPRESENTATION=STAR_FOR4;
		super.HEIGHT = STAR_REPRESENTATION.length;
		super.WIDTH = STAR_REPRESENTATION[0].length;
		super.draw();
	}
}
