package com.example.bump.bluetooth;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * Created by jjuulliieenn on 16/02/14.
 */
public class GestionBt extends Service {

    private Handler reception;
    private Handler handlerStatut;
    private final String TAG = "GestionBt";
    private BtInterface btInterface;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        handlerStatut = new Handler(){
            @Override

            public void handleMessage (Message msg) {
                if (msg.arg1 == 1) {
                    Log.i(TAG,"Connexion au bracelet reussie" );
                } else Log.i(TAG,"Probleme lors de la connexion");
            }
        };

        reception = new Handler() {
            @Override

            public void handleMessage (Message msg) {
                Bundle b = msg.getData();
                byte[] recu = b.getByteArray("receivedData");
                BtParseur.receive(recu);
            }
        };

        btInterface = new BtInterface(handlerStatut,reception);
        btInterface.connect();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        byte[] b = intent.getByteArrayExtra("message");
        btInterface.sendData(b);
        return super.onStartCommand(intent, flags, startId);
    }


}
