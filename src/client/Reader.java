package client;

import java.net.Socket;
import java.util.Scanner;

//Le reader ne fait que lire tout le temps en écrivant dans la console.
public class Reader implements Runnable {

	private Socket serveur;

	public Reader(Socket serv) {
		serveur = serv;
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		Scanner in;
		try {
			in = new Scanner(serveur.getInputStream());

			while (true) {
				System.out.println(in.nextLine());
			}

		} catch (Exception e) {
			System.out.println("Fin de la connexion avec le serveur.");
		}

	}

}
