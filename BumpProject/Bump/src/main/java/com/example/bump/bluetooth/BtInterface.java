package com.example.bump.bluetooth;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.bump.BluetoothConnexion;
import com.example.bump.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BtInterface {

    private BluetoothDevice appareil = null;
    private BluetoothSocket socket = null;
    private InputStream is = null;
    private OutputStream os = null;
    private final String TAG = "BT_INTERFACE";

    private ThreadReception threadReception;

    private Handler handler;
    private Context contexte;

    public BtInterface(Handler handlerStatus, Handler h, Context context) { //Les handlers permettent de savoir ce qu'il ce passe dans les threads.
        contexte =  context;
        Set<BluetoothDevice> setAppareilsApparies = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        Log.i(TAG,"Recuperation des devices bluetooth");
        BluetoothDevice[] appareilsAppareilles = (BluetoothDevice[]) setAppareilsApparies.toArray(new BluetoothDevice[setAppareilsApparies.size()]);

        for(int i=0;i<appareilsAppareilles.length;i++) {
            if(appareilsAppareilles[i].getName().contains(/*"Bracelet"*/"RNBT-1ED2")) {//1ED2 1D8B
                appareil = appareilsAppareilles[i];
                try {
                    socket = appareil.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    Log.i(TAG,"Creation de la socket");
                    socket.connect();
                    Log.i(TAG, "Connexion a la socket");
                    is = socket.getInputStream();
                    os = socket.getOutputStream();
                    Log.i(TAG,"Recuperation des flux");
                } catch (IOException e) {
                    Intent intent = new Intent(contexte, BluetoothConnexion.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    contexte.startActivity(intent);

                    Log.e(TAG,"Initialisation " + e.getMessage());
                    //e.printStackTrace();
                }
                break;
            }
        }

        handler = handlerStatus;

        threadReception = new ThreadReception(h);
        connect();
    }


    public void sendData(byte[] data) {
        try {
            if (os == null) {
                os = socket.getOutputStream();
            }
            os.write(data);
            os.flush();
            Log.i(TAG,"Ecriture dans le flux de sortie");
        } catch (IOException e) {
            Intent intent = new Intent(contexte, BluetoothConnexion.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexte.startActivity(intent);
            Log.e(TAG,"Send " + e.getMessage());
            //e.printStackTrace();
        }

    }

    public void connect() {
        new Thread() {
            @Override
            public void run() {
                //try {
                    //socket.connect();


                    Message msg = handler.obtainMessage();
                    msg.arg1 = 1; //On s'est bien connecte a la socket
                    handler.sendMessage(msg);

                    Log.i(TAG,"Modification du message dans le handler");

                    threadReception.start(); //Lorsqu'on se connecte, on lance le thread de reception

                //} catch (IOException e) {
                  //  Log.i(TAG,"Echec de la connexion : "+e.getMessage());
                   // e.printStackTrace();
                //}
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
            byte[] k = new byte[1];
            while(true) {
                try {
                    //byte buffer[] = new byte[100];
                    if (is == null) {
                        is = socket.getInputStream();
                        os = socket.getOutputStream();
                        continue;
                    }
                    is.read(k);
                    if(/*is.available() > 0(k = is.read(buffer, 0, 100))>0*/k[0]>0) {
                        Log.i(TAG,"Debut de la lecture du stream");

                        //byte buffer[] = new byte[100];  //Limite de 100 bytes?
                        //k = is.read(buffer, 0, 100);
                        Log.i(TAG,"Reception des donnees et k = " + k[0]);
                        byte[] buffer = new byte[k[0]];
                        is.read(buffer,0,k[0]);

                        /*if(k > 0) { //On verifie que l'on a bien recu les donnees
                            byte temp[] = new byte[k];
                            for(int i=0;i<k;i++) //Recopie du bon nombre de caracteres.
                                temp[i] = buffer[i];*/

                            //String data = new String(temp); //Attention, la chaine de caractere ne veut rien dire en soit, la reconvertir en byte[]
                            //Log.i(TAG,"Conversion des bytes en String");

                            Message msg = handler.obtainMessage();
                            Bundle b = new Bundle();

                            b.putByteArray("receivedData",/*temp*/buffer);
                            msg.setData(b);
                            handler.sendMessage(msg); //handler contiendra un bundle
                            Log.i(TAG,"Confirmation de la reception de donnees");
                    }
                } catch (IOException e) {
                    Log.e(TAG,"Reception : " + appareil.getName() + " " + e.getMessage()  );
                    try {
                        socket = appareil.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        Log.i(TAG,"Creation de la socket");
                        socket.connect();
                        Log.i(TAG, "Connexion a la socket");
                        is = socket.getInputStream();
                        os = socket.getOutputStream();
                        Log.i(TAG,"Recuperation des flux");
                    } catch (IOException e2) {
                        //Intent intent = new Intent(contexte, BluetoothConnexion.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //contexte.startActivity(intent);

                        Log.e(TAG,"Reception2 " + e2.getMessage());
                        //e.printStackTrace();
                    }
                        //e.printStackTrace();
                }
            }
        }
    }
}
