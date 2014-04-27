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

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.example.bump.BFList;
import com.example.bump.MessageActivity;
import com.example.bump.R;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 18/01/14.
 */
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
    public Transmissible execute(Context context, InetAddress address) {
        BFList bfList = new BFList("listeBF.txt",context);

        if (!bfList.isBF(address.getHostAddress()))
            return new Transmission(ErreurTransmission.IPNONRECONNUE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle(expediteur)
                        .setContentText(message);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MessageActivity.class);
        resultIntent.putExtra("nom", expediteur);
        resultIntent.putExtra("message", message);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //On modifie la messagerie

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String messagerie = preferences.getString("messagerie","");
        messagerie.concat(expediteur+" : "+message+"\n");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("messagerie",messagerie);
        editor.commit();


        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
