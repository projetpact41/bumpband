package com.example.bump.serveur;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class Serveur extends IntentService implements Runnable{

    private final int PORT = 4444;
    private ServerSocket serveurSocket = null;
    private Socket clientSocket;
    private static final String TAG = "SERVEUR";

    public Serveur (int port) { // Constructeur inutile pour le moment
        super(TAG); //Au hasard
        //this.PORT = port;
        //Pour l'instant, on ne peut pas definir le port
        Log.i(TAG, "Creation serveur");
    }

    public Serveur () {
        super(TAG);
        Log.i(TAG,"Creation serveur bis");
    }


    public void run () {
        try {
            serveurSocket = new ServerSocket(PORT);
            while (true) {
                try {
                    Log.i(TAG,"Attente de client");
                    clientSocket = serveurSocket.accept();
                    Log.i(TAG,"Client trouve");
                    TraitementClient t = new TraitementClient(clientSocket, this);
                } catch (Exception e) {
                    Log.i(TAG,"Probleme");
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            Log.i(TAG,"Erreur creation serveur");
        } finally {
            try {
                serveurSocket.close();
            } catch (Exception e) {
                Log.i(TAG,"Probleme fermeture serveur");
            }
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"Lancement de l'intention");
        if (serveurSocket == null) {
            Thread t = new Thread(this);
            t.start();
            Log.i(TAG,"Lancement Serveur");
        }
        else {
            Log.i(TAG,"Serveur deja lance");
        }
    }
}
