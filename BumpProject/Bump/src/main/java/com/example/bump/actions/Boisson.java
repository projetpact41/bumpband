package com.example.bump.actions;

import android.content.Context;

import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 15/04/14.
 */
public class Boisson implements Transmissible{
    private String nom;
    private byte prix ;
    private Color code ;
    private byte degre ;

    public Boisson(String nom, byte prix, Color code, byte degre) {
        this.nom = nom;
        this.prix = prix ;
        this.code = code ;
        this.degre = degre ;
    }

    public byte getPrix() {
        return prix;
    }

    public Color getCode() {
        return code;
    }

    public byte getDegre() {
        return degre;
    }

    public String getNom () { return nom;}

    @Override
    public Transmissible execute(Context context, InetAddress address) {

        return null;
    }

    public byte[] toBytes(){
        byte[] b =new byte[6+nom.length()];
        b[0]=8;
        b[1]=prix;
        b[2]=code.getRouge();
        b[3]=code.getVert();
        b[4]=code.getBleu();
        b[5]=degre;
        for (int i = 6; i < 6+nom.length() ; i++) {
            b[i] = (byte) nom.charAt(i-6);
        }
        return b;
    }
}

