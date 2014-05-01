package com.example.bump.actions;

import java.net.InetAddress;

import admin.Vestiaire;

public class Vestiaire_retrait implements Transmissible{

	@Override
	public Transmissible execute(InetAddress address) {
		Vestiaire.TraitementVestiaire(address.getCanonicalHostName());
		return new Transmission(true);
	}

	@Override
	public byte[] toBytes() {
		byte[] b = new byte[1];
		b[0] = 13;
		return null;
	}

	
}
