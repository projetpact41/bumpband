package com.example.bump.serveur;

import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.example.bump.actions.*;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class TraitementClient extends Thread{

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final String TAG = "TRAITEMENT";

    public TraitementClient (Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.start();
    }

    public void run () {
        try {
            out = new ObjectOutputStream (clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            Object obj = null;
            obj = in.readObject();
            Log.i(TAG,"Lecture de l'objet");

            if (obj != null) { // On lance la bonne action en fonction de ce que l'on recoit
                if (obj.getClass().getName().equals("com.example.bump.actions.Actualite")){
                        out.writeObject(new Transmission(true));
                }
                else if (obj.getClass().getName().equals("com.example.bump.actions.BumpFriend")) {
                        Log.i(TAG,"Bump Friend !");
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
                        out.writeObject(new Transmission (ErreurTransmission.TYPEINCONNU));
                }
                out.flush();
                Log.i(TAG,"FIN TRAITEMENT");
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
