package services;

import java.net.Socket;

import utilisateurs.Amateur;

public class AccesProgrammeur extends Acces {

	public AccesProgrammeur(Socket socket, Amateur user) {
		super(socket, user);
	}

	@Override
	protected void clientResponse(String s) {
		int i = Integer.parseInt(s);
		switch (i){
		case 1 :
			swapAcces(AccesServices.class);
			break;
		case 2 :
			// Completer
			break;
		case 3: 
			// Completer	
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
		// Completer
		reponse += " 4 - Quitter.";
		out.println(reponse);
		
	}

	@Override
	protected void welcomeMessage() {
		out.println("hi you prog");
		
	}

}
