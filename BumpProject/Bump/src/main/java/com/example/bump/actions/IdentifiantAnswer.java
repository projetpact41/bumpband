//The MIT License (MIT)
//
//Copyright (c) 2014 Julien ROMERO
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.


package com.example.bump.actions;

import android.content.Context;

import com.example.bump.BFList;

import java.net.InetAddress;
import java.nio.ByteBuffer;


public class IdentifiantAnswer implements Transmissible {

    private int id;
    private String ip;

    public IdentifiantAnswer (int id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    @Override
    public Transmissible execute(Context context,InetAddress address) {

        BFList bfList = new BFList("admin.txt",context);

        if (!bfList.isBF(address.getHostAddress())) //Verification que l'on parle avec l'admin
            return new Transmission(ErreurTransmission.IPNONRECONNUE);

        bfList = new BFList("listeBF.txt",context);
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
