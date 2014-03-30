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

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, cases);
        listView.setAdapter(adapter);
        Log.i(TAG,"Mise en place de l'adapteur");*/

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

        /*button_about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.e(TAG,"Appui sur un bouton" );

                int itemPosition     = position;
                String  itemValue    = (String) listView.getItemAtPosition(position);

                Log.e(TAG,itemValue);
                if (itemValue.equals("Liste de BumpFriend")) {
                    Intent i = new Intent(MenuPrincipal.this,BumpFriendListe.class);
                    startActivity(i);
                    Log.i(TAG,itemValue);
                } else if (itemValue.equals("Choix couleur")) {
                    //A faire en temps voulu
                    Intent i = new Intent (MenuPrincipal.this,ColorMenu.class);
                    startActivity(i);
                    Log.i(TAG,itemValue);
                } else if (itemValue.equals("Simulation Bump")) {
                    Intent i = new Intent (MenuPrincipal.this,SimuBump.class);
                    startActivity(i);
                    Log.i(TAG,itemValue);
                } else if (itemValue.equals("A propos")) {
                    Intent i = new Intent (MenuPrincipal.this,APropos.class);
                    startActivity(i);
                    Log.i(TAG,itemValue);
                } /*else if (itemValue.equals("Test BT")) {
                    Intent i = new Intent (MenuPrincipal.this, GestionBt.class);
                    byte[] b = new byte[1];
                    b[0] = (byte) 0;
                    i.putExtra("message",b);
                    startService(i);
                    Log.i(TAG,itemValue);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    i = new Intent (MenuPrincipal.this, GestionBt.class);
                    b = new byte[1];
                    b[0] = (byte) 1;
                    i.putExtra("message",b);
                    startService(i);
                    Log.i(TAG,itemValue);
                }

            }

        });*/


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

