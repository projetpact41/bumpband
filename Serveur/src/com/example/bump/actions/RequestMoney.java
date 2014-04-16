package com.example.bump.actions;

import java.net.InetAddress;

import admin.Banque;

public class RequestMoney implements Transmissible{


    @Override
    public Transmissible execute(InetAddress address) {
    	byte money = Banque.getMoney(address.getCanonicalHostName());
        return ((Transmissible) new Money(money));
    }

    @Override
    public byte[] toBytes() {
        byte[] b = new byte[1];
        b[0] = 9;
        return b;
    }
}