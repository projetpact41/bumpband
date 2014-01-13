package com.example.bump.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.bump.actions.Transmission;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class Destinataire {

    private InetAddress hostname;
    private int port;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private static final String TAG = "CLIENT";

    public Destinataire (InetAddress hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        Log.i(TAG,"Creation du destinataire");
    }

    public void envoieObjet (final Object obj,final Context context) {
        Log.i(TAG,"Envoi Objet");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(hostname,port);
                    Log.i(TAG,"Creation socket");
                    out = new ObjectOutputStream(socket.getOutputStream());
                    in = new ObjectInputStream(socket.getInputStream());
                    Log.i(TAG,"Creation des flux");

                    out.writeObject(obj);//Envoie de l'objet
                    out.flush();
                    Log.i(TAG,"Envoi des donnees");

                    Transmission t = null;

                    /*for (int i = 0; i < 10; i++) { //On essaye d'envoyer le message 10 fois : ne marche pas

                        Log.i(TAG,"Creation du chrono");
                        long end=System.currentTimeMillis()+60*100;

                        Log.i(TAG,"Debut de la boucle");
                        while (System.currentTimeMillis() < end) { // Temps avant de renvoyer l'info
                            if (in.available() > 0) {
                                try{
                                    Log.i(TAG,"Recuperation de l'objet");
                                    t = (Transmission) in.readObject();

                                } catch (ClassNotFoundException e) {
                                    Log.i(TAG, "Objet transmis invalide");
                                }
                            }
                            if (t != null) break;
                        }
                        if (t!=null) break;
                    }*/

                    try{
                        Log.i(TAG,"Recuperation de l'objet");
                        t = (Transmission) in.readObject(); //On recoit forcement un rapport de transmission.

                    } catch (ClassNotFoundException e) {
                        Log.i(TAG, "Objet transmis invalide");
                    }
                    //throw new TransmissionException (ErreurTransmission.MESSAGENONDELIVRE); @TODO gerer les exceptions

                    if (t==null) Log.i(TAG,"t null");
                    //throw new TransmissionException (t.getErreur()); //Renvoie l'erreur éventuelle
                    else if (!t.statut()) Log.i(TAG,"Probleme dans la connexion");
                    else {
                        Log.i(TAG,"Reussite");
                        Intent intent = new Intent(context,com.example.bump.Reussie.class);
                        context.startActivity(intent);
                    }


                } catch (IOException e) {
                    Log.i(TAG,"Erreur");
                    e.printStackTrace();
                } finally {
                    try{
                        socket.close();
                        out.close();
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

}
