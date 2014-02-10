package com.example.bump.actions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	private int monSC; // Mon code de securite
	private int tonSC; // Le code de securite de la cible
    private DataInputStream dis = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private static final long serialVersionUID = -3487449280575641304L;
	
	public Connexion (int monSC, int tonSC, InetAddress adresse) {
		this.monSC = monSC;
		this.tonSC = tonSC;
        this.adresse = adresse;
	}
	
	public boolean verifieCorrespond (int monSC, int tonSC) { 
		// Verifie, apres la reception, que les codes de s�curite correspondent
		return (monSC == this.tonSC && tonSC == this.monSC);
	}

    @Override
    public Transmissible execute(Context context) {
        try {
            Thread.sleep(5000); //Le temps de se scynchroniser.
            //Verification des security codes
            dis = new DataInputStream(
                                  new BufferedInputStream(
                                    new FileInputStream(
                                      new File(context.getFilesDir(),"monSC.txt"))));
            int sC = dis.readInt();
            dis.close();
            if (sC != monSC) return new Transmission(ErreurTransmission.SCINCORRECT);
            dis = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(context.getFilesDir(),"tonSC.txt"))));
            sC = dis.readInt();
            if (sC != tonSC) return new Transmission(ErreurTransmission.SCINCORRECT);

            //On met ce client dans la liste d'attente

            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(context.getFilesDir(),"enCours.txt")
                            )
                    )
            );

            oos.writeObject(adresse);
            oos.flush();

            //On envoie alors sa fiche bumpfriend perso

            Log.i("Connexion",context.getFilesDir().toString());
            ois = new ObjectInputStream(

                            new FileInputStream(
                                    new File(context.getFilesDir(),"fichePerso.txt")
                            )

            );
            return (BumpFriend) ois.readObject();

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
}
