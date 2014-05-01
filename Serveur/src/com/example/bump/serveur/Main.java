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

import graphique.FenetreBar;
import graphique.FenetreMessage;
import graphique.FenetreTableau;
import graphique.FlashMobFenetre;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

import admin.Boisson;
import admin.Menu;

import com.example.bump.actions.BFList;
import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Color;

public class Main {

	public static FenetreTableau fenetreTableau;
	public static FenetreBar fenetreBar;
	
	public static void main(String[] args) {
		EcouteConnexion eC = new EcouteConnexion(4444);
		eC.start();
		
		BFList bfList = new BFList("listeBF.txt"); //On remet a zero la liste des BFs
        bfList.initialiser();
		
		//faire une fiche perso
		
		BumpFriend moi = null;
		try {
			moi = new BumpFriend("Admin",InetAddress.getByName(getIpAddr()));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        //Enregistrement de ma fiche BF

        ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream (
			        new BufferedOutputStream(
			                new FileOutputStream(
			                        new File("fichePerso.txt")
			                )
			        )
			);
			oos.writeObject(moi);
			oos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Initialise tous les fichiers
        
		
		System.out.println(getIpAddr());
        
		//afficher mon ip
		
		Boisson boisson = new Boisson("whisky",(byte) 2,new Color((byte) 0, (byte) 0, (byte) 0),(byte)5);
		
		new Menu();
		Menu.ajouteBoisson(boisson);
		
		//Initialisation des graphiques
		
		fenetreBar = new FenetreBar();
		fenetreBar.setVisible(true);
		new FenetreMessage().setVisible(true);
		fenetreTableau = new FenetreTableau();
		fenetreTableau.setVisible(true);
		SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                FlashMobFenetre fenetre = new FlashMobFenetre();
                fenetre.setVisible(true);
            }
        });
		
	}
	
	private static String getIpAddr() { //Retourne l'ip de l'ordinateur
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

}
