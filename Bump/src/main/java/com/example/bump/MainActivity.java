package com.example.bump;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import com.example.bump.actions.BumpFriend;
import com.example.bump.serveur.Serveur;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends ActionBarActivity {

    private EditText pseudo;
    private Button connexion;
    private static final String TAG = "PAGE_CONNEXION";
    private static final int PORT = 4444;
    public static final String FICHE_PERSO = "FICHE_PERSO";
    private SharedPreferences fichePerso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //Souligne mais ca marche, je ne sais pas comment doneer les bonnes permissions
        StrictMode.setThreadPolicy(policy);

        //Instanciation des elements
        pseudo = (EditText) findViewById(R.id.pseudo);
        connexion = (Button) findViewById(R.id.buttonConnexion);

        //Ecoute du bouton
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Appui sur le bouton de connexion");
                verification();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void verification () {
        Log.i(TAG, "Entree dans la verification");
        if (pseudo.getText() == null || pseudo.getText().toString().equals("")) {
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
            String texte = pseudo.getText().toString();

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

                //Fiche perso
                BumpFriend moi = null;
                Log.i(TAG,"Creation fiche perso");
                try {
                    InetAddress address = InetAddress.getLocalHost();
                    moi = new BumpFriend(texte,address);

                    fichePerso = getSharedPreferences(FICHE_PERSO, Context.MODE_PRIVATE);
                    Log.i(TAG,"Recuperation Preferences");
                    SharedPreferences.Editor fichePersoEditor = fichePerso.edit();
                    Log.i(TAG,"EDITION");
                    fichePersoEditor.putString("nom",texte);
                    fichePersoEditor.putString("IP",address.getCanonicalHostName());
                    Log.i(TAG,"Edition");
                    fichePersoEditor.commit();

                    Log.i(TAG, "Enregistrement fiche perso");


                    // Il faut peut etre definir le port mieux
                    Log.i(TAG,"Serveur cree");

                    Intent i = new Intent(MainActivity.this,com.example.bump.serveur.Serveur.class);

                    startService(i);

                    i = new Intent(MainActivity.this, SendClient.class);
                    startActivity(i);
                    Log.i(TAG,"Lancement de l'autre activite");

                } catch (UnknownHostException e) {
                    Log.e(TAG,"Impossible de recuperer l'ip local");
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.i(TAG,"Probleme inconnu");
                    e.printStackTrace();
                }

            }
            else {
                Log.e(TAG, "Probleme de connexion au reseau");
                new AlertDialog.Builder(this).setTitle(R.string.alert_label).setMessage(R.string.probleme_reseau)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        }
    }

}
