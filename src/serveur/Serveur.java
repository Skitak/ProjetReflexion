package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.TreeMap;

import services.Connexion;
import utilisateurs.Amateur;
import utilisateurs.Programmeur;
import utilisateurs.Service;

public class Serveur {

	public static final int PORT_CONNEXION = 2700;
	public static final String IP_ADDR = "localhost";

	private static ServerSocket listenSocket;
	private static TreeMap<String, Amateur> users;

	public static void main(String[] args) {
		users = new TreeMap<>();
		try {
			listenSocket = new ServerSocket(PORT_CONNEXION);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				new Connexion(listenSocket.accept()).start();
				System.out.println("Nouvelle connexion!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static Service getService(String username, String servicename) throws Exception {
		Programmeur p = (Programmeur) users.get(username);
		if (p != null) {
			return p.getService(servicename);
		} else {
			return null;
		}
	}

	public static void promote(Amateur ama, Programmeur prog) {
		users.replace(ama.getUsername(), prog);
	}

	public static TreeMap<String, Amateur> getUsers() {
		return users;
	}

	public static boolean userExists(String username) {
		return users.containsKey(username);
	}

	public static Amateur getUser(String username, String pass) throws Exception {
		Amateur user = users.get(username);
		if (user == null)
			throw new Exception("L'utilisateur  " + username + " n'existe pas.");
		else if (!user.getPassword().equals(pass))// Sha512 dat?
			throw new Exception("Le mot de pass est incorrect.");
		return user;
	}

	public static Amateur addUser(String username, String pass) throws Exception {
		if (userExists(username))
			throw new Exception(username + " existe déjà.");
		Amateur user = new Amateur(username, pass);
		users.put(username, user);
		return user;
	}

}
