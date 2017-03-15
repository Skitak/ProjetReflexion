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
	protected void clientResponse(String i) {
		i.trim();
		int nbr;
		try {
			nbr = Integer.parseInt(i);
			switch (nbr) {
			case 1:
				if (user == null) {
					swapAcces(Connexion.class);
				} else if (user.getClass() == Programmeur.class) {
					swapAcces(AccesProgrammeur.class);
				} else {
					user = user.promote();
				}
				break;
			case 2:
				exit();
				break;
			default:
				out.println("La reponse " + i + " n'est pas une option valide.");
			}
		} catch (NumberFormatException e) {
			String[] split = i.split("-");
			try {
				Service srv = Serveur.getService(split[0], split[1]);
				if (srv == null) {
					out.println("Les données rentrées sont érronées");
					out.println(split[0] + " " + split[1]);
				} else {
					if (srv.isActive()) {
						clientInCommunication = true;
						// Socket à passer en parametre
						srv.start(client);
						out.println("Vous êtes mis en communication avec le service.");
					} else {
						out.println("Le service est inactif");
					}
				}
			} catch (Exception e1) { // <---- Print l'erreur
				out.println("L'utilisateur concerné n'est pas un programmeur " + e1);
			}
		}
	}

	@Override
	protected void showServices() {
		String reponse = "";
		if (this.user == null) {
			reponse += "1 - Retour vers le service de connexion";
		} else if (this.user.getClass() == Programmeur.class) {
			reponse += "1 - Passer côté programmeur";
		} else {
			reponse += "1 - Devenir programmeur";
		}
		reponse += System.getProperty("line.separator");
		reponse += "2 - Quitter";
		reponse += System.getProperty("line.separator");
		reponse += System.getProperty("line.separator");
		reponse += "Voici la liste des services disponibles";
		reponse += System.getProperty("line.separator");
		reponse += "Ils s'emploient comme ceci :  Pseudo-service";
		reponse += System.getProperty("line.separator");
		out.println(reponse);
		showServicesAvailable();
	}

	@Override
	protected void welcomeMessage() {
		out.println("Hi! Here are all the services.");
	}

	private void showServicesAvailable() {
		String reponse = "";
		for (Map.Entry<String, Amateur> entry : Serveur.getUsers().entrySet()) {
			if (entry.getValue().getClass() == Programmeur.class) {
				Programmeur programmeur = (Programmeur) entry.getValue();
				Vector<Service> services = programmeur.getServices();
				if (services.size() != 0) {
					reponse += System.getProperty("line.separator");
					reponse += "-- " + programmeur.getUsername() + " --";
				}
				for (Service service : services) {
					if (service.isActive()) {
						reponse += System.getProperty("line.separator");
						reponse += service.getName();
					}
				}
			}
		}
		out.println(reponse);
	}

}
