package services;

import java.net.Socket;

public class Connexion extends Acces{

	public Connexion(Socket socket) {
		super(socket, null);
	}
	
	@Override
	protected void showServices() {
		String reponse = " 1 - Accéder aux services";
		reponse += System.getProperty("line.separator");
		reponse += " 2 - Connexion";
		reponse += System.getProperty("line.separator");
		reponse += " 3 - Quitter";
		out.println(reponse);
		
	}
	@Override
	protected void clientResponse (int i) {
		
	}
	@Override
	protected void welcomeMessage() {
		String reponse = "Hi, welcome to the connection service.";
		out.println(reponse); 
	}

}
