package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import utilisateurs.Amateur;

public abstract class Acces implements Runnable{

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
		catch (IOException e) {e.printStackTrace();}

	}
	
	protected void swapAcces (Class<? extends Acces> classe){
		try {
			classe.getDeclaredConstructor(new Class[]{Socket.class, Amateur.class}).newInstance(client, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread.currentThread().interrupt();
	}

	public void start (){
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		welcomeMessage();
		String retour;
		while (true){
			showServices();
			retour = in.nextLine();
			clientResponse(Integer.parseInt(retour));
		}
	}

	protected abstract void clientResponse(int parseInt);

	protected abstract void showServices();

	protected abstract void welcomeMessage();

}
