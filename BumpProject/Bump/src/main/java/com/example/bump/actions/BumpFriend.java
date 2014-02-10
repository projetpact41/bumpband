package com.example.bump.actions;


import android.content.Context;
import android.util.Log;

import com.example.bump.BumpFriendList;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class BumpFriend implements Serializable, Transmissible {

    private InetAddress adresse; //Adresse du BF
    private String name;
    private static final long serialVersionUID = -5929515104076961259L;

    public BumpFriend (String name, InetAddress adresse) {
        this.adresse = adresse;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public InetAddress getAdresse() {
        return adresse;
    }

    public Transmissible execute (Context context) {
        Log.i("BF","BF recu");
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            //On verifie que le BF est bien en liste d'attente.
            //On commence par attendre 2 secondes que le serveur traite le client, au cas ou
            Thread.sleep(2000);
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(context.getFilesDir(),"enCours.txt")
                            )
                    )
            );

            InetAddress testAdresse = (InetAddress) ois.readObject();
            Log.e("BF",testAdresse.toString());
            Log.e("BF",testAdresse.toString());
            if (testAdresse.equals(adresse)) {
                Log.e("BF","Ici");

                /*ArrayList<BumpFriend> l = new ArrayList<BumpFriend>();
                FileInputStream fis = new FileInputStream(
                        new File(context.getFilesDir(),"BFList.txt")
                );
                ObjectInputStream ois2 = new ObjectInputStream(
                        new BufferedInputStream(
                                fis
                        )
                );
                ArrayList<BumpFriend> bf=new ArrayList<BumpFriend>();
                Log.i("BF","Debut de la lecture des bf");
                try {
                    bf = (ArrayList<BumpFriend>) ois.readObject();
                } catch (IOException e) {
                    Log.i("BF","FIN des BF");
                }

                Log.i("BF", "Taille " + l.size());

                oos = new ObjectOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(
                                        new File(context.getFilesDir(),"BFList.txt")
                                )
                        )
                );
                l.add(this);
                oos.writeObject(l);
                oos.flush();*/

                BumpFriendList.add(this);

                return new Transmission (true);
            } else return new Transmission(ErreurTransmission.IPNONRECONNUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return new Transmission(ErreurTransmission.PROBLEMETRAITEMENT);
    }

}
