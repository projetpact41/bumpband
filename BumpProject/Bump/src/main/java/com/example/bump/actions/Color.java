package com.example.bump.actions;

import android.content.Context;

import java.io.Serializable;
import java.net.InetAddress;

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
        // TODO
        return null;
    }


    public byte[] toBytes() {
        byte[] resultat = new byte[4];
        resultat[0] = 1;
        resultat[1]=rouge;
        resultat[2]=vert;
        resultat[3]=bleu;
        return resultat;
    }
}
