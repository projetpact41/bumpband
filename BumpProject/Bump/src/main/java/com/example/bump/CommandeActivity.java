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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bump.actions.Boisson;
import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.RetirerBoisson;
import com.example.bump.actions.Transmissible;
import com.example.bump.actions.Vestiaire_ajout;
import com.example.bump.actions.Vestiaire_retrait;
import com.example.bump.client.Destinataire;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class CommandeActivity extends ActionBarActivity {

    Spinner spinner;
    Button commander;
    Button retirer_boisson, ajout_vest, retrait_vest;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        spinner = (Spinner) findViewById(R.id.boisson1);
        commander = (Button) findViewById(R.id.commander);
        retirer_boisson = (Button) findViewById(R.id.retirer_boisson);
        ajout_vest = (Button) findViewById(R.id.ajout_vest);
        retrait_vest = (Button) findViewById(R.id.restirer_vest);

        context = this;
        retirer_boisson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BFList bfList = new BFList("admin.txt",context);
                ArrayList<BumpFriend> al = bfList.getBFliste();
                if (al != null && al.size() != 0) {
                    BumpFriend admin = al.get(0);
                    Destinataire destinataire = new Destinataire(admin.getAdresse(),4444);
                    destinataire.envoieObjet(new RetirerBoisson(),context);
                }
            }
        });

        ajout_vest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BFList bfList = new BFList("admin.txt",context);
                ArrayList<BumpFriend> al = bfList.getBFliste();
                if (al != null && al.size() != 0) {
                    BumpFriend admin = al.get(0);
                    Destinataire destinataire = new Destinataire(admin.getAdresse(),4444);
                    destinataire.envoieObjet(new Vestiaire_ajout(),context);
                }
            }
        });

        retrait_vest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BFList bfList = new BFList("admin.txt",context);
                ArrayList<BumpFriend> al = bfList.getBFliste();
                if (al != null && al.size() != 0) {
                    BumpFriend admin = al.get(0);
                    Destinataire destinataire = new Destinataire(admin.getAdresse(),4444);
                    destinataire.envoieObjet(new Vestiaire_retrait(),context);
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        ObjectInputStream ois = null;
        ArrayList<Boisson> menu = null;
        try {
            File file = new File(this.getFilesDir(),"menu.txt");
            if (file.exists()) {
                Log.i("admin","menu.txt existe");
                ois = new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(
                                        new File(this.getFilesDir(), "menu.txt")
                                )
                        )
                );

            menu = (ArrayList<Boisson>) ois.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Log.i("admin","Apres lecture menu");

        if (menu != null) {
            int n = menu.size();
            Log.i("admin", "Menu non null");
            for (int i = 0; i < n; i++) {
                Log.i("admin", "Taille non null");
                Boisson boisson = menu.get(i);
                adapter.add(new StringBuilder().append(boisson.getNom()).append(" - ").append(boisson.getPrix()).append(" credit(s)").toString());
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final ArrayList<Boisson> finalMenu = menu;
        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int off = spinner.getBaseline(); //distance a la premiere ligne
                int off = spinner.getSelectedItemPosition();
                if (finalMenu != null) {
                    Toast.makeText(context,finalMenu.get(off).getNom() + " commande",500);

                    BFList bfList = new BFList("admin.txt",context);
                    ArrayList<BumpFriend> al = bfList.getBFliste();
                    if (al != null && al.size() != 0) {
                        BumpFriend admin = al.get(0);
                        Destinataire destinataire = new Destinataire(admin.getAdresse(),4444);
                        destinataire.envoieObjet((Transmissible) finalMenu.get(off),context);
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.commande, menu);
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
