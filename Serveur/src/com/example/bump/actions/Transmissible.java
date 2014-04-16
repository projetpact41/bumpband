package com.example.bump.actions;

import java.net.InetAddress;

public interface Transmissible {
    public Transmissible execute(InetAddress address);
    public byte[] toBytes();
}
