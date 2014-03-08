package com.example.bump;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.Toast;

import com.example.bump.actions.BumpFriend;
import com.example.bump.bluetooth.BtParseur;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends ActionBarActivity {

    private EditText pseudo;
    private Button connexion;
    private static final String TAG = "PAGE_CONNEXION";
    private static final int PORT = 4444;
    public static final String FICHE_PERSO = "FICHE_PERSO";
    private SharedPreferences fichePerso;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
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
                if (!wifi) /*new AlertDialog.Builder(this).setTitle(R.string.avertissement).setMessage(R.string.non_wifi)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();*/
                    Toast.makeText(this,R.string.non_wifi,1000);

                //Fiche perso
                BumpFriend moi = null;
                Log.i(TAG,"Creation fiche perso");
                ObjectOutputStream oos = null;
                try {
                    InetAddress address = InetAddress.getByName(getIpAddr());
                    Log.i(TAG,"Mon adresse : " + address.toString());
                    Log.i(TAG,"Retour getIpAddr : "+getIpAddr());

                    moi = new BumpFriend(texte,address);

                    //Enregistrement de ma fiche BF

                    Log.i(TAG,this.getFilesDir().toString());

                    oos = new ObjectOutputStream (
                            new BufferedOutputStream(
                                    new FileOutputStream(
                                            new File(this.getFilesDir(), "fichePerso.txt")
                                    )
                            )
                    );
                    //Initialise tous les fichiers
                    oos.writeObject(moi);
                    oos.flush();
                    oos.close();

                    oos = new ObjectOutputStream (
                            new BufferedOutputStream(
                                    new FileOutputStream(
                                            new File(this.getFilesDir(), "monSC.txt")
                                    )
                            )
                    );
                    oos.close();

                    oos = new ObjectOutputStream (
                            new BufferedOutputStream(
                                    new FileOutputStream(
                                            new File(this.getFilesDir(), "tonSC.txt")
                                    )
                            )
                    );
                    oos.close();

                    oos = new ObjectOutputStream (
                            new BufferedOutputStream(
                                    new FileOutputStream(
                                            new File(this.getFilesDir(), "enCours.txt")
                                    )
                            )
                    );
                    oos.close();

                    oos = new ObjectOutputStream (
                            new BufferedOutputStream(
                                    new FileOutputStream(
                                            new File(this.getFilesDir(), "BFList.txt")
                                    )
                            )
                    );
                    oos.close();

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

                    startService(i); //Lancement du serveur

                    BtParseur.sendIp(this);
                    //BtParseur.clignote((byte)1,(byte)2,this);

                    //i = new Intent(MainActivity.this, SendClient.class);
                    i = new Intent(MainActivity.this, MenuPrincipal.class);
                    startActivity(i);
                    Log.i(TAG,"Lancement de l'autre activite");

                } catch (UnknownHostException e) {
                    Log.e(TAG, "Impossible de recuperer l'ip local");
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    Log.i(TAG,"Fichier non trouve");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.i(TAG, "IOException");
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

    public String getIpAddr() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        String ipString = String.format("%d.%d.%d.%d",(ip & 0xff),(ip >> 8 & 0xff),(ip >> 16 & 0xff),(ip >> 24 & 0xff));

        return ipString;
    }

}
