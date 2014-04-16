package admin;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import client.Destinataire;

import com.example.bump.actions.Message;

public class Vestiaire {
	private static HashMap<String,Integer> table;
	private static Integer compt = 1;
	
	public static void TraitementVestiaire (String ip) {
		if (table.containsKey(ip)) {
			int ticket = table.get(ip);
			//Afficher le ticket a l'ecran
			table.remove(ip);
			try {
				Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
				destinataire.envoieObjet(new Message("Vous venez de recuperer vos vetements","Admin"));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			table.put(ip, compt);
			//Afficher numero a l'ecran
			try {
				Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),4444);
				destinataire.envoieObjet(new Message("Vous venez de deposer vos vetements. Votre numero est le : "+compt,"Admin"));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			compt++;
		}
	}
}
