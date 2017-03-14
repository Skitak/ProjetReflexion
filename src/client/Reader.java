package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Reader implements Runnable {

	private Socket serveur;
	private PrintWriter out;

	public Reader(Socket serv, PrintWriter out) {
		serveur = serv;
		this.out = out;
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
		}catch (Exception e) {
			System.out.println("Fin de la communication avec le serveur.");
			try {
				serveur.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}


}
