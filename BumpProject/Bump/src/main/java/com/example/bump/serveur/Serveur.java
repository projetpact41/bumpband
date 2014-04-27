//The MIT License (MIT)
//
//Copyright (c) 2014 Julien ROMERO
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

package com.example.bump.serveur;

import android.app.IntentService;
import android.content.Intent;
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
