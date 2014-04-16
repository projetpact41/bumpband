package com.example.bump.actions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.bump.BFList;
import com.example.bump.bluetooth.BtParseur;
import com.example.bump.client.Destinataire;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class Color implements Serializable, Transmissible{
	private byte rouge;
	private byte vert;
	private byte bleu;
    private static final long serialVersionUID = -3487449280575641304L;
	
	public Color (byte rouge, byte vert, byte bleu) {
		this.rouge = rouge;
		this.vert = vert;
		this.bleu = bleu;
	}

    public byte getRouge() {
        return rouge;
    }

    public byte getVert() {
        return vert;
    }

    public byte getBleu() {
        return bleu;
    }

    @Override
    public Transmissible execute(Context context, InetAddress address) {
        BFList bfList = new BFList("admin.txt",context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (bfList.isBF(address.getHostAddress())) //On verifie que si l'on parle avec l'admin
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("rouge_jeu",rouge);
            editor.putInt("bleu_jeu",bleu);
            editor.putInt("vert_jeu",vert);
            editor.putBoolean("jeu",true);
            BtParseur.sendColor(new Color(rouge, vert, bleu), context);
            return new Transmission(true);
        }else {
            //BFList bfList2 = new BFList("listeBF.txt", context);
            int rouge = preferences.getInt("rouge_jeu",0);
            int vert = preferences.getInt("vert_jeu",0);
            int bleu = preferences.getInt("bleu_jeu",0);
            if (this.rouge == rouge && this.vert == vert && this.bleu == bleu) {
                ArrayList<BumpFriend> admin = bfList.getBFliste();
                if (admin == null || admin.size() != 1) return new Transmission(false);
                else {
                    Destinataire destinataire = new Destinataire(admin.get(0).getAdresse(),4444);
                    destinataire.envoieObjet(new FMConfirmation(address), context);
                }
            }
            return new Transmission(true);
        }
    }


    public byte[] toBytes() {
        byte[] resultat = new byte[4];
        resultat[0] = 1; //numero fonction
        resultat[1]=rouge;
        resultat[2]=vert;
        resultat[3]=bleu;
        return resultat;
    }
}
