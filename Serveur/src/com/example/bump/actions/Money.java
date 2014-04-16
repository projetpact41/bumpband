package com.example.bump.actions;

import java.net.InetAddress;

public class Money implements Transmissible {
	
	private byte money;
	
	public Money (byte money) {
		this.money = money;
	}

	@Override
	public Transmissible execute(InetAddress address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] toBytes() {
		byte[] b = new byte[2];
		b[0] = 10;
		b[1] = money;
		return b;
	}

}
