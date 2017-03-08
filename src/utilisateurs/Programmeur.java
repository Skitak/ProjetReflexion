package utilisateurs;

import java.util.Vector;

public class Programmeur extends Amateur {

	private Vector<Service> services;
	private String ftpName;

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

	public void addService(Service service){
		services.add(service);
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

	public void setFtpName(String nom){
		ftpName = nom;
	}

	public String getFtpName(){
		return ftpName;
	}

	public void deleteService(String name){
		Service service = getService(name);
		service.deleteService();
		services.remove(service);
	}

}
