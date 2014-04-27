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

import client.Destinataire;

import com.example.bump.actions.BFList;
import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Transmissible;

/**
 * Created by Arturo on 07/04/2014.
 */
public class Menu implements Transmissible{
    private static ArrayList<Boisson> menu ; // Contient la liste des boissons dans le menu

    public static void ajouteBoisson(Boisson b){
        menu.add(b);
        maj();
    }

    private static void retireBoisson(Boisson b){
        menu.remove(b);
        maj();
    }

    @Override
    public byte[] toBytes() {
        int n=menu.size();
        int taille = 0;

        for (int i = 0; i<n; i++) {
            taille += 5+menu.get(i).getNom().length()+1;
        }
        byte[] b = new byte[taille];
        int compteur = 1;
        b[0]=7;
        for (int i=0;i<n;i++){
            b[compteur]=menu.get(i).getPrix();
            b[compteur+1]=menu.get(i).getCode().getRouge();
            b[compteur+2]=menu.get(i).getCode().getVert();
            b[compteur+3]=menu.get(i).getCode().getBleu();
            b[compteur+4]=menu.get(i).getDegre();
            compteur += 5;
            String nom = menu.get(i).getNom();
            for (int j = 0; j< nom.length(); j++) {
                b[compteur] = (byte) nom.charAt(j);
                compteur++;
            }
            b[compteur] = (byte) '|';
        }
        return b;
    }

	@Override
	public Transmissible execute(InetAddress address) {
		//On ne recoit jamais de menu
		return null;
	}
	
	public static void maj() { //Permet de mettre a jour le menu
		BFList bfList = new BFList("listeBF.txt");
		Menu menu = new Menu();
		ArrayList<BumpFriend> liste = bfList.getBFliste();
		for (BumpFriend bf : liste) { //On parcourt l'ensemble des bf pour leur faire parvenir la nouvelle
			Destinataire destinataire = new Destinataire(bf.getAdresse(),4444);
			destinataire.envoieObjet(menu);
		}
	}
	
}
