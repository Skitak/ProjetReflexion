package services;

import java.net.Socket;

import serveur.Serveur;
import services.Acces.Pair;
import utilisateurs.Amateur;
import utilisateurs.Programmeur;

public class Connexion extends Acces{

	public Connexion(Socket socket) {
		super(socket, null);
	}

	@Override
	protected void showServices() {
		String reponse = " 1 - Acc�der aux services";
		reponse += System.getProperty("line.separator");
		reponse += " 2 - Connexion";
		reponse += System.getProperty("line.separator");
		reponse += " 3 - Cr�ation d'un compte utilisateur.";
		reponse += System.getProperty("line.separator");
		reponse += " 4 - Quitter.";
		out.println(reponse);
	}
	
	@Override
	protected void clientResponse (String response) {
		int i = Integer.parseInt(response);
		switch (i){
		case 1 :
			swapAcces(AccesServices.class);
			break;
		case 2 :
			connexion();
			break;
		case 3: 
			createAccount();
			break;
		case 4: 
			exit();
			break;
		default:
			out.println("La reponse " + i + " n'est pas une option valide.");
		}
	}

	private void createAccount() {
		Amateur userConnected = null;
		while (userConnected == null){
			Pair<String, String> user = getUserAndPass();
			try {
				userConnected = Serveur.addUser(user.username, user.pass);
			} catch (Exception e) {
				out.println(e);
				out.println("Quitter la cr�ation de compte?(o)");
				if (in.nextLine().equals("o"))
					return;
			}
		}
		out.println("Vous avez cr�� votre compte.");
		swapAcces(AccesServices.class);
	}

	private void connexion() {
		Amateur userConnected = null;
		while (userConnected == null){
			Pair<String, String> user = getUserAndPass();
			try {
				userConnected = Serveur.getUser(user.username, user.pass);
			} catch (Exception e) {
				out.println();
				out.println("Quit?(y)");
				if (in.nextLine().equals("y"))
					return;
			}
		}
		if (userConnected.getClass() == Programmeur.class)
			swapAcces(AccesProgrammeur.class);
		else swapAcces(AccesServices.class);
	}

	@Override
	protected void welcomeMessage() {
		String reponse = "Hi, welcome to the connection service.";
		out.println(reponse); 
	}

}
