package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.TreeMap;

import services.Connexion;
import utilisateurs.Amateur;

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
		
		while (true){
			try {
				new Connexion(listenSocket.accept()).start();
				System.out.println("Nouvelle connexion!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static boolean userExists (Amateur user){
		return users.containsKey(user.getUsername());
	}
	
	public static void addUser(Amateur user){
		users.put(user.getUsername(), user);
	}
	
	public static Amateur getUser (String username){
		return users.get(username);
	}

}
