package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.naming.Context;

import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Transmission;
import com.example.bump.serveur.Parseur;



public class Destinataire {

    private InetAddress hostname;
    private int port;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private Socket socket;
    byte[] b;
    private static final String TAG = "CLIENT";

    public Destinataire (InetAddress hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void envoieObjet (final Transmissible obj,final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(hostname,port);

                    if (out == null) {
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                    }

                    b = obj.toBytes();
                    byte n = (byte) b.length;
                    byte[] temp = new byte[n+1];
                    temp[0] = n;
                    for (int i = 1; i < n+1; i++) {
                        temp[i] = b[i-1];
                    }

                    out.write(temp);

                    out.flush();

                    if (!(obj instanceof Transmission)) {
                        Transmissible t = null;
                        Transmissible t2 = null;

                        try{

                            byte size = in.readByte(); //Recoit la taille envoyee
                            b = new byte[size];
                            in.read(b);

                            t = (Transmissible) Parseur.parser(b);
                            t2 = t.execute();
                            if (t2 != null) envoieObjet(t2,context);

                       } catch (NullPointerException e) {
                            System.out.println("Objet transmis invalide");
                        }
                        //@TODO gerer les exceptions
                    }

                    Thread.sleep(5000);

                } catch (IOException e) {
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
