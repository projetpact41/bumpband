package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.example.bump.actions.Transmission;
import com.example.bump.actions.TransmissionException;



public class Destinataire { // Destinataire de la transmission
	
	private InetAddress hostname;
	private int port;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;
	
	public Destinataire (InetAddress hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	public void envoieObjet (Object obj) throws TransmissionException{
		try {
			socket = new Socket(hostname,port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			out.writeObject(obj);//Envoie de l'objet
			out.writeObject(obj);
			out.flush();
			Transmission t = null;
			
			for (int i = 0; i < 10; i++) { //On essaye d'envoyer le message 10 fois
				long end=System.currentTimeMillis()+60*10;
		    
				while (System.currentTimeMillis() < end) { // Temps avant de renvoyer l'info
					if (in.available() > 0) {
						try{
							t = (Transmission) in.readObject();
						} catch (ClassNotFoundException e) {
							System.out.println("Objet transmis invalide");
						}
					}
					if (t != null) break;
				}
				if (t!=null) break;
			}
			
		if (t==null) throw new TransmissionException (com.example.bump.actions.ErreurTransmission.MESSAGENONDELIVRE);   
		if (!t.statut()) throw new TransmissionException (t.getErreur()); //Renvoie l'erreur Žventuelle
		
		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				socket.close();
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
