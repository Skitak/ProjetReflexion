package services;

import java.net.Socket;

import utilisateurs.Amateur;

public class AccesServices extends Acces{

	public AccesServices(Socket socket, Amateur user) {
		super(socket, user);
	}

	@Override
	protected void clientResponse(int i) {
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

	@Override
	protected void showServices() {
		String reponse = " 1 - Accéder aux services";
		reponse += System.getProperty("line.separator");
		reponse += " 2 - Connexion";
		reponse += System.getProperty("line.separator");
		reponse += " 3 - Création d'un compte utilisateur.";
		reponse += System.getProperty("line.separator");
		reponse += " 4 - Quitter.";
		out.println(reponse);
		
	}

	@Override
	protected void welcomeMessage() {
		out.println("Hi! Here are all the services.");
		
	}

}
