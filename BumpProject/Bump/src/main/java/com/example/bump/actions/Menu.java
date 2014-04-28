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

import android.content.Context;
import android.util.Log;

import com.example.bump.BFList;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by jjuulliieenn on 15/04/14.
 */
public class Menu implements Transmissible, Serializable{

    private ArrayList<Boisson> menu = new ArrayList<Boisson>();;

    public void ajouteBoisson(Boisson b){
        Log.i("admin","ajout d'une boisson");
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

        Log.i("admin", "Menu execute");
        BFList bfList = new BFList("admin.txt",context);

        if (!bfList.isBF(address.getHostAddress())) return new Transmission(false); //Verification de l'admin

        ObjectOutputStream oos = null;
        try {
            Log.i("admin","Menu recu");
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