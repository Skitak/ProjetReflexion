package services;

import java.net.Socket;
import java.util.Map;
import java.util.Vector;

import serveur.Serveur;
import utilisateurs.Amateur;
import utilisateurs.Programmeur;
import utilisateurs.Service;

public class AccesServices extends Acces {

	public AccesServices(Socket socket, Amateur user) {
		super(socket, user);
	}

	@Override
	protected void clientResponse(int i) {
		switch (i) {
		case 1:
			swapAcces(AccesServices.class);
			break;
		case 2:
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
		String add = "";
		String reponse = "";
		if (this.user == null) {
			add = " pour un utilisateur non connect�";
			reponse += "1 - Retour vers le service de connexion";
		} else if (this.user.getClass() == Programmeur.class) {
			add = " pour un utilisateur connect�";
			reponse += "1 - Passer c�t� programmeur";
		} else {
			add = " pour un utilisateur connect�";
			reponse += "1 - Devenir programmeur";
		}
		reponse += System.getProperty("line.separator");
		reponse += "2 - Quitter.";
		reponse += System.getProperty("line.separator");
		reponse += "Voici la liste des services disponibles" + add;
		reponse += System.getProperty("line.separator");
		reponse += "Ils s'emploient avec nom.service";
		reponse += System.getProperty("line.separator");
		for (Map.Entry<String, Amateur> entry : Serveur.getUsers().entrySet()) {
			if (entry.getValue().getClass() == Programmeur.class) {
				String key = entry.getKey();
				Programmeur value = (Programmeur) entry.getValue();
				Vector<Service> services = value.getServices();
				for (Service srv : services) {
					reponse += key + "." + srv.getName();
					reponse += System.getProperty("line.separator");
				}
			}
		}
		out.println(reponse);

	}

	@Override
	protected void welcomeMessage() {
		out.println("Hi! Here are all the services.");

	}

}
