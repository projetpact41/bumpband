package com.example.bump.actions;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.bump.BFList;
import com.example.bump.MenuPrincipal2;
import com.example.bump.Verrous;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.InetAddress;

public class BumpFriend implements Serializable, Transmissible {

    private InetAddress adresse; //Adresse du BF
    private String name; // Nom du BF
    private int id; //Identifiant du BF
    private static final long serialVersionUID = -5929515104076961259L;

    public BumpFriend (String name, InetAddress adresse) {
        this.adresse = adresse;
        this.name = name;
    }

    public BumpFriend (String name, int id) {
        this.name = name;
        this.id = id;
        //this.address = toAdresse(id);
    }

    public String getName() {
        return name;
    } //Retourne le nom du bf

    public InetAddress getAdresse() {
        return adresse;
    } // Retourne  l'adresse ip du bf

    public void setId (int id) {
        this.id=id;
    }

    public int getId (){
        return this.id;
    }

    public Transmissible execute (Context context, InetAddress address) {
        if (adresse.equals(getIpAddr(context))) {
            return new Transmission(ErreurTransmission.PROBLEMETRAITEMENT); //Empeche de s'auto-ajouter
        }
        Log.i("BF","BF recu");
        ObjectInputStream ois = null;
        try {
            //On verifie que le BF est bien en liste d'attente.
            //On commence par attendre 2 secondes que le serveur traite le client, au cas ou
            //Thread.sleep(2000);
            //Rendez vous
            Verrous.sync4.release();
            Verrous.sync3.acquire();

            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(context.getFilesDir(),"enCours.txt")
                            )
                    )
            ); //Contient le bf en cours de synchro

            InetAddress testAdresse = (InetAddress) ois.readObject(); //On verifie que l'ajout se fait avec la bonne personne
            Log.e("BF","Ici" + testAdresse.getHostAddress());
            Log.e("BF",adresse.getHostAddress());
            if (testAdresse.equals(adresse)) {
                Log.e("BF","Ici");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit(); // On ouvre un preference pour savoir si l'on synchronise avec l'admin

                Boolean admin = preferences.getBoolean("Admin",false);
                if (admin) {
                    BFList bfList = new BFList("admin.txt",context); //Selon que l'on synchro avec l'admin ou non, on enregistre dans differents fichiers
                    bfList.ajoutBF(this);
                    editor.putBoolean("Admin",false); //On ne synchro plus avec l'admin
                    editor.commit();
                    Intent intent = new Intent(context,MenuPrincipal2.class);
                    context.startActivity(intent);
                } else {
                    BFList bfList = new BFList("listeBF.txt", context);
                    bfList.ajoutBF(this);
                }
                Verrous.enCours.unlock(); // On deblogue la possibilite de faire un bump
                return new Transmission (true);
            } else return new Transmission(ErreurTransmission.IPNONRECONNUE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                Verrous.enCours.unlock();
                if (ois != null) {
                    ois.close();
                }
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
        resultat[i] = 0; // Numero fonction
        i++;
        for (; i < 1 + name.length(); i++) {
            resultat[i] = (byte) name.charAt(i-1); //copie nom bf
        }
        resultat[i] = '|'; //separateur
        i++;
        int t = i;
        for(;i< n;i++){
            resultat[i] = (byte) ad.charAt(i-t); //copie ip
        }
        return resultat;
    }

    public String toString(){return name+"µ"+adresse.getHostAddress();} //µ = transition

    public String getIpAddr(Context context) { //Obtient ip de l'appareil
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        return String.format("%d.%d.%d.%d",(ip & 0xff),(ip >> 8 & 0xff),(ip >> 16 & 0xff),(ip >> 24 & 0xff));
    }
}
