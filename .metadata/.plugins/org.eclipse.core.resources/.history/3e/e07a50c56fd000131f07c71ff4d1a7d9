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

package admin;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

import client.Destinataire;

import com.example.bump.actions.BFList;
import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Color;
import com.example.bump.actions.Message;

/**
 * Created by Arturo on 07/04/2014.
 */
public class FlashMob {
    
	private  static ArrayList<Color> couleur; //Contient la liste des couleurs dans l'ordre pour chaque couple
	private  static ArrayList<BumpFriend> groupe; //Les groupes sont les indices pair/impair cote a cote
	
	public static void initialiser() { //Cette fonction initialise le jeu
		 BFList bfList = new BFList("listeBF.txt");
		 ArrayList<BumpFriend> liste = bfList.getBFliste(); //On recupere la liste des clients
		 int n = liste.size();
		 couleur = new ArrayList<Color>();
		 Random random = new Random();
		 for (int i = n-1;i > 1; i--) { //Cette boucle va permettre de classer les clients de maniere alatoire
			 int alea = random.nextInt();
			 BumpFriend temp = liste.get(alea);
			 liste.set(alea, liste.get(i));
			 liste.set(i, temp);
		 }
		 for (int i = 0; i < n/2; i++) { //Cette boucle choisi une couleur aleatoire pour chaque couple
			 //Et les informe de leur couplage
			 byte[] color = new byte[3]; //Rouge vert bleu
			 random.nextBytes(color);
			 couleur.add(new Color(color[0],color[1],color[2])); //Ajout de la nouvelle couleur
			 BumpFriend bf1 = liste.get(2*i);
			 BumpFriend bf2 = liste.get(2*i+1);
			 //On informe les clients
			 Destinataire destinataire1 = new Destinataire(bf1.getAdresse(),4444);
			 Destinataire destinataire2 = new Destinataire(bf2.getAdresse(),4444);
			 destinataire1.envoieObjet(new Message("FlashMob\nTu dois trouver "+bf2.getName(),"Admin"));
			 destinataire2.envoieObjet(new Message("FlashMob\nTu dois trouver "+bf1.getName(),"Admin"));
			 destinataire1.envoieObjet(new Color(color[0],color[1],color[2]));
			 destinataire2.envoieObjet(new Color(color[0],color[1],color[2]));
		 }
	}
	
	public static boolean verification (InetAddress adr1, InetAddress adr2) {
		//Confirme si les deux addresses sont bien couplŽes
		int n = groupe.size();
		for (int i = 0; i< n ; i++) { //On verifie s'il sont bien au bon endroid
			BumpFriend bf = groupe.get(i);
			if (bf.getAdresse().getCanonicalHostName().equals(adr1.getCanonicalHostName())
					|| bf.getAdresse().getCanonicalHostName().equals(adr2.getCanonicalHostName())) {
				if (i%2 != 0) {
					return false;
				} else {
					if (i+1 >= n) return false;
					else {
						if (bf.getAdresse().getCanonicalHostName().equals(adr1.getCanonicalHostName())
					|| bf.getAdresse().getCanonicalHostName().equals(adr2.getCanonicalHostName())) {
							return true;
						}
						else return false;
					}
				}
			}
		}
		return false;
	}
	
}
