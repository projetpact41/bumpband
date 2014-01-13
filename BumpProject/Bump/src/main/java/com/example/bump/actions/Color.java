package com.example.bump.actions;

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

    @Override
    public void execute() {

    }
}
