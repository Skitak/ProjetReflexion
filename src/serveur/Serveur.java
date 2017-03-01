package serveur;

import java.util.TreeMap;

import utilisateurs.Amateur;

public class Serveur {
	
	public static final int PORT_CONNEXION = 2700;
	public static final String IP_ADDR = "localhost";
	private static TreeMap<String, Amateur> users;
	
	public static void main(String[] args) {
		users = new TreeMap<>();
	}
	
	public static void addUser(Amateur user){
	}

}
