package project.game.board;

/*
 * tu chyba wszystko git
 */
public class Field {
    private Pawn pawn;
    private int x;
    private int y;

    public Field(int x, int y) {
    	this.x=x;
    	this.y=y;
    	this.pawn = null;
    }

	public Pawn getPawn() {
		return pawn;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
		pawn.setLocation(x, y);
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
	
	public boolean isPawn() {
		if(pawn != null)
			return true;
		else
			return false;
	}
}
