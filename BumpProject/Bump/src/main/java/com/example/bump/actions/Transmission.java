package com.example.bump.actions;

import android.content.Context;

import java.io.Serializable;

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

    public Transmissible execute(Context context) {
        if (dialogueReussi) return null;
        // TODO gerer les differents cas d'erreurs.
        if (erreur == ErreurTransmission.IPNONRECONNUE) {

        } else if (erreur == ErreurTransmission.PROBLEMETRAITEMENT) {

        }
        return null;
    }
}
