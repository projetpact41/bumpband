package com.example.bump.actions;


/**
 * Created by jjuulliieenn on 01/01/14.
 */
public enum ErreurTransmission {

    LONGUEUR, // Mauvaise longueur
    TYPEINCONNU, // Objet transmis de type inconnu
    METHODEINCONNUE, // Methode inexistante dans l'objet
    NOMBREARGUMENT, // Pas assez d'argument dans la methode
    TYPEARGUMENT,	// Argument du mauvais type
    SCINCORRECT, // Le code de securite est incorrect
    MESSAGENONDELIVRE, //Quand on ne trouve pas le destinataire
    PROBLEMETRAITEMENT, //Quand on n'a pas reussi a traiter la connexion
    IPNONRECONNUE

}
