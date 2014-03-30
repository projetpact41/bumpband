package com.example.bump.actions;

import android.content.Context;

import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 13/01/14.
 */
public interface Transmissible {
    public Transmissible execute(Context context, InetAddress address);
    public byte[] toBytes();
}
