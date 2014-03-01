package com.example.bump;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.bump.actions.Color;
import com.example.bump.bluetooth.BtParseur;

public class SMSReceiver extends BroadcastReceiver {

    private final String ROUGE = "ROUGE";
    private final String BLEU = "BLEU";
    private final String VERT = "VERT";

    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            Color temp = new Color((byte)preferences.getInt(ROUGE,0),(byte)preferences.getInt(VERT,0),(byte)preferences.getInt(BLEU,0));

            BtParseur.sendColor(new Color((byte) 255,(byte) 0,(byte) 0),context);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(ROUGE, 255);
            editor.putInt(VERT,0);
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

        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
