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

	public String updateServices(){
		String response = "";
		for (Service service : services){
			Class<? extends Runnable> loadedClass = null;
			try {
				loadedClass = (Class<? extends Runnable>)ftp.loadClass(getUsername());
			} catch (ClassNotFoundException e){
				response += System.getProperty("line.separator");
				response += "La class " + service.getName() +" n'a pas été trouvé.";
				deleteService(service);
			}
			
			try {
				service.update(loadedClass);
				response += System.getProperty("line.separator");
				response += "La service " + service + " a bien été mis à jour.";
			} catch (Exception e){
				response += System.getProperty("line.separator");
				response += "La class " + service.getName() + " a rencontré l'erreur suivante : ";
				response += System.getProperty("line.separator");
				response += e.getMessage();
				deleteService(service);
			}
		}
		response += System.getProperty("line.separator");
		response += "Les services sont mis à jour.";
		return response;
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
		deleteService(service);
	}
	
	public void deleteService(Service service){
		services.remove(service);
	}

}
