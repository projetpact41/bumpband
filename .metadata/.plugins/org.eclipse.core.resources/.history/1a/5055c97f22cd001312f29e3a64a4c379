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
	
	private static LinkedList<String> listeCommande = new LinkedList();
	private static HashMap<String,Boisson> correspondance = new HashMap<String,Boisson>();
	private static HashMap<String,Boolean> etat = new HashMap<String,Boolean>(); //True <=> pret
	
	public static void add (String ip, Boisson boisson) {
		if (listeCommande.contains(ip)) return;
		listeCommande.add(ip);
		correspondance.put(ip,boisson);
		etat.put(ip,false);
	}
	
	public static void remove (String ip) {
		Boisson boisson = correspondance.get(ip);
		listeCommande.remove(ip);
		correspondance.remove(ip);
		etat.remove(ip);
		Destinataire destinataire;
		try {
			destinataire = new Destinataire(InetAddress.getByName(ip),4444);
			destinataire.envoieObjet(new Message("Vous venez d'acheter la boisson " +boisson.getNom()+ " pour "+boisson.getPrix()+" credit(s)","Admin"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Banque.removeMoney(ip, boisson.getPrix());
	}
	
	public static void prepare (String ip) { //Quand la boisson est prete
		etat.put(ip, true);
	}
	
	public static boolean enCommande (String ip) {
		return listeCommande.contains(ip);
	}
}
