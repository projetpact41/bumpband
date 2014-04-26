package admin;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import client.Destinataire;

import com.example.bump.actions.Message;

public class Vestiaire {
	private static HashMap<String,Integer> table;
	private static Integer compt = 1;
	
	public static void TraitementVestiaire (String ip) {
		if (table.containsKey(ip)) {
			int ticket = table.get(ip);
			//Afficher le ticket a l'ecran
			int rang = afficheSortie(ticket);
			if (rang == 0) {
				table.remove(ip);
				try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Vous venez de recuperer vos vetements","Admin"));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (rang == 1) {
				try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Recuperation Refusee","Admin"));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Recuperation annulee","Admin"));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} else {
			//Afficher numero a l'ecran
			int rang = afficheEntree(compt.intValue());
			if (rang == 0) {
				table.put(ip, compt);
				try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Vous venez de deposer vos vetements. Votre numero est le : "+compt,"Admin"));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				compt++;
		    } else if (rang == 1) {
		    	try {
					Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
					destinataire.envoieObjet(new Message("Depot refuse.","Admin"));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
		    }else{
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
	    return rang;
	    
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
