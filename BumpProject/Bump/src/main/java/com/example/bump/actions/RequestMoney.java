package com.example.bump.actions;

import android.content.Context;

import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 15/04/14.
 */
public class RequestMoney implements Transmissible{


    @Override
    public Transmissible execute(Context context, InetAddress address) {
        return null;
    }

    @Override
    public byte[] toBytes() {
        byte[] b = new byte[1];
        b[0] = 9;
        return b;
    }
}
