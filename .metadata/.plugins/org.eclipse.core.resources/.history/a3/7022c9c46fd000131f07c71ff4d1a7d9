package com.example.bump.actions;

import java.net.InetAddress;

import admin.Commande;

public class RetirerBoisson implements Transmissible{

	@Override
	public Transmissible execute(InetAddress address) {
		Commande.remove(address.getCanonicalHostName());
		return new Transmission(true);
	}

	@Override
	public byte[] toBytes() {
		byte[] b = new byte[1];
		b[0] = 14;
		return b;
	}

	
	
}
