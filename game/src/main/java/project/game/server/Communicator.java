package project.game.server;

import java.util.ArrayList;

/**
 * @author danieldrapala
 * @author mateo1248
 */
public class Communicator {
	String message;
	ArrayList<Integer> args;
	
	/**
	 * Initializing Communicator
	 * @param message
	 */
	
	public Communicator( String message ) {
		this.message = message;
		this.args = new ArrayList<Integer>();
	}
	
	/**
	 * Insert Argument to array args
	 * @param x
	 */
	public void insertArg( int x ) {
		args.add( x );
	}
	
	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Array with Arguments
	 */
	public ArrayList<Integer> getArgs() {
		return args;
	}
	
	/**
	 * @param x
	 * @return argument with index 
	 */
	public int getArg( int x ) {
		return args.get( x );
	}
	
	
	/**
	 * change Communicator to String 
	 */
	public String toString() {
		String ret = message;
		for( Integer i: args ) {
			ret += " ";
			ret += i;
		}
		return ret;
	}
	
	/**
	 * @param src
	 * @return Communicator created from String 
	 */
	public static Communicator fromString( String src ) {
		try {
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
		catch(NullPointerException x)
		{x.printStackTrace();
		}
		return null;
	}
}
