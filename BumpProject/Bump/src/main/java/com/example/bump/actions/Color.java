package com.example.bump.actions;

import android.content.Context;

import java.io.Serializable;

public class Color implements Serializable, Transmissible{
	private int rouge;
	private int vert;
	private int bleu;
    private static final long serialVersionUID = -3487449280575641304L;
	
	public Color (int rouge, int vert, int bleu) {
		this.rouge = rouge;
		this.vert = vert;
		this.bleu = bleu;
	}

    public int getRouge() {
        return rouge;
    }

    public int getVert() {
        return vert;
    }

    public int getBleu() {
        return bleu;
    }

    @Override
    public Transmissible execute(Context context) {
        // TODO
        return null;
    }
}
