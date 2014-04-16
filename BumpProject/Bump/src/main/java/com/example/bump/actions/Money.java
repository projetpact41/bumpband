package com.example.bump.actions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 15/04/14.
 */
public class Money implements Transmissible {

    private byte money;

    public Money (byte money) {
        this.money = money;
    }

    @Override
    public Transmissible execute(Context context, InetAddress address) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("money",money);
        editor.commit();
        return new Transmission(true);
    }

    @Override
    public byte[] toBytes() {
        byte[] b = new byte[2];
        b[0] = 10;
        b[1] = money;
        return b;
    }

}
