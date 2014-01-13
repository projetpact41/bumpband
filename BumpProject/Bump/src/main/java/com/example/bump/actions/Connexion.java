package com.example.bump.actions;

import java.io.Serializable;

public class Connexion implements Serializable, Transmissible{ //Sert a etablir la premiere connexion
	private int monSC; // Mon code de securite
	private int tonSC; // Le code de securite de la cible
    private static final long serialVersionUID = -3487449280575641304L;
	
	public Connexion (int monSC, int tonSC) {
		this.monSC = monSC;
		this.tonSC = tonSC;
	}
	
	public boolean verifieCorrespond (int monSC, int tonSC) { 
		// Verifie, apres la reception, que les codes de s�curite correspondent
		return (monSC == this.tonSC && tonSC == this.monSC);
	}

    @Override
    public void execute() {

    }
}
