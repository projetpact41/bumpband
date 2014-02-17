package com.example.bump.actions;

public class Connexion { //Sert a etablir la premiere connexion
	private int monSC; // Mon code de securite
	private int tonSC; // Le code de securite de la cible
	
	public Connexion (int monSC, int tonSC) {
		this.monSC = monSC;
		this.tonSC = tonSC;
	}
	
	public boolean verifieCorrespond (int monSC, int tonSC) { 
		// Verifie, apres la reception, que les codes de sécurite correspondent
		return (monSC == this.tonSC && tonSC == this.monSC);
	}
}
