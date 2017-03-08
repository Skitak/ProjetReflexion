package services;

import java.net.Socket;

import utilisateurs.Amateur;

public class AccesProgrammeur extends Acces {

	public AccesProgrammeur(Socket socket, Amateur user) {
		super(socket, user);
	}

	@Override
	protected void clientResponse(String s) {
		s.trim();
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
			// Completer	
			break;
		case 5: 
			// Completer	
			break;
		case 6: 
			// Completer	
			break;
		case 7: 
			exit();
			break;
		default:
			out.println("La reponse " + i + " n'est pas une option valide.");
		}
	}

	@Override
	protected void showServices() {
		String reponse = "1 - Accéder aux services";
		reponse += System.getProperty("line.separator");
		reponse += "2 - Fournir un nouveau service";
		reponse += System.getProperty("line.separator");
		reponse += "3 - Mettre à jour un service";
		reponse += System.getProperty("line.separator");
		reponse += "4 - Déclarer un changement d'adresse de serveur FTP";
		reponse += System.getProperty("line.separator");
		reponse += "5 - Démarrer un service";
		reponse += System.getProperty("line.separator");
		reponse += "6 - Arrêter un service";
		reponse += System.getProperty("line.separator");
		reponse += "7 - Quitter.";
		out.println(reponse);
		
	}

	@Override
	protected void welcomeMessage() {
		out.println("hi you prog");
		
	}

}
