package com.example.bump.serveur;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.example.bump.actions.ErreurTransmission;
import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Transmission;

public class TraitementClient extends Thread {

	private Socket clientSocket;
	private DataOutputStream out;
	private DataInputStream in;
	
	public TraitementClient (Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.start();
	}
	
	public void run () {
        try {
            byte[] b;
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            byte size = in.readByte(); //Recoit la taille envoyee
            b = new byte[size];
            in.read(b);

            Transmissible obj = null;
            obj = (Transmissible) Parseur.parser(b);
            

            Transmissible t = null;

            while (obj != null && !(obj instanceof Transmission) ) { // On lance la bonne action en fonction de ce que l'on recoit
                try {
                    t = obj.execute();
                    if (t != null) {
                    b = t.toBytes().clone();
                    byte n = (byte) b.length;
                    byte[] temp = new byte[n+1];
                    temp[0] = n;
                    for (int i = 1; i < n+1; i++) {
                        temp[i] = b[i-1];
                    }

                    out.write(temp);
                    }

                } catch (Exception e) {
                    if (t != null) {
                    Transmission u = new Transmission (ErreurTransmission.TYPEINCONNU);
                    b = u.toBytes().clone();
                    byte n = (byte) b.length;
                    e.printStackTrace();
                    byte[] temp = new byte[n+1];
                    temp[0] = n;
                    for (int i = 1; i < n+1; i++) {
                        temp[i] = b[i-1];
                    }
                        out.write(b);
                    }
                }
                out.flush();
                if (t instanceof Transmission || t == null) // On suppose que l'on termine la conversation avec Transmission
                    break;
                else {
                    size = in.readByte(); //Recoit la taille envoyee
                    b = new byte[size];
                    in.read(b);

                    obj = (Transmissible) Parseur.parser(b);
                    
                }
            }
            if (obj != null && t != null) t.execute(); //On fini la conversation avec une transmission
            Thread.sleep(1000); // On ne ferme pas la socket trop tot

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