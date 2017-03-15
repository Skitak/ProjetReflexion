package services;

import java.net.MalformedURLException;
import java.net.Socket;

import utilisateurs.Amateur;
import utilisateurs.Programmeur;

public class AccesProgrammeur extends Acces {

	private Programmeur prog;

	public AccesProgrammeur(Socket socket, Amateur user) {
		super(socket, user);
		prog = (Programmeur) user;
	}

	@Override
	protected void showServices() {
		String reponse = "1 - Acc�der aux services";
		reponse += System.getProperty("line.separator");
		reponse += "2 - Fournir un nouveau service";
		reponse += System.getProperty("line.separator");
		reponse += "3 - Mettre � jour un service";
		reponse += System.getProperty("line.separator");
		reponse += "4 - D�clarer un changement d'adresse de serveur FTP";
		reponse += System.getProperty("line.separator");
		reponse += "5 - D�marrer un service";
		reponse += System.getProperty("line.separator");
		reponse += "6 - Arr�ter un service";
		reponse += System.getProperty("line.separator");
		reponse += "7 - Visioner ses services";
		reponse += System.getProperty("line.separator");
		reponse += "8 - Se d�connecter";
		reponse += System.getProperty("line.separator");
		reponse += "9 - Quitter";
		out.println(reponse);

	}

	@Override
	protected void clientResponse(String s) {
		s.trim();
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			// Reponse non valide
		}
		switch (i) {
		case 1:
			swapAcces(AccesServices.class);
			break;
		case 2:
			fournirService();
			break;
		case 3:
			majService();
			break;
		case 4:
			changementFTP();
			break;
		case 5:
			demarrerService();
			break;
		case 6:
			arretService();
			break;
		case 7:
			visionnerServices();
			break;
		case 8:
			user = null;
			swapAcces(Connexion.class);
			break;
		case 9:
			exit();
			break;
		default:
			out.println("La reponse " + s + " n'est pas une option valide.");
		}
	}

	private void visionnerServices() {
		for (int i = 0; i < prog.getServices().size(); ++i) {
			out.println(i + " - " + prog.getService(i).getName() + " ("
					+ (prog.getService(i).isActive() ? "Actif" : "Eteint") + ")");
		}

	}

	private void arretService() {
		out.println("Quel service voulez vous arr�ter?");
		visionnerServices();
		out.println(prog.getServices().size() + " - Retour");
		String resultat = null;
		int service = -1;
		while (service < 0 || service > prog.getServices().size()) {
			try {
				resultat = read();
				service = Integer.parseInt(resultat);
			} catch (Exception e) {
				out.println(resultat + " n'est pas une valeur valide.");
			}
		}
		if (service == prog.getServices().size()) // Quitter
			return;
		if (!prog.getService(service).isActive())
			out.println("Le service est d�j� arr�t�.");
		else {
			prog.stopperService(service);
			out.println("Le service est maintenant arr�t�.");
		}
	}

	private void demarrerService() {
		out.println("Quel service voulez vous d�marrer?");
		visionnerServices();
		out.println(prog.getServices().size() + " - Retour");
		String resultat = null;
		int service = -1;
		while (service < 0 || service > prog.getServices().size()) {
			try {
				resultat = read();
				service = Integer.parseInt(resultat);
			} catch (Exception e) {
				out.println("Ceci n'est pas une valeur valide.");
			}
		}
		if (service == prog.getServices().size())
			// Quitter
			return;
		if (prog.getService(service).isActive())
			out.println("Le service est d�j� disponible.");
		else {
			prog.demarrerService(service);
			out.println("Le service est maintenant disponible.");
		}
	}

	private void changementFTP() {
		out.println("Quel est le lien de votre nouveau FTP?");
		String nouveauFTP = read();
		try {
			prog.setFtpName(nouveauFTP);
			out.println("Votre nouveau lien ftp (\"" + nouveauFTP + "\") a bien �t� enregistr�.");
		} catch (MalformedURLException e) {
			out.println(e.getMessage());
			out.println("Votre lien ftp n'a pas �t� enregistr�.");
		}
	}

	private void majService() {
		out.println("Quel service voulez vous mettre � jour?");
		visionnerServices();
		out.print(prog.getServices().size() + " - Retour");
		String resultat = read();
		int service = -1;
		while (service < 0 || service > prog.getServices().size()) {
			try {
				service = Integer.parseInt(resultat);
			} catch (Exception e) {
				out.println(resultat + " n'est pas une valeur valide.");
			}
		}
		if (service == prog.getServices().size()) // Quitter
			return;
		try {
			prog.updateService(service);
			out.println("Le service a �t� mis � jour.");
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	private void fournirService() {
		out.println("Quel est le nom du service que vous voulez fournir?");
		String service = read();
		try {
			prog.addService(service);
			out.println("Le service " + service + " a bien �t� ajout�.");
			out.println("Pensez � le d�marrer! (Les services ne sont pas actifs par d�faut).");
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	@Override
	protected void welcomeMessage() {
		out.println("Hi, you prog!");
	}

}
