package com.example.bump.bluetooth;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BtInterface {

    private BluetoothDevice appareil = null;
    private BluetoothSocket socket = null;
    private InputStream is = null;
    private OutputStream os = null;
    private final String TAG = "BT_INTERFACE";

    private ThreadReception threadReception;

    private Handler handler;

    public BtInterface(Handler handlerStatus, Handler h) { //Les handlers permettent de savoir ce qu'il ce passe dans les threads.
        Set<BluetoothDevice> setAppareilsApparies = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        Log.i(TAG,"Recuperation des devices bluetooth");
        BluetoothDevice[] appareilsAppareilles = (BluetoothDevice[]) setAppareilsApparies.toArray(new BluetoothDevice[setAppareilsApparies.size()]);

        for(int i=0;i<appareilsAppareilles.length;i++) {
            if(appareilsAppareilles[i].getName().contains("Bracelet")) {
                appareil = appareilsAppareilles[i];
                try {
                    socket = appareil.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    Log.i(TAG,"Creation de la socket");
                    is = socket.getInputStream();
                    os = socket.getOutputStream();
                    Log.i(TAG,"Recuperation des flux");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        handler = handlerStatus;

        threadReception = new ThreadReception(h);
    }


    public void sendData(byte[] data) {
        try {
            os.write(data);
            os.flush();
            Log.i(TAG,"Ecriture dans le flux de sortie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    socket.connect();
                    Log.i(TAG, "Connexion a la socket");

                    Message msg = handler.obtainMessage();
                    msg.arg1 = 1; //On s'est bien connecte a la socket
                    handler.sendMessage(msg);

                    Log.i(TAG,"Modification du message dans le handler");

                    threadReception.start(); //Lorsqu'on se connecte, on lance le thread de reception

                } catch (IOException e) {
                    Log.i(TAG,"Echec de la connexion : "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void close() {
        try {
            socket.close();
            Log.i(TAG,"Fermeture de la socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BluetoothDevice getDevice() {
        return appareil;
    }

    private class ThreadReception extends Thread { //Classe interne
        private Handler handler;

        public ThreadReception(Handler h) {
            handler = h;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    if(is.available() > 0) {
                        Log.i(TAG,"Debut de la lecture du stream");

                        byte buffer[] = new byte[100];  //Limite de 100 bytes?
                        int k = is.read(buffer, 0, 100);
                        Log.i(TAG,"Reception des donnees");

                        if(k > 0) { //On verifie que l'on a bien recu les donnees
                            byte temp[] = new byte[k];
                            for(int i=0;i<k;i++) //Recopie du bon nombre de caracteres.
                                temp[i] = buffer[i];

                            //String data = new String(temp); //Attention, la chaine de caractere ne veut rien dire en soit, la reconvertir en byte[]
                            //Log.i(TAG,"Conversion des bytes en String");

                            Message msg = handler.obtainMessage();
                            Bundle b = new Bundle();

                            b.putByteArray("receivedData",temp);
                            msg.setData(b);
                            handler.sendMessage(msg); //handler contiendra un bundle
                            Log.i(TAG,"Confirmation de la reception de donnees");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
