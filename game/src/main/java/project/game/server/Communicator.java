package project.game.server;

import java.util.ArrayList;

public class Communicator {
	String message;
	ArrayList<Integer> args;
	
	public Communicator( String message ) {
		this.message = message;
		this.args = new ArrayList<Integer>();
	}
	
	public void insertArg( int x ) {
		args.add( x );
	}
	
	public String getMessage() {
		return message;
	}
	
	public ArrayList<Integer> getArgs() {
		return args;
	}
	
	public int getArg( int x ) {
		return args.get( x );
	}
	
	public String toString() {
		String ret = message;
		for( Integer i: args ) {
			ret += " ";
			ret += i;
		}
		return ret;
	}
	
	public static Communicator fromString( String src ) {
		String[] words = src.split( " " );
		if( words.length < 1 ) return null;
		Communicator ret = new Communicator( words[0] );
		for( int i = 1; i<words.length; i++ ) {
			try {
				ret.insertArg( Integer.parseInt( words[i] ) );
			}
			catch( NumberFormatException e ) {
				return null;
			}
		}
		return ret;
	}
}
