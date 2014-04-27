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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.bump.BFList;

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

        BFList bfList = new BFList("admin.txt",context);

        //Verification de l'admin
        if (!bfList.isBF(address.getHostAddress())) return new Transmission(false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("money",money);
        editor.commit(); //On enregistre les credits qu'il nous reste dans une preference
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
