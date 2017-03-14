package appli;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Random;
import java.util.Scanner;
import java.sql.*;

import blti.*;

public class BLTiLaunch {

	public static void main(String[] args) throws MalformedURLException {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);

		// URLClassLoader sur ftp
		String fileDirURL = "ftp://localhost:2121/classes/";  // ou file:///z:/myworkspace/etc en adressage absolu
		URLClassLoader urlcl = new URLClassLoader(new URL [] {
				new URL (fileDirURL)
		});

		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activit� BLTi");
		System.out.println("Pour ajouter une activit�, celle-ci doit �tre pr�sente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'int�grer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activit�");
		int i = (int) Math.floor(Math.random() * 7);

		new Thread(new ServeurBLTi(3000)).start();

		while (true){
			try {
				String classeName = clavier.next();
				Class<? extends Service> classeCharg�e = (Class<? extends Service>)urlcl.loadClass(classeName);
				ServiceRegistry.addService(classeCharg�e);
				System.out.println("Service enregistr�");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	
		
	}
}
