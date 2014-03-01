package com.example.bump;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.bump.actions.Color;
import com.example.bump.bluetooth.BtParseur;

/**
 * Created by jjuulliieenn on 01/03/14.
 */
public class MyPhoneStateListener extends PhoneStateListener {

    private final String ROUGE = "ROUGE";
    private final String BLEU = "BLEU";
    private final String VERT = "VERT";
    private Context context;

    public MyPhoneStateListener (Context context) {
        super();
        this.context = context;
    }

    public void onCallStateChanged(int state,String incomingNumber){
        switch(state){
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("DEBUG", "Innocupe");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG", "Decroche");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("DEBUG", "Sonne");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                Color temp = new Color((byte)preferences.getInt(ROUGE,0),(byte)preferences.getInt(VERT,0),(byte)preferences.getInt(BLEU,0));

                BtParseur.sendColor(new Color((byte) 0, (byte) 255, (byte) 0), context);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(ROUGE, 0);
                editor.putInt(VERT,255);
                editor.putInt(BLEU,0);
                editor.commit();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BtParseur.clignote((byte) 2,(byte) 5,context);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                editor.putInt(ROUGE, temp.getRouge());
                editor.putInt(VERT,temp.getVert());
                editor.putInt(BLEU,temp.getBleu());
                editor.commit();
                break;
        }
    }
}
