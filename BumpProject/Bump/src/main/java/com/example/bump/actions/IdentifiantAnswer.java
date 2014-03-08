package com.example.bump.actions;

import android.content.Context;

import com.example.bump.BFList;

import java.nio.ByteBuffer;

/**
 * Created by jjuulliieenn on 03/03/14.
 */
public class IdentifiantAnswer implements Transmissible {

    private int id;
    private String ip;

    public IdentifiantAnswer (int id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    @Override
    public Transmissible execute(Context context) {
        BFList bfList = new BFList("listeBF.txt",context);
        //bfList.changerId(id,ip);
        return new Transmission(true);
    }

    @Override
    public byte[] toBytes() {
        int n = 1 + 4 + ip.length();
        ByteBuffer b = ByteBuffer.allocate(n);
        b.put((byte) 6);
        b.putInt(id);
        for (int i = 5; i < n; i++) {
            b.putChar(ip.charAt(i-5));
        }
        return b.array();
    }
}
