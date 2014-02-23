package com.example.bump;

import android.R;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChoixCouleur extends Activity {

    private ListView listView;
    private final String TAG = "ChoixCouleur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"Debut ListView");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_couleur);

        listView = (ListView) findViewById(R.id.list);
        Log.e(TAG,"Obtention listView");
        //Definition des choix de couleurs possible
        String[] cases = {"ROUGE", "BLEU", "VERT"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, cases);
        listView.setAdapter(adapter);
        Log.i(TAG,"Mise en place de l'adapteur");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.e(TAG,"Appui sur un bouton" );

                int itemPosition     = position;
                String  itemValue    = (String) listView.getItemAtPosition(position);

                Log.e(TAG,itemValue);
                if (itemValue.equals("ROUGE")) {
                    //bluetooth.allumerRouge();
                    Log.i(TAG,itemValue);
                } else if (itemValue.equals("BLEU")) {
                    //bluetooth.allumerBleu();
                    Log.i(TAG,itemValue);
                } else if (itemValue.equals("VERT")) {
                   //bluetooth.allumerVert();
                    Log.i(TAG,itemValue);
                }

            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_menuprincipal, container, false);
            return rootView;
        }
    }

}

