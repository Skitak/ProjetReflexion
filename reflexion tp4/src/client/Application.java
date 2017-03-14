package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Application {
		private final static int PORT_SERVICE = 3000;
		private final static String HOST = "localhost"; 
	
	public static void main(String[] args) {
		Socket s = null;		
		try {
			s = new Socket(HOST, PORT_SERVICE);

			BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
			PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
		
			System.out.println("Connect� au serveur " + s.getInetAddress() + ":"+ s.getPort());
			
			String line;
		// menu et choix du service
			line = sin.readLine();
			System.out.println(line.replaceAll("##", "\n"));
		// saisie/envoie du choix
			sout.println(clavier.readLine());
			
		// r�ception/affichage de la question
			System.out.println(sin.readLine());
		// saisie clavier/envoie au service de la r�ponse
			sout.println(clavier.readLine());
		// r�ception/affichage de la r�ponse
			System.out.println(sin.readLine());
				
			
		}
		catch (IOException e) { System.err.println("Fin de la connexion"); }
		// Refermer dans tous les cas la socket
		try { if (s != null) s.close(); } 
		catch (IOException e2) { ; }		
	}
}
