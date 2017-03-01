package services;

import java.net.Socket;

import utilisateurs.Amateur;

public class AccesServices extends Acces{

	public AccesServices(Socket socket, Amateur user) {
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
		out.println("Hi! Here are all the services.");
		
	}

}
