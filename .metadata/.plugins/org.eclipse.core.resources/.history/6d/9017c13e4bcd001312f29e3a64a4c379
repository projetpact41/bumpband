package com.example.bump.actions;


import java.net.InetAddress;

import admin.Banque;
import admin.FlashMob;

/**
 * Created by jjuulliieenn on 16/04/14.
 */
public class FMConfirmation implements Transmissible{

    private InetAddress address;

    public FMConfirmation (InetAddress address) {
        this.address = address;
    }

    @Override
    public Transmissible execute(InetAddress address) {
    	if (FlashMob.verification(this.address, address)) {
    		Banque.addMoney(address.getCanonicalHostName(), (byte) 5); 
    		return new Message("Vous venez de gagner 5 credits","Admin");
    	} else {
    		return new Message("Erreur lors de la verification du destinataire","Admin");
    	}
    }

    @Override
    public byte[] toBytes() {
        String str = address.getCanonicalHostName();
        byte[] b = new byte[1+str.length()];
        b[0] = 11;
        for (int i = 1; i < 1+str.length(); i++) {
            b[i] = (byte) str.charAt(i-1);
        }
        return b;
    }
}
