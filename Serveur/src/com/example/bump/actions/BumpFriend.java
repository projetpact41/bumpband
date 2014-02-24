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

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class BumpFriend implements Serializable, Transmissible {

    private InetAddress adresse; //Adresse du BF
    private String name;
    private String id;
    private static final long serialVersionUID = -5929515104076961259L;

    public BumpFriend (String name, InetAddress adresse) {
        this.adresse = adresse;
        this.name = name;
    }

    public BumpFriend (String name, String id) {
        this.name = name;
        this.id = id;
        //this.address = toAdresse(id);
    }

    public String getName() {
        return name;
    }

    public InetAddress getAdresse() {
        return adresse;
    }

    public Transmissible execute () {
        ObjectInputStream ois = null;
        //ObjectOutputStream oos = null;
        try {
            //On verifie que le BF est bien en liste d'attente.
            //On commence par attendre 2 secondes que le serveur traite le client, au cas ou
            Thread.sleep(2000);
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File("enCours.txt")
                            )
                    )
            );

            InetAddress testAdresse = (InetAddress) ois.readObject();
            if (testAdresse.equals(adresse)) {
                
                BFList bfList = new BFList("listeBF.txt");
                bfList.ajoutBF(this);

                //BumpFriendList.add(this);

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
                //oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return new Transmission(ErreurTransmission.PROBLEMETRAITEMENT);
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

    public String toString(){return name+"�"+adresse.getHostAddress();}//� = transition
    
}