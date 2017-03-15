package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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
		Scanner in = null;
		try {
			in = new Scanner(serveur.getInputStream());
			while (!serveur.isClosed())
				System.out.println(in.nextLine());
		} catch (Exception e) {
			System.out.println("Fin de la communication avec le serveur.");
			try {
				serveur.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
