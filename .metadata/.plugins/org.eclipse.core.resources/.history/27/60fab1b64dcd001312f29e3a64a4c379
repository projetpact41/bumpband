package com.example.bump.actions;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Identifiant implements Transmissible {
	private int id;
    public Identifiant(int id) {
    	this.id=id;
    }
	@Override
	public Transmissible execute(InetAddress address) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte[] toBytes() {
		ByteBuffer b = ByteBuffer.allocate(5);
		b.put((byte) 5);
	    b.putInt(id);
	    byte[] bytes = b.array();
		return bytes;
	}
}
