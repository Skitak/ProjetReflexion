package utilisateurs;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

public class Programmeur extends Amateur {

	private Vector<Service> services;
	private URLClassLoader ftp;

	public Programmeur(Amateur ama) {
		super(ama.getUsername(), ama.getPassword());
		this.services = new Vector<Service>();
	}

	public Vector<Service> getServices() {
		return services;
	}

	public void addService(String nom) throws Exception{
		if (getService(nom) != null)
			throw new Exception("Vous avez deja un service � ce nom.");
		Class<? extends Runnable> classeCharg�e = null;
		try {
			classeCharg�e = (Class<? extends Runnable>)ftp.loadClass(nom);
		} catch (ClassNotFoundException e){
			throw new ClassNotFoundException("La class n'a pas �t� trouv�.");
		}
		services.add(new Service(getUsername(),classeCharg�e));
		//TODO ajouter un service
	}

	public Service getService(String nom){
		if (services.contains(nom))
			for(int i = 0; i < services.size(); ++i)
				if (nom.equals(services.get(i).getName()))
					return services.get(i);
		return null;
	}

	public Service getService (int service){
		return services.elementAt(service);
	}

	public String updateServices(){
		String response = "";
		for (int i = 0 ; i < services.size() ; ++i){
			try {
				updateService(i);
				response += "Le service " + getService(i).getName() + " a bien �t� mis � jour.";
				response += System.getProperty("line.separator");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response += e.getMessage();
			}
		}
		response += System.getProperty("line.separator");
		response += "Les services sont mis � jour.";
		return response;
	}

	public void demarrerService(String nom){
		getService(nom).setActive(true);
	}

	public void demarrerService(int service) {
		services.elementAt(service).setActive(true);
	}

	public void stopperService(String nom){
		getService(nom).setActive(false);
	}

	public void setFtpName(String nom) throws MalformedURLException{
		ftp = new URLClassLoader(new URL [] {
				new URL (nom)
		});
	}

	public String getFtpName(){
		return ftp.getURLs()[0].getPath();
	}

	public void deleteService(String name){
		Service service = getService(name);
		deleteService(service);
	}

	public void deleteService(Service service){
		services.remove(service);
	}

	public void stopperService(int service) {
		services.elementAt(service).setActive(false);
	}

	@SuppressWarnings("unchecked")
	public void updateService(int service) throws Exception {
		Class<? extends Runnable> loadedClass = null;
		try {
			loadedClass = (Class<? extends Runnable>)ftp.loadClass(getUsername());
		} catch (Exception e){
			throw new Exception("La class " + getService(service).getName() +" n'a pas �t� trouv�.") ;
		}

		try {
			getService(service).update(loadedClass);
		} catch (Exception e){
			throw new Exception ("La class " + getService(service).getName() + " a rencontr� l'erreur suivante : " + System.getProperty("line.separator") + e.getMessage());
		}

	}

}
