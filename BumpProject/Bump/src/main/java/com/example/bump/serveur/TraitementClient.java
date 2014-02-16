package com.example.bump.serveur;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.bump.actions.*;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class TraitementClient extends Thread{

    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private final String TAG = "TRAITEMENT";
    private Context context;

    public TraitementClient (Socket clientSocket, Context context) {
        this.clientSocket = clientSocket;
        this.context = context;
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
            obj = (Transmissible) parser(b);
            Log.i(TAG,"Lecture de l'objet");

            Transmissible t = null;

            while (obj != null && !(obj instanceof Transmission) ) { // On lance la bonne action en fonction de ce que l'on recoit
                try {
                    t = obj.execute(context);
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
                    Log.i(TAG,"Longueur de b " + n);
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

                    obj = (Transmissible) parser(b);
                    Log.i(TAG,"Lecture de l'objet");
                }
            }
            if (obj != null && t != null) t.execute(context); //On fini la conversation avec une transmission
            Log.i(TAG,"FIN TRAITEMENT");
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
        return (Transmissible) new Color (recu[1],recu[2],recu[3]);
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
