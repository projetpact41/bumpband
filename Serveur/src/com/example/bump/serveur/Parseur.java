//The MIT License (MIT)
//
//Copyright (c) 2014 Julien ROMERO
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

package com.example.bump.serveur;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;


import admin.Boisson;
import admin.Menu;

import com.example.bump.actions.*;
public class Parseur {
	
	public static Transmissible parser(byte[] recu) {
        switch (recu[0]) {
            case 0 : return lireBF(recu);
            case 1 : return lireColor(recu);

            case 2 : return lireConnexion(recu);

            case 3 : return lireMessage(recu);

            case 4 : return lireTransmission(recu);
            
            case 5 : return lireIdentifiant(recu);
            
            case 6 : return lireIdentifiantAnswer(recu);
            
            case 7 : return lireMenu(recu);
            
            case 8 : return lireBoisson(recu);
            
            case 9 : return lireRequestMoney(recu);
            
            case 10 : return lireMoney(recu);

            case 11 : return lireFMConfirmation(recu);
            
            case 12 : return new Vestiaire_ajout();
            		
            case 13 : return new Vestiaire_retrait();
            
            case 14 : return new RetirerBoisson();
            
            default : System.out.println("Classe recue non reconnue.");
                return null;
        }
    }

	private static Transmissible lireFMConfirmation(byte[] recu) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < recu.length; i++) {
            stringBuilder.append((char) recu[i]);
        }
        try {
            return new FMConfirmation(InetAddress.getByName(stringBuilder.toString()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return new Transmission(false);
        }
    }
	
    private static Transmissible lireMoney(byte[] recu) {
		return (Transmissible) (new Money(recu[1]));
	}

	private static Transmissible lireRequestMoney(byte[] recu) {
		return (Transmissible) (new RequestMoney());
	}

	private static Transmissible lireBoisson(byte[] recu) {
        byte prix = recu[1];
        byte rouge = recu[2];
        byte vert = recu[3];
        byte bleu = recu[4];
        byte degre = recu[5];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 6; i < 6+recu.length ; i++) {
            stringBuilder.append(recu[i]);
        }
        return new Boisson(stringBuilder.toString(),prix,new Color(rouge,vert,bleu),degre);
    }

	private static Transmissible lireMenu(byte[] recu) {
        int n = recu.length-1;
        for (int i=0;i<n;){
            byte prix = recu[i];
            byte rouge = recu[i+1];
            byte vert = recu[i+2];
            byte bleu = recu[i+3];
            byte degre = recu[i+4];
            i += 5;
            StringBuilder stringBuilder = new StringBuilder();
            while (recu[i] != (byte)'|'){
                stringBuilder.append((char) recu[i]);
                i++;
            }
            i++;
            Boisson boisson = new Boisson(stringBuilder.toString(),prix, new Color(rouge,vert,bleu),degre);
            Menu.ajouteBoisson(boisson);
        }
        return new Menu();
    }

    private static Transmissible lireIdentifiantAnswer(byte[] recu) {
        ByteBuffer b = ByteBuffer.wrap(recu);
        if (b.get() != (byte) 6) return null;
        int id = b.getInt();
        StringBuilder ip = new StringBuilder();
        for (int i = 5; i < recu.length; i++) {
            ip.append((char) recu[i]);
        }
        return new IdentifiantAnswer(id, ip.toString());
    }

	private static Transmissible lireIdentifiant(byte[] recu) {
		// Rien pour l'instant
		return null;
	}

	private static Transmissible lireBF(byte[] recu) {
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        int n = recu.length;
        int i = 1;
        while (recu[i] != '|') {
            str1.append((char)recu[i]);
            i++;
        }
        i++;
        for (;i<n;i++) {
            str2.append((char)recu[i]);
        }
        try {
            return (Transmissible) new BumpFriend(str1.toString(), InetAddress.getByName(str2.toString()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Transmissible lireColor (byte[] recu) {
        return (Transmissible) new Color (recu[1],recu[2],recu[3]);
    }

    private static Transmissible lireConnexion(byte[] recu) {
        StringBuilder str1 = new StringBuilder();
        int i = 1;
        while (recu[i] != '|') {
            str1.append((char)recu[i]);
            i++;
        }
        i++;
        try {
            return (Transmissible) new Connexion(recu[i],recu[i+1],InetAddress.getByName(str1.toString()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Transmissible lireMessage(byte[] recu) {
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        int n = recu.length;
        int i = 1;
        while (recu[i] != '|') {
            str1.append((char)recu[i]);
            i++;
        }
        i++;
        for (;i<n;i++) {
            str2.append((char)recu[i]);
        }
        return new Message(str1.toString(),str2.toString());
    }

    private static Transmissible lireTransmission(byte[] recu) {
        int n = recu.length;
        boolean dialogueReussi;
        if (recu[1] == 1) dialogueReussi = true;
        else dialogueReussi = false;
        StringBuilder str = new StringBuilder();
        for (int i = 2; i < n ; i++) {
            str.append((char) recu[i]);
        }
        if ( n != 2) {
            return new Transmission(ErreurTransmission.valueOf(str.toString()));
        }
        else return new Transmission(dialogueReussi);
    }
}
