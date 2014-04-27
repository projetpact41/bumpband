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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.RequestMoney;
import com.example.bump.actions.Transmissible;
import com.example.bump.client.Destinataire;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Informations extends ActionBarActivity {

    private TextView nom;
    private TextView ip;
    private TextView compte;
    private Button actualiser;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);
        nom = (TextView) findViewById(R.id.nom);
        ip = (TextView) findViewById(R.id.ip);
        compte = (TextView) findViewById(R.id.compte);
        actualiser = (Button) findViewById(R.id.actualiser);
        actualiser();
        actualiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BFList bfList = new BFList("admin.txt",context);
                if (bfList.getBFliste().size() != 0) {
                    BumpFriend bf = bfList.getBFliste().get(0);
                    Destinataire destinataire = new Destinataire(bf.getAdresse(), 4444);
                    destinataire.envoieObjet((Transmissible) (new RequestMoney()), context);
                    actualiser();
                }
            }
        });
    }

    private void actualiser () {
        ObjectInputStream ois = null;
        BumpFriend bf;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(this.getFilesDir(), "fichePerso.txt")
                            )
                    )
            );
            bf = (BumpFriend) ois.readObject();
            nom.setText(bf.getName());
            ip.setText(bf.getIpAddr(this));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            compte.setText("Solde : " +preferences.getInt("money",0));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (ois != null) try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.informations, menu);
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

}
