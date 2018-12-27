package project.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import project.game.server.Communicator;


public class Client {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private int id=1;
	
	
	Client() {
		try {
			socket = new Socket("localhost", 4444);
			try {
	        	in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				out = new PrintWriter(this.socket.getOutputStream(), true);
			} 
			catch (IOException e) { e.printStackTrace(); }
			
			//get id
	        this.id = Integer.parseInt( read() );
	        System.out.println("client odebrano id " + id);
		} 
		catch (UnknownHostException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	
	boolean isHost() {
		if(id == 0) return true;
		else return false;
	}
	
	
	public String read() {
		try { return in.readLine(); } 
		catch (IOException e) {	e.printStackTrace(); return null; }
	}
	
	
	public void write(String message) {
		out.println( message );
	}
	
	
	public Communicator getMessage() {
		Communicator x = Communicator.fromString(read());
		return x;		
	}
	
	
	public void sendMessage(String x) {
		write(x);
	}
	public int getId() {
		return id;
	}
}