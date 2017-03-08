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

	public void removeService(String name) {
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getName().equals(name)) {
				services.remove(i);
			}
		}
	}

	public void addService(String nom) throws Exception{
		if (getService(nom) != null)
			throw new Exception("Vous avez deja un service à ce nom.");
		Class<? extends Runnable> classeChargée = null;
		try {
			classeChargée = (Class<? extends Runnable>)ftp.loadClass(nom);
		} catch (ClassNotFoundException e){
			throw new ClassNotFoundException("La class n'a pas été trouvé.");
		}
		services.add(new Service(getUsername(),classeChargée));
		//TODO ajouter un service
	}

	public Service getService(String nom){
		if (services.contains(nom))
			for(int i = 0; i < services.size(); ++i)
				if (nom.equals(services.get(i).getName()))
					return services.get(i);
		return null;
	}

	public void updateServices(){
		//todo update services
	}

	public void demarrerService(String nom){
		getService(nom).setActive(true);
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
		service.deleteService();
		services.remove(service);
	}

}
