package com.example.bump.actions;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
