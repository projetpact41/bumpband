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
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;

import client.Destinataire;

import com.example.bump.actions.Message;

/**
 * Created by Arturo on 07/04/2014.
 */
public class Commande{
	
	private static LinkedList<String> listeCommande = new LinkedList<String>();
	private static HashMap<String,Boisson> correspondance = new HashMap<String,Boisson>();
	private static HashMap<String,Boolean> etat = new HashMap<String,Boolean>(); //True <=> pret
	
	public static int add (String ip, Boisson boisson) { //Ajoute une commande
		if (listeCommande.contains(ip)) return -1; //Il y a deja une commande a ce nom
		listeCommande.add(ip); 
		correspondance.put(ip,boisson);
		etat.put(ip,false); //Pour l'instant la commande n'est pas prete
		return 0;
	}
	
	public static void remove (String ip) { //retire une commande
		//On recupere la boisson avant de la supprimer
		Boisson boisson = correspondance.get(ip);
		if (boisson == null) return;
		listeCommande.remove(ip); //On supprime des deux informations liées à une ip et la commande
		correspondance.remove(ip); 
		etat.remove(ip);
		Destinataire destinataire;
		try {
			destinataire = new Destinataire(InetAddress.getByName(ip),4444);
			//On confirme au client qu'il a bien recupere sa boisson
			destinataire.envoieObjet(new Message("Vous venez d'acheter la boisson " +boisson.getNom()+ " pour "+boisson.getPrix()+" credit(s)","Admin"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Banque.removeMoney(ip, boisson.getPrix()); //On retire l'argent du compte du client
		//A cette etape, on a deja verifie s'il avait assez d'argent
	}
	
	public static void prepare (String ip) { //Quand la boisson est prete
		etat.put(ip, true);
	}
	
	public static boolean enCommande (String ip) { //Indique s'il y a une commande en cours pour ce client
		return listeCommande.contains(ip);
	}
}
