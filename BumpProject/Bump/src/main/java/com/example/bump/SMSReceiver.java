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

            Verrous.sms = true;

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
            //BtParseur.clignote((byte) 2,(byte) 5,context);
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
        else throw new UnsupportedOperationException("Pas implemente");
    }
}
