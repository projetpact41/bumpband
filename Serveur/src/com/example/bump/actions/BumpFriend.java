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

package com.example.bump.actions;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.InetAddress;

import com.example.bump.serveur.Main;

import admin.Banque;
import admin.Menu;
import bracelet.Verrous;
import client.Destinataire;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class BumpFriend implements Serializable, Transmissible {

	private InetAddress adresse; //Adresse du BF
    private String name; //Nom du bumpfriend
    private String id;
    private static final long serialVersionUID = -5929515104076961259L;

    public BumpFriend (String name, InetAddress adresse) {
        this.adresse = adresse;
        this.name = name;
    }

    public BumpFriend (String name, String id) {
        this.name = name;
        this.id = id;
    }

    //Quelques getters
    
    public String getName() {
        return name;
    }

    public InetAddress getAdresse() {
        return adresse;
    }

    

    public Transmissible execute (InetAddress address) {
        ObjectInputStream ois = null;
        
            //On verifie que le BF est bien en liste d'attente.
            //Rendez vous
        	//Verrous.sync4.release();
            //Verrous.sync3.acquire();
            //ois = new ObjectInputStream(
             //       new BufferedInputStream(
               //             new FileInputStream(
                 //                   new File("enCours.txt")
                   //         )
                   // )
            //);

            /*InetAddress testAdresse = (InetAddress) ois.readObject();
            if (testAdresse.equals(adresse)) {*/
                
        		System.out.println("BF recu");
        		
                BFList bfList = new BFList("listeBF.txt");
                bfList.ajoutBF(this);

                //int identifiant = HashList.add(this);

                    	Destinataire destinataire = new Destinataire(adresse, 4444);
                        //destinataire.envoieObjet(new Identifiant(identifiant));
                       
                       // destinataire = new Destinataire(this.adresse, 4444);
                        try {
							ois = new ObjectInputStream(

							        new FileInputStream(
							                new File("fichePerso.txt")
							        )

            );
							System.out.println("Envoi fiche perso...");
	                        BumpFriend bfTemp = (BumpFriend) ois.readObject();
	                        destinataire.envoieObjet(bfTemp);
	                        System.out.println("Fiche Perso envoyee");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
				        	//Verrous.enCours.unlock(); //On s'assure qu'on la bien debloque
				            try {
				                ois.close();
				            } catch (IOException e) {
				                e.printStackTrace();
				            } catch (NullPointerException e){
				                e.printStackTrace();
				            }
                        
						}
                        
                    try {
						Thread.sleep(2000);
						System.out.println("Je dors");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //Sinon le menu n'est pas accepte
                
                
                System.out.println("Thread lance");
                //Verrous.enCours.unlock(); // On deblogue la possibilite de faire un bump
                Banque.create(adresse.getHostAddress());
                System.out.println("Ajout banque");
                
                //Ajout a la fenetre d'ami
                
                Main.fenetreTableau.add(this);
                
                return new Menu();
            /*} else return new Transmission(ErreurTransmission.IPNONRECONNUE);*/
        
         }



    public byte[] toBytes() {
        String ad = adresse.getHostAddress();
        int n = 1 + name.length()+1+ad.length();
        byte[] resultat = new byte[n];
        int i = 0;
        resultat[i] = 0;
        i++;
        for (; i < 1 + name.length(); i++) {
            resultat[i] = (byte) name.charAt(i-1);
        }
        resultat[i] = '|';
        i++;
        int t = i;
        for(;i< n;i++){
            resultat[i] = (byte) ad.charAt(i-t);
        }
        return resultat;
    }

    public String toString(){return name+"µ"+adresse.getHostAddress();}//µ = transition
    
}
