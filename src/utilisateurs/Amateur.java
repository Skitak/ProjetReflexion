package utilisateurs;

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

}
