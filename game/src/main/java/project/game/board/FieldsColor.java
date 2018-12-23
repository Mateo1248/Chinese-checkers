package project.game.board;

public enum FieldsColor {
	
	//in enum it would be like 0, 1 2 3 4 5, 6
	
	    NO_PLAYER("WHITE"),
	    PLAYER1("RED"),
	    PLAYER2("BLUE"),
	    PLAYER3("YELLOW"),
	    PLAYER4("GREEN"),
	    PLAYER5("GRAY"),
	    PLAYER6("PINK"),
	    LEGAL("GRAY"),
	    EH("TRANSPARENT");

	    private final String color;

	    FieldsColor(String color) {
	        this.color = color;
	    }

	    public String getColor() {
	        return color;
	    }
	}

