package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import utilisateurs.Amateur;

public abstract class Acces implements Runnable{

	private boolean inCommunication = true;
	
	protected Socket client;
	protected Amateur user;
	protected PrintWriter out;
	protected Scanner in;

	public Acces(Socket socket, Amateur user) {
		this.client = socket;
		this.user = user;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new Scanner(socket.getInputStream());
		}
		catch (IOException e) {System.err.println("erreur à la création");}
	}
	
	protected void swapAcces (Class<? extends Acces> classe){
		try {
			classe.getDeclaredConstructor(new Class[]{Socket.class, Amateur.class}).newInstance(client, user).start();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("erreur à la création de la class");
		}
		inCommunication = false;
	}

	public void start (){
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		welcomeMessage();
		String retour;
		while (inCommunication){
			showServices();
			retour = read();
			clientResponse(retour);
		}
	}
	
	protected void exit (){
		inCommunication = false;
		try {
			client.close();
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected String read (){
		out.flush();
		return in.nextLine();
	}
	
	protected Pair <String, String> getUserAndPass(){
		Pair<String, String> user = new Pair<String, String>();
		out.println("Username : ");
		user.username = read();
		out.println("Password : ");
		user.pass = read();
		return user;
	}

	protected abstract void clientResponse(String reponse);

	protected abstract void showServices();

	protected abstract void welcomeMessage();
	
	class Pair<F, S> {
	    protected F username;
	    protected S pass;
	}

}
