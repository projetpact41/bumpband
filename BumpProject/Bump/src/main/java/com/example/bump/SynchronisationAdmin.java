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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bump.actions.BumpFriend;
import com.example.bump.client.Destinataire;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;


public class SynchronisationAdmin extends ActionBarActivity {

    private Button passer, synchro;
    private EditText iptext;
    private Context context = this;
    private String TAG = "SynchroAdmin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronisation_admin);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Admin",true);
        editor.commit();

        passer = (Button) findViewById(R.id.passeradmin);
        iptext = (EditText) findViewById(R.id.ipAdmin);
        synchro = (Button) findViewById(R.id.synchro);

        synchro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("Admin",true);
                editor.commit();
                envoieBF();
                Intent intent = new Intent(SynchronisationAdmin.this,BluetoothConnexion.class);
                startActivity(intent);
            }
        });

        passer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("Admin",false);
                editor.commit();
                Intent intent = new Intent(SynchronisationAdmin.this,BluetoothConnexion.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.synchronisation_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void envoieBF() {
        Log.i(TAG, "Debut protocole");
        if(iptext.getText()==null) return;

        DataOutputStream dos = null;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(this.getFilesDir(),"enCours.txt")
                            )
                    )
            );
            oos.writeObject(InetAddress.getByName(iptext.getText().toString()));
            oos.flush();
            dos = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File (this.getFilesDir(),"monSC.txt")
                            )
                    )
            );
            dos.writeInt(0);
            dos.flush();
            dos.close();

            dos = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File (this.getFilesDir(),"tonSC.txt")
                            )
                    )
            );
            dos.writeInt(0);
            dos.flush();

            ObjectInputStream ois = new ObjectInputStream(

                    new FileInputStream(
                            new File(context.getFilesDir(),"fichePerso.txt")
                    )

            );

            BumpFriend bfTemp = (BumpFriend) ois.readObject();

            Destinataire destinataire = new Destinataire(InetAddress.getByName(iptext.getText().toString()),4444);
            destinataire.envoieObjet(bfTemp,this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.i(TAG,"Sans doute un flux null");
                e.printStackTrace();
            }
        }
    }

    public String getIpAddr() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        String ipString = String.format("%d.%d.%d.%d",(ip & 0xff),(ip >> 8 & 0xff),(ip >> 16 & 0xff),(ip >> 24 & 0xff));

        return ipString;
    }

}
