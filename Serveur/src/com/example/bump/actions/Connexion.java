package com.example.bump.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;

public class Connexion implements Serializable, Transmissible{ //Sert a etablir la premiere connexion
    private InetAddress adresse;
	private byte monSC; // Mon code de securite
	private byte tonSC; // Le code de securite de la cible
    private DataInputStream dis = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private static final long serialVersionUID = -3487449280575641304L;
    private final String TAG = "ConnexionClass";
	
	public Connexion (byte monSC, byte tonSC, InetAddress adresse) {
		this.monSC = monSC;
		this.tonSC = tonSC;
        this.adresse = adresse;
	}
	
	public boolean verifieCorrespond (byte monSC, byte tonSC) {
		// Verifie, apres la reception, que les codes de securite correspondent
		return (monSC == this.tonSC && tonSC == this.monSC);
	}

    @Override
    public Transmissible execute() {
        try {
            Thread.sleep(5000); //Le temps de se scynchroniser.
            //Verification des security codes
            dis = new DataInputStream(
                                  new BufferedInputStream(
                                    new FileInputStream(
                                      new File("monSC.txt"))));
            int sC = dis.readInt();
            dis.close();
            if (sC != monSC) return new Transmission(ErreurTransmission.SCINCORRECT);
            dis = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File("tonSC.txt"))));
            sC = dis.readInt();
            if (sC != tonSC) return new Transmission(ErreurTransmission.SCINCORRECT);

            //On met ce client dans la liste d'attente

            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("enCours.txt")
                            )
                    )
            );

            oos.writeObject(adresse);
            oos.flush();

            //On envoie alors sa fiche bumpfriend perso

            
            ois = new ObjectInputStream(

                            new FileInputStream(
                                    new File("fichePerso.txt")
                            )

            );

            BumpFriend bfTemp = (BumpFriend) ois.readObject();
            return bfTemp;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Transmission(ErreurTransmission.PROBLEMETRAITEMENT);
    }

    public byte[] toBytes() {
        String addressIP = adresse.getHostAddress();
        int n = 1 + addressIP.length()+4;
        byte[] resultat = new byte[n];
        int i = 0;
        resultat[0] = 2;
        i++;
        for (;i<1+addressIP.length(); i++) {
            resultat[i] = (byte) addressIP.charAt(i-1);
        }
        resultat[i] = '|';
        resultat[i+1] = monSC;
        resultat[i+2] = tonSC;
        return resultat;
    }
}
