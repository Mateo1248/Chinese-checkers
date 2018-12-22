package project.game.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
	
	private Socket socket;
	private int id;
	private BufferedReader in;
	private PrintWriter out;
	
	Player(Socket socket, int clientno) {
		this.socket = socket;
		this.id = id;
		
		try {
			in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} 
		catch (IOException e) { e.printStackTrace(); }
		
		/*
		 * send id to client
		 */
		write(Integer.toString(id));
		System.out.println("player wyslal id: " + id);
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
}