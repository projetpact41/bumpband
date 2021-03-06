package com.example.bump;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import com.example.bump.actions.BumpFriend;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class BumpFriendListe extends Activity {

    ListView listView;
    private final String TAG = "BFL";
    private String[] cases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bfl);

        Log.i(TAG, "Debut BFL");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bfl);

        listView = (ListView) findViewById(R.id.listView);
        Log.e(TAG,"Obtention listView");
        //Definition des cases du menu principal
        cases = lectureBF();
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

                //Intention

                Intent i = new Intent(BumpFriendListe.this,BumpFriendAction.class);
                i.putExtra("nom",itemValue);
                startActivity(i);

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bump_friend_liste, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_bfl, container, false);
            return rootView;
        }
    }

    @Override

    protected void onRestart () {
        super.onRestart();
        cases = lectureBF();
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

                //Intention

                Intent i = new Intent(BumpFriendListe.this,BumpFriendAction.class);
                i.putExtra("nom",itemValue);
                startActivity(i);
            }

        });
    }

    private String[] lectureBF () {
        /*ArrayList<String> l = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(
                    new File(this.getFilesDir(),"BFList.txt")
            );
            ObjectInputStream ois = new ObjectInputStream(
                    new BufferedInputStream(
                            fis
                    )
            );
            BumpFriend bf;
            Log.i(TAG,"Debut de la lecture des bf");
            try {
            while (true) {
                bf = (BumpFriend) ois.readObject();
                Log.i(TAG,"BF");
                l.add(bf.getName());
            }
            } catch (IOException e) {
                Log.i(TAG,"FIN des BF");
            }
            Log.i(TAG,"Fin de la lecture");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        int n = BumpFriendList.l.size();
        String[] s = new String[n];
        for (int i = 0; i < n; i++) {
            s[i] = BumpFriendList.l.get(i).getName();
        }
        return s;
    }

}
