package project.game.server;

import java.util.ArrayList;

public class CommandParser {

	private String message;
	private ArrayList<Integer> args;
	
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ArrayList<Integer> getArgs() {
		return args;
	}
	
	public void addArg(int arg) {
		this.args.add(arg);
	}
	
	public CommandParser getCommand(String msg) {
		
		String []x = msg.split(" ");
		CommandParser cp = new CommandParser();
		cp.setMessage(x[0]);
		
		for(int i=1 ; i<=args.size() ; i++) {
			try {
				cp.addArg(Integer.parseInt(x[i]));
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
				System.out.println("CommandParser parse error");
				System.exit(-1);
			}
		}
		return cp;
	}
	
	
	
}
