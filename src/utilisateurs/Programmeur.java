package utilisateurs;

import java.util.Vector;

public class Programmeur extends Amateur {

	private Vector<Service> services;

	public Programmeur(Amateur ama) {
		super(ama.getUsername(), ama.getPassword());
		this.services = new Vector<Service>();
	}

	public String getServices() {
		String rep = "";
		for (Service srv : services) {
			rep += srv.getName() + System.getProperty("line.separator");
			;
		}
		return rep;
	}

	public void removeService(String name) {
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getName().equals(name)) {
				services.remove(i);
			}
		}
	}

}
