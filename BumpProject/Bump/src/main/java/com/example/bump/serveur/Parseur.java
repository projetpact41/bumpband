package com.example.bump.serveur;

import android.util.Log;

import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Color;
import com.example.bump.actions.Connexion;
import com.example.bump.actions.ErreurTransmission;
import com.example.bump.actions.Message;
import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Transmission;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by jjuulliieenn on 17/02/14.
 */
public class Parseur {

    private static final String TAG = "PARSEUR";

    public static Transmissible parser(byte[] recu) {
        switch (recu[0]) {
            case 0 : return lireBF(recu);
            case 1 : return lireColor(recu);

            case 2 : return lireConnexion(recu);

            case 3 : return lireMessage(recu);

            case 4 : return lireTransmission(recu);

            default : Log.i(TAG, "Classe recue non reconnue.");
                return null;
        }
    }

    private static Transmissible lireBF(byte[] recu) {
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

    private static Transmissible lireColor (byte[] recu) {
        return (Transmissible) new Color(recu[1],recu[2],recu[3]);
    }

    private static Transmissible lireConnexion(byte[] recu) {
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

    private static Transmissible lireMessage(byte[] recu) {
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

    private static Transmissible lireTransmission(byte[] recu) {
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