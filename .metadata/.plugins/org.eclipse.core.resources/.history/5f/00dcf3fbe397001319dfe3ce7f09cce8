package com.example.bump.serveur;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.example.bump.actions.ErreurTransmission;
import com.example.bump.actions.Transmission;

public class TraitementClient extends Thread {

	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public TraitementClient (Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.start();
	}
	
	public void run () {
		try {
			System.out.println("Salut");
			out = new ObjectOutputStream (clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			
			Object obj = null;
			obj = in.readObject();
			
			if (obj != null) { // On lance la bonne action en fonction de ce que l'on recoit
				 if (obj.getClass().getName().equals("com.example.bump.actions.Actualite")){
                     out.writeObject(new Transmission(true));
             }
             else if (obj.getClass().getName().equals("com.example.bump.actions.BumpFriend")) {
            	 	System.out.println("ICI");
                     out.writeObject(new Transmission(true));
             }
             else if (obj.getClass().getName().equals("com.example.bump.actions.actions.Color")) {
                     out.writeObject(new Transmission(true));
             }
             else if (obj.getClass().getName().equals("com.example.bump.actions.Connexion")) {
                     out.writeObject(new Transmission(true));
             }
             else if (obj.getClass().getName().equals("com.example.bump.actions.Message")) {
                     out.writeObject(new Transmission(true));
             }
             else if (obj.getClass().getName().equals("com.example.bump.actions.Transmission")) {
                     out.writeObject(new Transmission(true));
             }
             else {
            	 	 System.out.println("Coucou");
                     out.writeObject(new Transmission (ErreurTransmission.TYPEINCONNU));
             }
             out.flush();
             System.out.println("Yo");
             Thread.sleep(1000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				clientSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
