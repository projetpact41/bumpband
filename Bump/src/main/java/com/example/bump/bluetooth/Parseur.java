package com.example.bump.bluetooth;

/**
 * Created by Arturo on 13/01/14.
 */
import java.nio.ByteBuffer;

public class Parseur {
   public void receive(Byte[] s) {
    if (s[0]==0) {
        receiveBump(s);
        }
    if (s[0]==1) {
        receiveButton1();
        }
        if (s[0]==2) {
            receiveButton2();
        }

    }

    public  void sendMessage(String message,int duree) {
        ByteBuffer b = ByteBuffer.allocate(message.length()*2+7);
        b.put((byte) 0);
        for (int i = 0; i<message.length(); i++) {
            b.putChar(message.charAt(i));
        }
        b.putChar('/');
        b.put((byte)duree);
        byte[] bytes = b.array();
        send(bytes);
    }


    public void  vibre(int duree) {
        ByteBuffer b = ByteBuffer.allocate(5);
        b.put((byte)1);
        b.putInt(duree);
        byte[] bytes = b.array();

        send(bytes);
    }

    public void sendColor(int numeroCanal, Couleur color) {
        ByteBuffer b = ByteBuffer.allocate(20);
        b.putInt(2);
        b.putInt(numeroCanal);
        b.put((byte)color.getRouge());
        b.put((byte)color.getVert());
        b.put((byte)color.getBleu());
        byte[] bytes = b.array();
        send(bytes);
    }

    public void clignote(int frequence) {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.put((byte)3);
        b.putInt(frequence);
        byte[] bytes = b.array();
        send(bytes);
    }


    public void send(byte[] bytes) { // pour l'instant test
        for (byte by : bytes) {
            System.out.format("%x ", by);
        }

    }
}