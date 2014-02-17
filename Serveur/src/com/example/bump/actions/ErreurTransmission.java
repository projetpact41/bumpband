package com.example.bump.actions;

public enum ErreurTransmission {
	LONGUEUR, // Mauvaise longueur
	TYPEINCONNU, // Objet transmis de type inconnu
	METHODEINCONNUE, // Methode inexistante dans l'objet
	NOMBREARGUMENT, // Pas assez d'argument dans la methode
	TYPEARGUMENT,	// Argument du mauvais type
	SCINCORRECT, // Le code de securite est incorrect
	MESSAGENONDELIVRE //Quand on ne trouve pas le destinataire
}
