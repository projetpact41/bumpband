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

public class Color implements Serializable, Transmissible{
	private byte rouge;
	private byte vert;
	private byte bleu;
    private static final long serialVersionUID = -3487449280575641304L;
	
	public Color (byte rouge, byte vert, byte bleu) {
		this.rouge = rouge;
		this.vert = vert;
		this.bleu = bleu;
	}

	//Quelques getters
	
    public byte getRouge() {
        return rouge;
    }

    public byte getVert() {
        return vert;
    }

    public byte getBleu() {
        return bleu;
    }

    @Override
    public Transmissible execute(InetAddress address) {
        // On en recoit pas de color quand on est admin
        return null;
    }


    public byte[] toBytes() {
        byte[] resultat = new byte[4];
        resultat[0] = 1;
        resultat[1]=rouge;
        resultat[2]=vert;
        resultat[3]=bleu;
        return resultat;
    }
    
    public static Color Blanc = new Color((byte)255,(byte)255,(byte)255);

}