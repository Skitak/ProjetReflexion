package blti;


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;


class ServiceBLTi implements Runnable {

	private Socket client;

	ServiceBLTi(Socket socket) {
		client = socket;
	}

	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
		PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
		out.println(ServiceRegistry.toStringue() + " Tapez le numéro de service désiré :");
		int choix = Integer.parseInt(in.readLine());

		Service service = null;
		
		try {
			 service = ServiceRegistry.getServiceClass(choix).getConstructor(Socket.class).newInstance(client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		service.run();

		}
		catch (IOException e) {
			System.err.println("Fin du service");
		}

		try {client.close();} catch (IOException e2) {}
	}

	protected void finalize() throws Throwable {
		client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
