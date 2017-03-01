package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

//Le reader ne fait que lire tout le temps en �crivant dans la console.
public class Reader implements Runnable{
	
	private Socket serveur;
	
	public Reader (Socket serv){
		serveur = serv;
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		Scanner in = null;
		try {
			in = new Scanner (serveur.getInputStream());
			
			while(true){
				String reponse = in.nextLine();
				System.out.println(reponse);
			}
			
		} catch (IOException e) {
			System.out.println("Fin de la connexion avec le serveur.");
		}
		
	}

}
