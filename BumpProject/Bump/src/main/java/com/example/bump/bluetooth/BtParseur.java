package com.example.bump.bluetooth;

/**
 * Created by Arturo on 10/02/14.
 */

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.nio.ByteBuffer;


import com.example.bump.actions.Color;
import com.example.bump.actions.Connexion;
import com.example.bump.client.Destinataire;

public class BtParseur {

    public static void sendMessage(String message,byte duree,Context context) {
        ByteBuffer b = ByteBuffer.allocate(message.length()+2);
        b.put((byte) 0);
        for (int i = 0; i<message.length(); i++) {
            b.putChar(message.charAt(i));
        }
        b.put(duree);
        byte[] bytes = b.array();
        send(bytes, context);
    }


    public static void  vibre(byte duree,Context context) {
        ByteBuffer b = ByteBuffer.allocate(2);
        b.put((byte)1);
        b.put(duree);
        byte[] bytes = b.array();

        send(bytes, context);
    }

    public static void sendColor(/*byte numeroCanal,*/Color color,Context context) {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.put((byte) 2);
        //b.put(numeroCanal);
        b.put((byte)color.getRouge());
        b.put((byte)color.getVert());
        b.put((byte)color.getBleu());
        byte[] bytes = b.array();
        send(bytes, context);
    }

    public static void clignote(byte frequence, byte duree, Context context) {
        ByteBuffer b = ByteBuffer.allocate(3);
        b.put((byte)3);
        b.put(frequence);
        b.put(duree);
        byte[] bytes = b.array();
        send(bytes, context);
    }

    public static void sendIp(Context context) {
        byte[] b = new byte[5];
        b[0] = 4;

        WifiManager wifiManager;
        wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        b[1] = (byte) (ip & 0xff);
        b[2] = (byte) (ip >> 8 & 0xff);
        b[3] = (byte) (ip >> 16 & 0xff);
        b[4] = (byte) (ip >> 24 & 0xff);

        send(b,context);

    }


    public static void send(byte[] bytes,Context context) { // pour l'instant test
        /*for (byte by : bytes) {
            System.out.format("%x ", by);
        }*/

        Intent i = new Intent(context,GestionBt.class);
        i.putExtra("message",bytes);
        context.startService(i);

    }


}
