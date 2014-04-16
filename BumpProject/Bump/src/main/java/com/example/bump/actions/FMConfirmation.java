package com.example.bump.actions;

import android.content.Context;

import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 16/04/14.
 */
public class FMConfirmation implements Transmissible{

    private InetAddress address;

    public FMConfirmation (InetAddress address) {
        this.address = address;
    }

    @Override
    public Transmissible execute(Context context, InetAddress address) {
        return null;
    }

    @Override
    public byte[] toBytes() {
        String str = address.getCanonicalHostName();
        byte[] b = new byte[1+str.length()];
        b[0] = 11;
        for (int i = 1; i < 1+str.length(); i++) {
            b[i] = (byte) str.charAt(i-1);
        }
        return b;
    }
}
