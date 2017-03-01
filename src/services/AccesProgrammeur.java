package services;

import java.net.Socket;

import utilisateurs.Amateur;

public class AccesProgrammeur extends Acces {

	public AccesProgrammeur(Socket socket, Amateur user) {
		super(socket, user);
	}

	@Override
	protected void clientResponse(int parseInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void showServices() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void welcomeMessage() {
		// TODO Auto-generated method stub
		
	}

}
