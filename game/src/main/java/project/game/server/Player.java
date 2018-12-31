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
	private int idtri;
	
	public Player(Socket socket, int id) {
		this.socket = socket;
		this.id = id;
		
		try {
			in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} 
		catch (IOException e) { e.printStackTrace(); }
		write(Integer.toString(id));
		System.out.println("player wyslal id: " + id);
	}
	
	public int getId() {
		return this.id;
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
	
	
	public void setIdTriangle(int numP) {
		switch(numP) {
		case 2:
			if(id==0)
				idtri=1;
			if(id==1)
				idtri= 4;
			break;
		case 3:
			idtri= (id+1)*2;
			break;
		case 4:
			if(id==0)
				idtri= 2;
			if(id==1)
				idtri= 3;
			if(id==2)
				idtri= 5;
			if(id==3)
				idtri= 6;
			break;
		case 6:
			idtri= id+1;
			break;
		}
	}
	
	
	public int getIdTriangle() {
		return idtri;
	}
	
	
	public void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}