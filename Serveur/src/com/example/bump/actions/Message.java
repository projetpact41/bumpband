package com.example.bump.actions;

import java.io.Serializable;

import javax.naming.Context;

public class Message implements Serializable, Transmissible{
    private String message;
    private String expediteur;
    private static final long serialVersionUID = -3487449280575641304L;

    public Message (String message,String expediteur) {
        this.message = message;
        this.expediteur = expediteur;
    }

    public String getMessage () {
        return message;
    }

    @Override
    public Transmissible execute() {
        
        return new Transmission(true);
    }

    public byte[]  toBytes() {
        int n = 1+message.length()+1+expediteur.length();
        byte[] resultat = new byte[n];
        int i = 0;
        resultat[i]= 3;
        i++;
        for (;i< 1 + message.length();i++) {
            resultat[i] = (byte) message.charAt(i-1);
        }
        resultat[i] = '|';
        i++;
        int t = i;
        for (;i< n;i++) {
            resultat[i] = (byte) expediteur.charAt(i-t);
        }
        return resultat;
    }
}
