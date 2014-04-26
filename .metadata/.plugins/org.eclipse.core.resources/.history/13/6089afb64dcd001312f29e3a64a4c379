package com.example.bump.actions;

import java.net.InetAddress;
import java.nio.ByteBuffer;

import javax.naming.Context;

public class IdentifiantAnswer implements Transmissible {

    private int id;
    private String ip;

    public IdentifiantAnswer (int id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    @Override
    public Transmissible execute(InetAddress address) {

        BFList bfList = new BFList("admin.txt");

        if (!bfList.isBF(address.getHostAddress()))
            return new Transmission(ErreurTransmission.IPNONRECONNUE);

        bfList = new BFList("listeBF.txt");
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

