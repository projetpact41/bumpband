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
        try {
            serveurSocket = new ServerSocket(PORT);
            while (true) {
                try {
                   
                    clientSocket = serveurSocket.accept();
                    
                    TraitementClient t = new TraitementClient(clientSocket);
                } catch (Exception e) {
                   
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            
        } finally {
            try {
                serveurSocket.close();
            } catch (Exception e) {
                
            }
        }
    }
	
}
