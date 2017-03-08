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
		switch (i) {
		case "1":
			if (this.user == null) {
				swapAcces(Connexion.class);
			} else if (this.user.getClass() == Programmeur.class) {
				swapAcces(AccesProgrammeur.class);
			} else {
				user = user.promote();
			}
			break;
		case "2":
			exit();
			break;
		default:
			String[] split = i.split(".");
			try {
				Service srv = Serveur.getService(split[0], split[1]);
				if (srv == null) {
					out.println("Les données rentrées sont érronées");
				} else {
					if (srv.isActive()) {
						srv.start(); // Socket à passer en parametre
					} else {
						out.println("Le service est inactif");
					}
				}
			} catch (Exception e) {
				out.println("L'utilisateur conserné n'est pas un programmeur");
			}
			out.println("La reponse " + i + " n'est pas une option valide.");
		}
	}

	@Override
	protected void showServices() {
		String add = "";
		String reponse = "";
		if (this.user == null) {
			add = " pour un utilisateur non connecté";
			reponse += "1 - Retour vers le service de connexion";
		} else if (this.user.getClass() == Programmeur.class) {
			add = " pour un utilisateur connecté";
			reponse += "1 - Passer côté programmeur";
		} else {
			add = " pour un utilisateur connecté";
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
					if (srv.isActive()) {
						reponse += key + "." + srv.getName();
						reponse += System.getProperty("line.separator");
					}
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
