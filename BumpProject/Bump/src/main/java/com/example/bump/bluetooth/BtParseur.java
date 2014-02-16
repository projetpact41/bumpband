package com.example.bump.bluetooth;

/**
 * Created by Arturo on 10/02/14.
 */

import android.content.Context;
import android.content.Intent;

import java.nio.ByteBuffer;


import com.example.bump.actions.Color;

public class BtParseur {

    public static void receive(byte[] s) {

    if (s[0]==0) {
        //receiveBump(s);
    }
    if (s[0]==1) {
        //receiveButton1();
    }
    if (s[0]==2) {
        //receiveButton2();
    }

}

    public static void sendMessage(String message,int duree,Context context) {
        ByteBuffer b = ByteBuffer.allocate(message.length()*2+7);
        b.put((byte) 0);
        for (int i = 0; i<message.length(); i++) {
            b.putChar(message.charAt(i));
        }
        b.putChar('/');
        b.put((byte)duree);
        byte[] bytes = b.array();
        send(bytes, context);
    }


    public static void  vibre(int duree,Context context) {
        ByteBuffer b = ByteBuffer.allocate(5);
        b.put((byte)1);
        b.putInt(duree);
        byte[] bytes = b.array();

        send(bytes, context);
    }

    public static void sendColor(int numeroCanal, Color color,Context context) {
        ByteBuffer b = ByteBuffer.allocate(20);
        b.putInt(2);
        b.putInt(numeroCanal);
        b.put((byte)color.getRouge());
        b.put((byte)color.getVert());
        b.put((byte)color.getBleu());
        byte[] bytes = b.array();
        send(bytes, context);
    }

    public static void clignote(int frequence,Context context) {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.put((byte)3);
        b.putInt(frequence);
        byte[] bytes = b.array();
        send(bytes, context);
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
