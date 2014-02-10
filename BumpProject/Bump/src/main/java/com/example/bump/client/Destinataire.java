package com.example.bump.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.bump.actions.Transmissible;
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

                    if (!(obj instanceof Transmission)) {
                        Transmissible t = null;
                        Transmissible t2 = null;

                        try{
                            Log.i(TAG,"Recuperation de l'objet");
                            //Thread.sleep(1000);
                            t = (Transmissible) in.readObject();
                            t2 = t.execute(context);
                            if (t2 != null) envoieObjet(t2,context);

                       } catch (ClassNotFoundException e) {
                            Log.i(TAG, "Objet transmis invalide");
                        }
                        //throw new TransmissionException (ErreurTransmission.MESSAGENONDELIVRE); @TODO gerer les exceptions
                    }
                    Log.i(TAG,"Reussite");
                    //Intent intent = new Intent(context,com.example.bump.Reussie.class);
                    //context.startActivity(intent);

                    Thread.sleep(1000);

                } catch (IOException e) {
                    Log.i(TAG,"Erreur");
                    e.printStackTrace();
                } catch (InterruptedException e) {
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
