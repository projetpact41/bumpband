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
    
	private  static ArrayList<Color> couleur;
	private  static ArrayList<BumpFriend> groupe; //Les groupes sont les indices pair/impaire cote a cote
	
	public static void initialiser() {
		 BFList bfList = new BFList("listeBF.txt");
		 ArrayList<BumpFriend> liste = bfList.getBFliste();
		 int n = liste.size();
		 couleur = new ArrayList<Color>();
		 Random random = new Random();
		 for (int i = n-1;i > 1; i--) {
			 int alea = random.nextInt();
			 BumpFriend temp = liste.get(alea);
			 liste.set(alea, liste.get(i));
			 liste.set(i, temp);
		 }
		 for (int i = 0; i < n/2; i++) {
			 byte[] color = new byte[3]; //Rouge vert bleu
			 random.nextBytes(color);
			 couleur.add(new Color(color[0],color[1],color[2]));
			 BumpFriend bf1 = liste.get(2*i);
			 BumpFriend bf2 = liste.get(2*i+1);
			 Destinataire destinataire1 = new Destinataire(bf1.getAdresse(),4444);
			 Destinataire destinataire2 = new Destinataire(bf2.getAdresse(),4444);
			 destinataire1.envoieObjet(new Message("FlashMob\nTu dois trouver "+bf2.getName(),"Admin"));
			 destinataire2.envoieObjet(new Message("FlashMob\nTu dois trouver "+bf1.getName(),"Admin"));
			 destinataire1.envoieObjet(new Color(color[0],color[1],color[2]));
			 destinataire2.envoieObjet(new Color(color[0],color[1],color[2]));
		 }
	}
	
	public static boolean verification (InetAddress adr1, InetAddress adr2) {
		int n = groupe.size();
		for (int i = 0; i< n ; i++) {
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
