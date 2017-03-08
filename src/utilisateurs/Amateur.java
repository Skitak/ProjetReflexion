package utilisateurs;

import serveur.Serveur;

public class Amateur {

	private String username;
	private String password;

	public Amateur(String uname, String pword) {
		this.username = uname;
		this.password = pword;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Programmeur promote() {
		Programmeur p = new Programmeur(this);
		Serveur.promote(this, p);
		return p;
	}

}
