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

package com.example.bump.serveur;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.example.bump.actions.ErreurTransmission;
import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Transmission;

public class TraitementClient extends Thread {

	private Socket clientSocket;
	private DataOutputStream out; //Flux d'output
	private DataInputStream in; //Flux d'input
	
	public TraitementClient (Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.start(); //On lance le traitement
	}
	
	public void run () {
        try {
            byte[] b;
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            byte size = in.readByte(); //Recoit la taille envoyee
            b = new byte[size];
            in.read(b);

            Transmissible obj = null;
            obj = (Transmissible) Parseur.parser(b);
            

            Transmissible t = null;

            while (obj != null && !(obj instanceof Transmission) ) { // On lance la bonne action en fonction de ce que l'on recoit
                try {
                    t = obj.execute(clientSocket.getInetAddress());
                    if (t != null) {
                    b = t.toBytes().clone();
                    byte n = (byte) b.length;
                    byte[] temp = new byte[n+1];
                    temp[0] = n;
                    for (int i = 1; i < n+1; i++) {
                        temp[i] = b[i-1];
                    }

                    out.write(temp);
                    }

                } catch (Exception e) {
                    if (t != null) {
                    Transmission u = new Transmission (ErreurTransmission.TYPEINCONNU);
                    b = u.toBytes().clone();
                    byte n = (byte) b.length;
                    e.printStackTrace();
                    byte[] temp = new byte[n+1];
                    temp[0] = n;
                    for (int i = 1; i < n+1; i++) {
                        temp[i] = b[i-1];
                    }
                        out.write(b);
                    }
                }
                out.flush();
                if (t instanceof Transmission || t == null) // On suppose que l'on termine la conversation avec Transmission
                    break;
                else {
                    size = in.readByte(); //Recoit la taille envoyee
                    b = new byte[size];
                    in.read(b);

                    obj = (Transmissible) Parseur.parser(b);
                    System.out.println(obj.getClass().getCanonicalName());
                }
            }
            if (obj != null && t != null && (t instanceof Transmission)) t.execute(clientSocket.getInetAddress()); //On fini la conversation avec une transmission
            if (obj != null && t != null && (obj instanceof Transmission)) obj.execute(clientSocket.getInetAddress()); //On fini la conversation avec une transmission
            
            Thread.sleep(1000); // On ne ferme pas la socket trop tot

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
