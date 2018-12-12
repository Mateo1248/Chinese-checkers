
public class BoardDrawer {
	/*
	 * wstepnie ogarniete pentelki do rysowania planszy klienta oraz serwera
	 * spacje jako pola nie uzywane * jako pola po ktorych mozemy skakac
	 * 
	 * jak polaczyc te dwie rozne plansze?
	 * pomysl jest taki aby dodac id dla kazdego pola
	 * bedzie ono rownowazne na obu planszach
	 */
	public void drawClientBoard() {
		int spaces=0;
		int pawns=0;
		int i=0;
		
		spaces=12;
		
		for(pawns=1 ; pawns<=4 ; pawns++ ) {
			for(i=0 ; i<spaces ; i++) System.out.print(" ");
			for(i=0 ; i<pawns ; i++) System.out.print("* ");
			System.out.println("");
			spaces--;
		}
		
		spaces=0;
		for(pawns=13 ; pawns>=9 ; pawns-- ) {
			for(i=0 ; i<spaces ; i++) System.out.print(" ");
			for(i=0 ; i<pawns ; i++) System.out.print("* ");
			System.out.println("");
			spaces++;
		}
		
		spaces=3;
		for(pawns=10 ; pawns<=13 ; pawns++ ) {
			for(i=0 ; i<spaces ; i++) System.out.print(" ");
			for(i=0 ; i<pawns ; i++) System.out.print("* ");
			System.out.println("");
			spaces--;
		}
		
		spaces=9;
		for(pawns=4 ; pawns>=1 ; pawns-- ) {
			for(i=0 ; i<spaces ; i++) System.out.print(" ");
			for(i=0 ; i<pawns ; i++) System.out.print("* ");
			System.out.println("");
			spaces++;
		}
	}
	
	public void drawServerBoard() {
		int spaces=0;
		int pawns=0;
		int i=0;
		
		spaces=12;
		
		for(pawns=1 ; pawns<=4 ; pawns++ ) {
			for(i=0 ; i<spaces ; i++) System.out.print(" ");
			for(i=0 ; i<pawns ; i++) System.out.print("* ");
			System.out.println("");
			spaces--;
		}
		
		spaces=4;
		for(pawns=13 ; pawns>=9 ; pawns-- ) {
			for(i=0 ; i<spaces ; i++) System.out.print(" ");
			for(i=0 ; i<pawns ; i++) System.out.print("* ");
			System.out.println("");
		}
		
		spaces=3;
		for(pawns=10 ; pawns<=13 ; pawns++ ) {
			for(i=0 ; i<spaces ; i++) System.out.print(" ");
			for(i=0 ; i<pawns ; i++) System.out.print("* ");
			System.out.println("");
			spaces--;
		}
		
		spaces=4;
		for(pawns=4 ; pawns>=1 ; pawns-- ) {
			for(i=0 ; i<spaces ; i++) System.out.print(" ");
			for(i=0 ; i<pawns ; i++) System.out.print("* ");
			System.out.println("");
			spaces++;
		}
	}
}
