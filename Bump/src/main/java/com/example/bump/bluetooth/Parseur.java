package com.example.bump.bluetooth;

/**
 * Created by Arturo on 13/01/14.
 */
public class Parseur {
    public receive(String s) {
    if (s.charAt(0)=='0') {
        receiveBump(s);
        }
    if (s.charAt(0)=='1') {
        receiveButton1(s);
        }
        if (s.charAt(0)=='2') {
            receiveButton2(s);
        }

    }

    sendMessage(String message,int duree) {
        send("0/"+message+"/"+duree);
    }

    vibre(int duree) {
        send("1/"+duree);
    }

    sendColor(int numeroCanal, Couleur color) {
        send("2/"+numeroCanal+"/"+color.toString());
    }

    clignote(int frequence) {
        send("3/"+frequence);
    }

}
