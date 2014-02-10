package com.example.bump;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.bump.actions.Message;
import com.example.bump.client.Destinataire;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

public class BumpFriendAction extends ActionBarActivity {

    private final String TAG = "BFA";
    private EditText message;
    private Button envoyer;
    private BumpFriend bf;
    private final int PORT = 4444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bfa);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        message = (EditText) findViewById(R.id.message);
        envoyer = (Button) findViewById(R.id.buttonEnvoi);

        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Appui sur le bouton d'envoi");
                envoyer();
            }
        });

    }

    private void envoyer() {
        Log.i(TAG, "Entree dans la verification");
        Log.i(TAG,"Bonne connexion");
        String texte = message.getText().toString();

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
                try {

                    Log.i(TAG,texte);
                    Destinataire destinataire = new Destinataire(bf.getAdresse(),PORT);
                    Log.i(TAG,"Creation destinataire"+bf.getAdresse());
                    ObjectInputStream ois = new ObjectInputStream(
                            new BufferedInputStream(
                                    new FileInputStream(
                                            new File("fichePerso.txt")
                                    )
                            ));
                    BumpFriend moi = (BumpFriend) ois.readObject();
                    destinataire.envoieObjet(new Message(texte,moi.getName()),BumpFriendAction.this);


                } catch (Exception e) {
                    Log.i(TAG,"Probleme d'ip");
                    e.printStackTrace();
                }
        }

    }

    @Override

    protected void onStart() {
        super.onStart();
        Log.i(TAG,"Debut onStart");

        Bundle extra = getIntent().getExtras();
        String nom = extra.getString("nom");

        bf = lectureBF(nom);

    }

    private BumpFriend lectureBF (String s) {
        /*try {

            ObjectInputStream ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(this.getFilesDir(),"BFList.txt")
                            )
                    )
            );
            Log.i(TAG,"Debut de la lecture des bf");
            BumpFriend bf;

            try {
                while (true) {
                    bf = (BumpFriend) ois.readObject();
                    if(bf.getName().equals(s)) return bf;

                }
            } catch (IOException e) {
                Log.i(TAG,"FIN des BF");
            }
            Log.i(TAG,"Fin de la lecture");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
*/
        for (BumpFriend bf : BumpFriendList.l) {
            if (bf.getName().equals(s)) return bf;
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bump_friend_action, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_bfa, container, false);
            return rootView;
        }
    }

}
