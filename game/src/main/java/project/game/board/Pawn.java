package project.game.board;

import project.game.player.Player;

public class Pawn {

	private int x, y;
	private Player player;
	
	public Pawn(Player player) {
		this.player = player;			
	}
	
	public void setLocation(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPlayer(Player color) {
		this.player = player;
	}
	public Player getPlayer() {
		return player;
	}
}
