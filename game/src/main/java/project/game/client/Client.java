package project.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import project.game.server.Communicator;


/**
 * @author danieldrapala
 *
 */
public class Client {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private int id;
	private int idgame;
	
	/**
	 * @throws SocketTimeoutException
	 */
	public Client() throws SocketTimeoutException{
		try {
			SocketAddress socadr = new InetSocketAddress("localhost", 4444);
			socket = new Socket();
			socket.connect(socadr, 1000);
			try {
	        	in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				out = new PrintWriter(this.socket.getOutputStream(), true);
			} 
			catch (IOException e) { e.printStackTrace(); }
			
			//get id
	        this.id = Integer.parseInt( read() );
	        System.out.println("client odebrano id " + id);
		}
		catch (SocketTimeoutException e) {throw new SocketTimeoutException(); }
		catch (UnknownHostException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
	}

	
	
	/**
	 * @return HostValidation
	 */
	public boolean isHost() {
		if(id == 0) return true;
		else return false;
	}
	
	
	/**
	 * @return line from Client's bufferReader
	 */
	public String read() {
		try { return in.readLine(); } 
		catch (IOException e) {	e.printStackTrace(); return null; }
	}
	
	
	/**
	 * @param message
	 */
	public void write(String message) {
		out.println( message );
	}
	
	
	/**
	 * @return Communicator between clients
	 */
	public Communicator getMessage() {
		Communicator x = Communicator.fromString(read());
		return x;		
	}
	
	
	/**
	 * @param x
	 */
	
	public void sendMessage(String x) {
		write(x);
	}
	
	
	/**
	 * @return normal Id given by server
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * @param numP
	 */
	public void setIdGame(int numP) {
		switch(numP) {
		case 2:
			if(id==0)
				idgame=1;
			if(id==1)
				idgame= 4;
			break;
		case 3:
			idgame= (id+1)*2;
			break;
		case 4:
			if(id==0)
				idgame= 2;
			if(id==1)
				idgame= 3;
			if(id==2)
				idgame= 5;
			if(id==3)
				idgame= 6;
			break;
		case 6:
			idgame= id+1;
		}
	}
	
	
	/**
	 * @return Clients id in Game
	 */
	public int getIdGame() {
		return idgame;
	}
	
	
	/**
	 * closing method
	 */
	public void close() {
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}