package project.game.board.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import project.game.board.Board;

public class BoardInitTests  {
	Board b;

	@Test
    public void creatingBoardfor2() {
		b=Board.initialize(2);
		int[][] arr= {
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
		for(int i=0;i<arr.length;i++) {
			for(int j=0; j<arr[0].length;j++) {
				assertEquals(arr[i][j],b.STAR_REPRESENTATION[i][j]);

			}
		}
	}
	@Test
		public void creatingBoardfor3() {
			b=Board.initialize(3);
			int[][] arr= {
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
			for(int i=0;i<arr.length;i++) {
				for(int j=0; j<arr[0].length;j++) {
					assertEquals(arr[i][j],b.STAR_REPRESENTATION[i][j]);

				}
			}
		}
	@Test
			public void creatingBoardfor4() {
				b=Board.initialize(4);
				int[][] arr= {
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
				for(int i=0;i<arr.length;i++) {
					for(int j=0; j<arr[0].length;j++) {
						assertEquals(arr[i][j],b.STAR_REPRESENTATION[i][j]);
					}
				}
			}
	@Test
	public void creatingBoardfor6() {
					b=Board.initialize(6);
					int[][] arr= {
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
					for(int i=0;i<arr.length;i++) {
						for(int j=0; j<arr[0].length;j++) {
							assertEquals(arr[i][j],b.STAR_REPRESENTATION[i][j]);

						}
					}
				}
}