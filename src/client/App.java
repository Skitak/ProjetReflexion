package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import serveur.Serveur;

public class App {
	public static void main(String[] args) {
		Socket s = null;		
		try {
			s = new Socket(Serveur.IP_ADDR, Serveur.PORT_CONNEXION);
			new Reader(s).start();
			PrintWriter out = new PrintWriter (s.getOutputStream ( ), true);
			@SuppressWarnings("resource")
			Scanner clavier = new Scanner(System.in);
			
			String line;
			while(true) {
				line = clavier.nextLine();
				out.println(line);
			}
		}
		catch (IOException e) {
			
		}
		try {s.close(); } 
		catch (IOException e2) { }		
	}
}
