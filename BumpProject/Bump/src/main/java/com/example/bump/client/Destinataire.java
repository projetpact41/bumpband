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


package com.example.bump.client;

import android.content.Context;
import android.util.Log;

import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Transmission;
import com.example.bump.serveur.Parseur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class Destinataire {

    private InetAddress hostname;
    private int port;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private Socket socket;
    byte[] b;
    private static final String TAG = "CLIENT";

    public Destinataire (InetAddress hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        Log.i(TAG,"Creation du destinataire "+hostname.toString());
    }

    public void envoieObjet (final Transmissible obj,final Context context) {
        Log.i(TAG,"Envoi Objet "+ obj.getClass().getName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(hostname,port);
                    Log.i(TAG,"Creation socket");

                    if (out == null) {
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                    }


                    Log.i(TAG,"Creation des flux");

                    b = obj.toBytes();
                    byte n = (byte) b.length;
                    byte[] temp = new byte[n+1];
                    temp[0] = n;
                    for (int i = 1; i < n+1; i++) {
                        temp[i] = b[i-1];
                    }

                    out.write(temp);

                    out.flush();
                    Log.i(TAG,"Envoi des donnees");

                    if (!(obj instanceof Transmission)) {
                        Transmissible t = null;
                        Transmissible t2 = null;

                        try{
                            Log.i(TAG,"Recuperation de l'objet");

                            byte size = in.readByte(); //Recoit la taille envoyee
                            Log.i(TAG,"Taille recue " + size);
                            b = new byte[size];
                            in.read(b);

                            t = (Transmissible) Parseur.parser(b);

                            if (t.getClass().getName().equals((BumpFriend.class).getName()))
                            {
                                Log.i(TAG,((BumpFriend) t).getName());
                            }
                            t2 = t.execute(context, hostname);
                            if (t2 != null) envoieObjet(t2,context);

                       } catch (NullPointerException e) {
                            Log.i(TAG, "Objet transmis invalide");
                        }
                        //throw new TransmissionException (ErreurTransmission.MESSAGENONDELIVRE); @TODO gerer les exceptions
                    }
                    Log.i(TAG,"Reussite");
                    //Intent intent = new Intent(context,com.example.bump.Reussie.class);
                    //context.startActivity(intent);

                    Thread.sleep(5000);

                } catch (IOException e) {
                    Log.i(TAG,"Erreur");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try{
                        if (socket != null)
                        socket.close();
                        if (out != null)
                        out.close();
                        if (in != null)
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

}
