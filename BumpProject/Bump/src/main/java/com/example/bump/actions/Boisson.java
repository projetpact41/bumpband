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

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 15/04/14.
 */
public class Boisson implements Transmissible, Serializable{
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
    public Transmissible execute(Context context, InetAddress address) {
        //Impossible a recevoir
        return null;
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

