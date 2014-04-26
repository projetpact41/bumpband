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

import java.io.Serializable;
import java.net.InetAddress;

public class Message implements Serializable, Transmissible{
    private String message;
    private String expediteur;
    private static final long serialVersionUID = -3487449280575641304L;

    public Message (String message,String expediteur) {
        this.message = message;
        this.expediteur = expediteur;
    }

    public String getMessage () {
        return message;
    }

    @Override
    public Transmissible execute(InetAddress address) {
        //On n'est pas cense envoyer de message a l'admin.
        return new Transmission(true);
    }

    public byte[]  toBytes() {
        int n = 1+message.length()+1+expediteur.length();
        byte[] resultat = new byte[n];
        int i = 0;
        resultat[i]= 3;
        i++;
        for (;i< 1 + message.length();i++) {
            resultat[i] = (byte) message.charAt(i-1);
        }
        resultat[i] = '|';
        i++;
        int t = i;
        for (;i< n;i++) {
            resultat[i] = (byte) expediteur.charAt(i-t);
        }
        return resultat;
    }
}
