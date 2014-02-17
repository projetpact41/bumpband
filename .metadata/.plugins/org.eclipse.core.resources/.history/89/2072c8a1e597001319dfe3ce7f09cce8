package com.example.bump.serveur;

import java.net.ServerSocket;
import java.net.Socket;

public class EcouteConnexion extends Thread{
	
	private final int PORT;
	private ServerSocket serveurSocket;
	private Socket clientSocket; 
	
	public EcouteConnexion (int port) {
		this.PORT = port;
	}
	
	public void run () {
		while (true) {
			try {
				serveurSocket = new ServerSocket(PORT);
				System.out.println("YOpla");
				clientSocket = serveurSocket.accept();
				System.out.println("Connexion");
				TraitementClient t = new TraitementClient(clientSocket);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					serveurSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
