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

package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Transmission;
import com.example.bump.serveur.Parseur;



public class Destinataire { 
	
	//Ici nous trouvons la partie client

    private InetAddress hostname;
    private int port;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private Socket socket;
    byte[] b;

    public Destinataire (InetAddress hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void envoieObjet (final Transmissible obj) { //Permet d'envoyer un objet
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(hostname,port);//Ouverture de la socket

                    if (out == null) {
                    // r�cup�ration des flux
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                    }

                    b = obj.toBytes(); //Transformation en tableau de bytes
                    byte n = (byte) b.length;
                    byte[] temp = new byte[n+1];
                    temp[0] = n;
                    for (int i = 1; i < n+1; i++) {
                        temp[i] = b[i-1];
                    }

                    out.write(temp); //Envoie des bytes

                    out.flush();

                    if (!(obj instanceof Transmission)) { //Cela signifierait que la conversation serait
                    	//finie
                        Transmissible t = null;
                        Transmissible t2 = null;
                        //On se prepare a recevoir
                        try{

                            byte size = in.readByte(); //Recoit la taille envoyee
                            b = new byte[size];
                            in.read(b); //On lit

                            t = (Transmissible) Parseur.parser(b); //On inteprete
                            t2 = t.execute(hostname);
                            if (t2 != null) envoieObjet(t2);

                       } catch (NullPointerException e) {
                            System.out.println("Objet transmis invalide");
                        }
                        //@TODO gerer les exceptions
                    }

                    Thread.sleep(5000); //Permet de ne pas fermer la communication trop tot

                }catch (EOFException e) {
                	//Je ne comprends pas pourquoi ca arrive
                } 
                catch (IOException e) {
                    System.out.println("Erreur");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try{
                        if (socket != null)
                        socket.close();
                        if (out != null)
                        out.close();
                        if (in != null)
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

}
