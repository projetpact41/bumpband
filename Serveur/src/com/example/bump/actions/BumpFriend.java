package com.example.bump.actions;


import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class BumpFriend implements Serializable {

    private InetAddress adresse; //Adresse du BF
    private String name;
    private static final long serialVersionUID = -5929515104076961259L;

    public BumpFriend (String name, InetAddress adresse) {
        this.adresse = adresse;
        this.name = name;
    }

}
