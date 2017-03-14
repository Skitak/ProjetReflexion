package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import serveur.Serveur;

public class App {
	public static void main(String[] args) {
		Socket s = null;
		try {
			s = new Socket(Serveur.IP_ADDR, Serveur.PORT_CONNEXION);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			@SuppressWarnings("resource")
			Scanner clavier = new Scanner(System.in);
			String line;
			new Reader(s, out).start();
			while (!s.isClosed()) {
				line = clavier.nextLine();
				out.println(line);
			}
		} catch (IOException e) {
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
