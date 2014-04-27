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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bump.actions.BumpFriend;

import java.util.ArrayList;

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

        BFList bfList = new BFList("listeBF.txt",this);
        ArrayList<BumpFriend> l = bfList.getBFliste();

        int n = l.size();
        String[] s = new String[n];
        for (int i = 0; i < n; i++) {
            s[i] = l.get(i).getName();
            Log.i(TAG,l.get(i).getName());
        }
        return s;
    }

}
