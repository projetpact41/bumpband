package com.example.bump.bluetooth;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.bump.actions.Color;
import com.example.bump.actions.Connexion;
import com.example.bump.client.Destinataire;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.nio.ByteBuffer;

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
                    Log.i(TAG, new String(recu,"US-ASCII"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                receive(recu);
            }
        };

        btInterface = new BtInterface(handlerStatut,reception);
        //btInterface.connect();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (btInterface == null) {
            //this.onCreate();
            Log.i(TAG, "Bug");
        }
        byte[] b = intent.getByteArrayExtra("message");
        if (b != null)  btInterface.sendData(b);
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
            Log.d(TAG,"Message de mauvaise longueur "+ new String(s));
            return;
        }
        else {
            Log.d(TAG,"Traitement BUMP");
            StringBuilder str = new StringBuilder();
            str.append((char)s[1]);
            str.append('.');
            str.append((char)s[2]);
            str.append('.');
            str.append((char)s[3]);
            str.append('.');
            str.append((char)s[4]);
            ip = str.toString();
            str = new StringBuilder();
            str.append((char) s[5]);
            monSC = str.toString();
            str = new StringBuilder();
            str.append((char) s[6]);
            monSC = str.toString();
            envoieBF();
        }

    }


    private void envoieBF() {
        Log.i(TAG, "Debut protocole");
        DataOutputStream dos = null;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(this.getFilesDir(),"enCours.txt")
                            )
                    )
            );
            oos.writeObject(InetAddress.getByName(ip));
            oos.flush();
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
