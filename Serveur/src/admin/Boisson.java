package admin;

import java.net.InetAddress;

import com.example.bump.actions.Color;
import com.example.bump.actions.Message;
import com.example.bump.actions.Transmissible;

/**
 * Created by Arturo on 07/04/2014.
 */
public class Boisson implements Transmissible{
    private String nom;
    private byte prix ;
    private Color code ;
    private byte degre ;

    public Boisson(String nom, byte prix, Color code, byte degre) {
        this.nom = nom;
        this.prix = prix ;
        this.code = code ;
        this.degre = degre ;
    }

    public byte getPrix() {
        return prix;
    }

    public Color getCode() {
        return code;
    }

    public byte getDegre() {
        return degre;
    }

    public String getNom () { return nom;}

    @Override
    public Transmissible execute(InetAddress address) {
    	Commande.add(address.getCanonicalHostName(), this);
        return new Message("Nous confirmons la commande de "+nom+" pour "+prix+" credits.","Admin");
    }

    public byte[] toBytes(){
        byte[] b =new byte[6+nom.length()];
        b[0]=8;
        b[1]=prix;
        b[2]=code.getRouge();
        b[3]=code.getVert();
        b[4]=code.getBleu();
        b[5]=degre;
        for (int i = 6; i < 6+nom.length() ; i++) {
            b[i] = (byte) nom.charAt(i-6);
        }
        return b;
    }
}