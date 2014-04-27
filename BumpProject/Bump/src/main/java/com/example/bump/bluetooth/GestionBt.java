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


package com.example.bump.bluetooth;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.bump.BFList;
import com.example.bump.MenuPrincipal2;
import com.example.bump.Verrous;
import com.example.bump.actions.Connexion;
import com.example.bump.client.Destinataire;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * Created by jjuulliieenn on 16/02/14.
 */
public class GestionBt extends Service {

    private Handler reception;
    private Handler handlerStatut;
    private final String TAG = "GestionBt";
    private BtInterface btInterface = null;
    private String ip;
    private String monSC;
    private String tonSC;
    private int PORT = 4444;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        handlerStatut = new Handler(){
            @Override

            public void handleMessage (Message msg) {
                if (msg.arg1 == 1) {
                    Log.i(TAG,"Connexion au bracelet reussie" );
                } else Log.i(TAG,"Probleme lors de la connexion");
            }
        };

        reception = new Handler() {
            @Override

            public void handleMessage (Message msg) {
                Bundle b = msg.getData();
                byte[] recu = b.getByteArray("receivedData");
                try {
                    Log.i(TAG, Arrays.toString(recu));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                receive(recu);
            }
        };

        btInterface = new BtInterface(handlerStatut,reception,this);
        Intent i = new Intent(GestionBt.this, MenuPrincipal2.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        //btInterface.connect();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (btInterface == null) {
            //this.onCreate();
            Log.i(TAG, "Bug");
        }
        if (intent == null) return 0;
        byte[] b = intent.getByteArrayExtra("message"); //PP
        Log.i(TAG,"Avant envoie "+Arrays.toString(b));
        if (b != null)  btInterface.sendData(b);
        if (b[0] == 5 && b[1] == 4 && btInterface.estConnecte()) {
            Intent i = new Intent(GestionBt.this, MenuPrincipal2.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void receive (byte[] s) {

        if (s[0]==0) {
            receiveBump(s);
        }
        if (s[0]==1) {
            //receiveButton1();
        }
        if (s[0]==2) {
            //receiveButton2();
        }

    }

    private void receiveBump(byte[] s) {
        if (s.length != 7) {
            Log.d(TAG,"Message de mauvaise longueur ");
            return;
        }
        else {
            Log.d(TAG,"Traitement BUMP");
            StringBuilder str = new StringBuilder();
            str.append(s[1] & 0xff);
            str.append('.');
            str.append(s[2] & 0xff);
            str.append('.');
            str.append(s[3] & 0xff);
            str.append('.');
            str.append(s[4] & 0xff);
            ip = str.toString();
            Log.i(TAG,ip);
            str = new StringBuilder();
            str.append(s[5] & 0xff);
            monSC = str.toString();
            Log.i(TAG,"Mon SC :" + monSC);
            str = new StringBuilder();
            str.append(s[6] & 0xff);
            tonSC = str.toString();
            envoieBF();
        }

    }


    private void envoieBF() {

        BFList bfList = new BFList("admin.txt",this);

        if (bfList.isBF(ip)) { //c'est l'admin avec qui on a déjà bumpe
            //TODO Cas des boissons au bar
            return;
        }

        Log.i(TAG, "Debut protocole");
        DataOutputStream dos = null;
        ObjectOutputStream oos = null;
        try {
            Verrous.enCours.lock();
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(this.getFilesDir(),"enCours.txt")
                            )
                    )
            );
            oos.writeObject(InetAddress.getByName(ip));
            oos.flush();
            Verrous.monSC.lock();
            dos = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File (this.getFilesDir(),"monSC.txt")
                            )
                    )
            );
            dos.writeInt(Integer.parseInt(monSC));
            dos.flush();
            dos.close();

            Verrous.tonSC.lock();
            dos = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File (this.getFilesDir(),"tonSC.txt")
                            )
                    )
            );
            dos.writeInt(Integer.parseInt(tonSC));
            dos.flush();

            Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),PORT);
            destinataire.envoieObjet(new Connexion(Byte.parseByte(monSC),Byte.parseByte(tonSC),InetAddress.getByName(getIpAddr())),GestionBt.this);

            Verrous.sync1.release();
            Verrous.sync2.acquire();
            Verrous.sync3.release();
            Verrous.sync4.acquire();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.i(TAG,"Sans doute un flux null");
                e.printStackTrace();
            }
        }


    }

    public String getIpAddr() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        String ipString = String.format("%d.%d.%d.%d",(ip & 0xff),(ip >> 8 & 0xff),(ip >> 16 & 0xff),(ip >> 24 & 0xff));

        return ipString;
    }

}
