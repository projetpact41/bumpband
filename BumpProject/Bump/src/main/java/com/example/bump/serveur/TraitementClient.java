package com.example.bump.serveur;

import android.content.Context;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.example.bump.actions.*;

/**
 * Created by jjuulliieenn on 01/01/14.
 */
public class TraitementClient extends Thread{

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final String TAG = "TRAITEMENT";
    private Context context;

    public TraitementClient (Socket clientSocket, Context context) {
        this.clientSocket = clientSocket;
        this.context = context;
        this.start();
    }

    public void run () {
        try {
            out = new ObjectOutputStream (clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            Transmissible obj = null;
            obj = (Transmissible) in.readObject();
            Log.i(TAG,"Lecture de l'objet");

            Transmissible t = null;

            while (obj != null && !(obj instanceof Transmission) ) { // On lance la bonne action en fonction de ce que l'on recoit
                try {
                    t = obj.execute(context);

                    out.writeObject(t);


                } catch (Exception e) {
                        out.writeObject(new Transmission (ErreurTransmission.TYPEINCONNU));
                }
                out.flush();
                if (t instanceof Transmission || t == null) // On suppose que l'on termine la conversation avec Transmission
                    break;
                else obj = (Transmissible) in.readObject();
            }
            if (obj != null && t != null) t.execute(context); //On fini la conversation avec une transmission
            Log.i(TAG,"FIN TRAITEMENT");
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
