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

import javax.swing.JOptionPane;

import client.Destinataire;

import com.example.bump.actions.Message;

public class Vestiaire {
	private static HashMap<String,Integer> table; //Association ip/numero vestiaire
	private static Integer compt = 1; //Avancement des numeros de vestiaire
	
	public static void TraitementVestiaire (String ip) {
		if (table.containsKey(ip)) { //On regarde s'il est deja dans le vestiaire
			int ticket = table.get(ip);
			//Afficher le ticket a l'ecran
			int rang = afficheSortie(ticket);
			if (rang == 0) { //En fonction du choix de l'admin, on effectue différentes actions
				//Confirmation de la réception
				table.remove(ip);
				try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Vous venez de recuperer vos vetements","Admin"));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			} else if (rang == 1) {
				//Refus de la réception
				try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Recuperation Refusee","Admin"));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			} else {
				//Annulation
				try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Recuperation annulee","Admin"));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
			
		} else {
			//Afficher numero a l'ecran
			int rang = afficheEntree(compt.intValue());
			if (rang == 0) { //Validation
				table.put(ip, compt);
				try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Vous venez de deposer vos vetements. Votre numero est le : "+compt,"Admin"));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				compt++; //On avance d'un numero de vestiaire
		    } else if (rang == 1) { //Refus
		    	try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Depot refuse.","Admin"));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
		    }else{//Annulation
		    	try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Depot annule.","Admin"));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
		    }
			
		}
	}
	
	private static int afficheEntree (int numeroVestiaire) {
		String[] option = {"Valider", "Refuser", "Annuler"};
	    JOptionPane jop = new JOptionPane();
	    int rang = jop.showOptionDialog(null, 
	      "Ticket "+numeroVestiaire+" attribue?",
	      "Confirmation Vestiaire",
	      JOptionPane.YES_NO_CANCEL_OPTION,
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      option,
	      option[2]);
	    return rang; //Transmet le choix de l'admin
	    
	}
	
	private static int afficheSortie (int numeroVestiaire) {
		String[] option = {"Valider", "Refuser", "Annuler"};
	    JOptionPane jop = new JOptionPane();
	    int rang = jop.showOptionDialog(null, 
	      "Ticket "+numeroVestiaire+" souhaite retirer",
	      "Confirmation Vestiaire",
	      JOptionPane.YES_NO_CANCEL_OPTION,
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      option,
	      option[2]);
	    return rang;
	    
	}
}
