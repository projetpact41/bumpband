package com.example.bump.actions;

import android.content.Context;

import java.net.InetAddress;


public class Vestiaire_retrait implements Transmissible{

    @Override
    public Transmissible execute(Context context, InetAddress address) {
        return new Transmission(true);
    }

    @Override
    public byte[] toBytes() {
        byte[] b = new byte[1];
        b[0] = 13;
        return null;
    }


}
