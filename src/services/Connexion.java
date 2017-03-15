package services;

import java.net.Socket;

import serveur.Serveur;
import utilisateurs.Amateur;
import utilisateurs.Programmeur;

public class Connexion extends Acces {

	public Connexion(Socket socket) {
		super(socket, null);
	}

	public Connexion(Socket socket, Amateur user) {
		super(socket, null);
	}

	@Override
	protected void showServices() {
		String reponse = "1 - Accéder aux services";
		reponse += System.getProperty("line.separator");
		reponse += "2 - Connexion";
		reponse += System.getProperty("line.separator");
		reponse += "3 - Création d'un compte utilisateur.";
		reponse += System.getProperty("line.separator");
		reponse += "4 - Quitter";
		out.println(reponse);
	}

	@Override
	protected void clientResponse(String response) {
		response.trim();
		int i = Integer.parseInt(response);
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

	private void createAccount() {
		while (user == null) {
			Pair<String, String> connectionUser = getUserAndPass();
			try {
				user = Serveur.addUser(connectionUser.username, connectionUser.pass);
			} catch (Exception e) {
				out.println(e.getMessage());
				out.println("Retour?(o)");
				if (in.nextLine().equals("o"))
					return;
			}
		}
		out.println(
				"Vous avez créé votre compte au nom de " + user.getUsername() + ", ne perdez pas votre mot de passe!");
		swapAcces(AccesServices.class);
	}

	private void connexion() {
		while (user == null) {
			Pair<String, String> newUser = getUserAndPass();
			try {
				user = Serveur.getUser(newUser.username, newUser.pass);
			} catch (Exception e) {
				out.println(e.getMessage());
				out.println("Retour?(o)");
				if (in.nextLine().equals("o"))
					return;
			}
		}
		if (user.getClass() == Programmeur.class)
			swapAcces(AccesProgrammeur.class);
		else
			swapAcces(AccesServices.class);
	}

	@Override
	protected void welcomeMessage() {
		String reponse = "Bonjour et bienvenue aux services de connexion.";
		reponse += System.getProperty("line.separator");
		out.println(reponse);
	}

}
