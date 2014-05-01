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

import com.example.bump.Verrous;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public Connexion (byte monSC, byte tonSC, InetAddress adresse) {
		this.monSC = monSC;
		this.tonSC = tonSC;
        this.adresse = adresse;
	}

    @Override
    public Transmissible execute(Context context, InetAddress address) {
        try {
            Log.i("bf","connexion semaphore1");
            //Thread.sleep(1000); //Le temps de se scynchroniser.
            //Verification des security codes
            Verrous.sync3.release();
            Verrous.sync4.acquire();

            Log.i("bf","connexion semaphore2");
            dis = new DataInputStream(
                                  new BufferedInputStream(
                                    new FileInputStream(
                                      new File(context.getFilesDir(),"monSC.txt"))));
            int sC = dis.readInt();
            dis.close();
            //Verrous.monSC.unlock();
            if (sC != monSC) return new Transmission(ErreurTransmission.SCINCORRECT);
            dis = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(context.getFilesDir(),"tonSC.txt"))));
            sC = dis.readInt();
            //Verrous.tonSC.unlock();
            if (sC != tonSC) return new Transmission(ErreurTransmission.SCINCORRECT);

            //On envoie alors sa fiche bumpfriend perso

            Log.i("Connexion", context.getFilesDir().toString());
            ois = new ObjectInputStream(

                            new FileInputStream(
                                    new File(context.getFilesDir(),"fichePerso.txt")
                            )

            );

            BumpFriend bfTemp = (BumpFriend) ois.readObject();
            String TAG = "ConnexionClass";
            Log.i(TAG, bfTemp.getAdresse().toString());
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
