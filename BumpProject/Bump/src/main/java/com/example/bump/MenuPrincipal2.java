package com.example.bump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuPrincipal2 extends Activity {//VRAI MENU PRINCIPAL

    private Button button_about;
    private Button button_bfl;
    private Button button_color;
    private Button informations;
    private Button commande;
    private final String TAG = "MenuPrincipal2";

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"Debut ListView");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal2);

        button_about = (Button) findViewById(R.id.button_about);
        button_bfl = (Button) findViewById(R.id.button_bfl);
        button_color = (Button) findViewById(R.id.button_color);
        informations = (Button) findViewById(R.id.info);
        commande = (Button) findViewById(R.id.commande);

        //Ecoute du bouton
        button_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Appui sur le bouton about");
                Intent i = new Intent(MenuPrincipal2.this,APropos.class);
                startActivity(i);
            }
        });

        button_bfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Appui sur le bouton bfl");
                Intent i = new Intent(MenuPrincipal2.this,BumpFriendListe.class);
                startActivity(i);
            }
        });

        button_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Appui sur le bouton color");
                Intent i = new Intent(MenuPrincipal2.this,ColorMenu.class);
                startActivity(i);
            }
        });

        informations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Bouton information");
                Intent i = new Intent(MenuPrincipal2.this,Informations.class);
                startActivity(i);
            }
        });

        commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Bouton commande");
                Intent i = new Intent(MenuPrincipal2.this,CommandeActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal2, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_menu_principal2, container, false);
            return rootView;
        }
    }

}

