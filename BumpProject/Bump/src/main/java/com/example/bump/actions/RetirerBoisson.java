package com.example.bump.actions;

import android.content.Context;

import java.net.InetAddress;


public class RetirerBoisson implements Transmissible{

    @Override
    public Transmissible execute(Context context,InetAddress address) {
        return new Transmission(true);
    }

    @Override
    public byte[] toBytes() {
        byte[] b = new byte[1];
        b[0] = 14;
        return b;
    }



}