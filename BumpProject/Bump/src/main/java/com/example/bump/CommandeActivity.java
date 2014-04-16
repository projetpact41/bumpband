package com.example.bump;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bump.actions.Boisson;
import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Transmissible;
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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        spinner = (Spinner) findViewById(R.id.boisson1);
        commander = (Button) findViewById(R.id.commander);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        ObjectInputStream ois = null;
        ArrayList<Boisson> menu = null;
        try {
            File file = new File(this.getFilesDir(),"menu.txt");
            if (file.exists()) {
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
        if (menu != null) {
            int n = menu.size();
            for (int i = 0; i < n; i++) {
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
                int off = spinner.getBaseline(); //distance a la premiere ligne
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
