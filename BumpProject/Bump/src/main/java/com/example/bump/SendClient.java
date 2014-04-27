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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bump.actions.BumpFriend;
import com.example.bump.client.Destinataire;

import java.net.InetAddress;

public class SendClient extends ActionBarActivity {

    private static final String TAG = "SEND_CLIENT";
    private EditText ipText;
    private Button bouton;
    private final int PORT = 4444;
    private SharedPreferences monProfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        ipText = (EditText) findViewById(R.id.ip);
        bouton = (Button) findViewById(R.id.send);

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Appui sur le bouton" );
                envoyerFiche();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_client, container, false);
            return rootView;
        }
    }

    private void envoyerFiche() {
        Log.i(TAG, "Entree dans la verification");
        if (ipText.getText() == null || ipText.getText().toString().equals("")) {
            //Boite d'alerte
            Log.e(TAG,"Probleme de pseudo");
            new AlertDialog.Builder(this).setTitle(R.string.alert_label).setMessage(R.string.mauvais_pseudo)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }
        else {
            Log.i(TAG,"Bonne connexion");
            String texte = ipText.getText().toString();

            // Verification de la connexion au reseau.
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            Log.i(TAG,"Creation des infos de connexion");

            if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
                Log.i(TAG,"Connexion OK");
                boolean wifi = (networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
                Log.d(TAG, "L'interface de connexion active est du Wifi : " + wifi);

                //On alerte l'utilisateur s'il n'est pas en wifi
                if (!wifi) new AlertDialog.Builder(this).setTitle(R.string.avertissement).setMessage(R.string.non_wifi)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

                monProfil = getSharedPreferences(MainActivity.FICHE_PERSO, Context.MODE_PRIVATE);
                String name = monProfil.getString("nom","NA");
                String ipT = monProfil.getString("IP","NA");
                Log.i(TAG,"Recuperation de la fiche perso");

                try {

                    Log.i(TAG,texte);
                    Destinataire destinataire = new Destinataire(InetAddress.getByName(texte),PORT);
                    Log.i(TAG,"Creation destinataire");
                    destinataire.envoieObjet(new BumpFriend(name,InetAddress.getByName(ipT)),SendClient.this);

                } catch (Exception e) {
                    Log.i(TAG,"Probleme d'ip");
                    e.printStackTrace();
                }
            }
        }
    }

}
