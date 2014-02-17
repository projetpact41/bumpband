package com.example.bump.serveur;

public class Main {

	public static void main(String[] args) {
		EcouteConnexion eC = new EcouteConnexion(4444);
		eC.start();
	}

}
