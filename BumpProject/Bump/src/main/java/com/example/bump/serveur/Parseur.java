package com.example.bump.serveur;

import android.util.Log;

import com.example.bump.actions.Boisson;
import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Color;
import com.example.bump.actions.Connexion;
import com.example.bump.actions.ErreurTransmission;
import com.example.bump.actions.FMConfirmation;
import com.example.bump.actions.Identifiant;
import com.example.bump.actions.IdentifiantAnswer;
import com.example.bump.actions.Menu;
import com.example.bump.actions.Message;
import com.example.bump.actions.Money;
import com.example.bump.actions.RequestMoney;
import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Transmission;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

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

            case 5 : return lireIdentifiant(recu);

            case 6 : return lireIdentifiantAnswer(recu);

            case 7 : return lireMenu(recu);

            case 8 : return lireBoisson(recu);

            case 9 : return lireRequestMoney(recu);

            case 10 : return lireMoney(recu);

            case 11 : return lireFMConfirmation(recu);

            default : Log.i(TAG, "Classe recue non reconnue.");
                return null;
        }
    }

    private static Transmissible lireFMConfirmation(byte[] recu) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < recu.length; i++) {
            stringBuilder.append((char) recu[i]);
        }
        try {
            return new FMConfirmation(InetAddress.getByName(stringBuilder.toString()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return new Transmission(false);
        }
    }

    private static Transmissible lireMoney(byte[] recu) {
        return (Transmissible) (new Money(recu[1]));
    }

    private static Transmissible lireRequestMoney(byte[] recu) {
        return (Transmissible) (new RequestMoney());
    }

    private static Transmissible lireBoisson(byte[] recu) {
        byte prix = recu[1];
        byte rouge = recu[2];
        byte vert = recu[3];
        byte bleu = recu[4];
        byte degre = recu[5];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 6; i < 6+recu.length ; i++) {
            stringBuilder.append(recu[i]);
        }
        return new Boisson(stringBuilder.toString(),prix,new Color(rouge,vert,bleu),degre);
    }

    private static Transmissible lireMenu(byte[] recu) {
        int n = recu.length-1;
        Menu menu = new Menu();
        for (int i=0;i<n;){
            byte prix = recu[i];
            byte rouge = recu[i+1];
            byte vert = recu[i+2];
            byte bleu = recu[i+3];
            byte degre = recu[i+4];
            i += 5;
            StringBuilder stringBuilder = new StringBuilder();
            while (recu[i] != (byte)'|'){
                stringBuilder.append((char) recu[i]);
                i++;
            }
            i++;
            Boisson boisson = new Boisson(stringBuilder.toString(),prix, new Color(rouge,vert,bleu),degre);
            menu.ajouteBoisson(boisson);
        }
        return menu;
    }

    private static Transmissible lireIdentifiantAnswer(byte[] recu) {
        ByteBuffer b = ByteBuffer.wrap(recu);
        if (b.get() != (byte) 6) return null;
        int id = b.getInt();
        StringBuilder ip = new StringBuilder();
        for (int i = 5; i < recu.length; i++) {
            ip.append((char) recu[i]);
        }
        return new IdentifiantAnswer(id, ip.toString());
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

    private static Transmissible lireIdentifiant (byte[] recu) {
        int n = recu.length;
        if (n != 5) {
            Log.i(TAG,"Probleme longueur identifiant");
            return null;
        }
        else {
            Log.i(TAG, "Message Identifiant");
            Identifiant identifiant = new Identifiant(bytesToInt(recu));
            return identifiant;
        }
    }

    protected static int bytesToInt(byte[] bytes)
    {
        int value = 0;

        for(int i=1; i<bytes.length; i++)
        {
            value = value << 8;
            value += bytes[i] & 0xff;
        }

        return value;
    }
}
