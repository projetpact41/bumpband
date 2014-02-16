package com.example.bump.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Color;
import com.example.bump.actions.Connexion;
import com.example.bump.actions.ErreurTransmission;
import com.example.bump.actions.Message;
import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Transmission;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class Destinataire {

    private InetAddress hostname;
    private int port;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private Socket socket;
    byte[] b;
    private static final String TAG = "CLIENT";

    public Destinataire (InetAddress hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        Log.i(TAG,"Creation du destinataire");
    }

    public void envoieObjet (final Transmissible obj,final Context context) {
        Log.i(TAG,"Envoi Objet");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(hostname,port);
                    Log.i(TAG,"Creation socket");

                    if (out == null) {
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                    }


                    Log.i(TAG,"Creation des flux");

                    b = obj.toBytes();
                    byte n = (byte) b.length;
                    byte[] temp = new byte[n+1];
                    temp[0] = n;
                    for (int i = 1; i < n+1; i++) {
                        temp[i] = b[i-1];
                    }

                    out.write(temp);

                    out.flush();
                    Log.i(TAG,"Envoi des donnees");

                    if (!(obj instanceof Transmission)) {
                        Transmissible t = null;
                        Transmissible t2 = null;

                        try{
                            Log.i(TAG,"Recuperation de l'objet");

                            byte size = in.readByte(); //Recoit la taille envoyee
                            Log.i(TAG,"Taille recue " + size);
                            b = new byte[size];
                            in.read(b);

                            t = (Transmissible) parser(b);
                            t2 = t.execute(context);
                            if (t2 != null) envoieObjet(t2,context);

                       } catch (NullPointerException e) {
                            Log.i(TAG, "Objet transmis invalide");
                        }
                        //throw new TransmissionException (ErreurTransmission.MESSAGENONDELIVRE); @TODO gerer les exceptions
                    }
                    Log.i(TAG,"Reussite");
                    //Intent intent = new Intent(context,com.example.bump.Reussie.class);
                    //context.startActivity(intent);

                    Thread.sleep(5000);

                } catch (IOException e) {
                    Log.i(TAG,"Erreur");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try{
                        if (socket != null)
                        socket.close();
                        if (out != null)
                        out.close();
                        if (in != null)
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    private Transmissible parser(byte[] recu) {
        switch (recu[0]) {
            case 0 : return lireBF(recu);
            case 1 : return lireColor(recu);

            case 2 : return lireConnexion(recu);

            case 3 : return lireMessage(recu);

            case 4 : return lireTransmission(recu);

            default : Log.i(TAG,"Classe recue non reconnue.");
                return null;
        }
    }

    private Transmissible lireBF(byte[] recu) {
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        int n = recu.length;
        Log.i(TAG, String.valueOf(n));
        int i = 1;
        while (recu[i] != '|') {
            str1.append((char)recu[i]);
            i++;
        }
        i++;
        for (;i<n;i++) {
            str2.append((char)recu[i]);
        }
        try {
            return (Transmissible) new BumpFriend(str1.toString(), InetAddress.getByName(str2.toString()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Transmissible lireColor (byte[] recu) {
        return (Transmissible) new Color(recu[1],recu[2],recu[3]);
    }

    private Transmissible lireConnexion(byte[] recu) {
        StringBuilder str1 = new StringBuilder();
        int i = 1;
        while (recu[i] != '|') {
            str1.append((char)recu[i]);
            i++;
        }
        i++;
        try {
            return (Transmissible) new Connexion(recu[i],recu[i+1],InetAddress.getByName(str1.toString()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Transmissible lireMessage(byte[] recu) {
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        int n = recu.length;
        int i = 1;
        while (recu[i] != '|') {
            str1.append((char)recu[i]);
            i++;
        }
        i++;
        for (;i<n;i++) {
            str2.append((char)recu[i]);
        }
        return new Message(str1.toString(),str2.toString());
    }

    private Transmissible lireTransmission(byte[] recu) {
        int n = recu.length;
        boolean dialogueReussi;
        if (recu[1] == 1) dialogueReussi = true;
        else dialogueReussi = false;
        StringBuilder str = new StringBuilder();
        for (int i = 2; i < n ; i++) {
            str.append((char) recu[i]);
        }
        if ( n != 2) {
            return new Transmission(ErreurTransmission.valueOf(str.toString()));
        }
        else return new Transmission(dialogueReussi);
    }

}
