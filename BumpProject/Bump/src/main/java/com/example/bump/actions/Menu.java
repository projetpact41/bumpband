package com.example.bump.actions;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by jjuulliieenn on 15/04/14.
 */
public class Menu implements Transmissible{

    private ArrayList<Boisson> menu ;

    public void ajouteBoisson(Boisson b){
        menu.add(b);
    }

    private void retireBoisson(Boisson b){
        menu.remove(b);
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
    public Transmissible execute(Context context, InetAddress address) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(context.getFilesDir(),"menu.txt")
                            )
                    )
            );
            oos.writeObject(menu);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Transmission(true);
    }
}