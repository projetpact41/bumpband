package com.example.bump.actions;

import android.content.Context;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class Transmission implements Serializable, Transmissible{

    private boolean dialogueReussi; // Le message est-il bien transmis?
    private ErreurTransmission erreur = null;
    private static final long serialVersionUID = -3487449280575641304L;

    public Transmission (boolean verif){
        dialogueReussi = verif;
    }

    public Transmission (ErreurTransmission probleme) {
        //Sert a preciser le probleme
        dialogueReussi = false;
        this.erreur = probleme;
    }

    public boolean statut () {
        //Precise si le dialogue a eu lieu correctement
        return dialogueReussi;
    }

    public ErreurTransmission getErreur (){
        return erreur;
    }

    public Transmissible execute(Context context,InetAddress address) {
        if (dialogueReussi) return null;
        // TODO gerer les differents cas d'erreurs.
        if (erreur == ErreurTransmission.IPNONRECONNUE) {

        } else if (erreur == ErreurTransmission.PROBLEMETRAITEMENT) {

        }
        return null;
    }


    public byte[] toBytes() {
        String err = null;
        if (erreur != null) {
        err = erreur.toString();}
        int n;
        if (err != null) {
            n = 2 + err.length();
        }
        else {
            n = 2;
        }
        byte[] resultat = new byte[n];
        resultat[0] = 4;
        if (dialogueReussi) resultat[1] = 1;
        else resultat[1] = 0;
        for (int i = 0; i < n -2; i++ ) {
            resultat[i+2] = (byte) err.charAt(i);
        }
        return resultat;
    }
}
