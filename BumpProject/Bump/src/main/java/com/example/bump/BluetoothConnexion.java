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

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.bump.bluetooth.BtParseur;

import java.util.Set;


public class BluetoothConnexion extends ActionBarActivity {

    private Button connexion;
    private Button passer;
    Spinner spinner;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connexion);

        connexion = (Button) findViewById(R.id.connexionblue);
        passer = (Button) findViewById(R.id.passerblue);
        spinner = (Spinner) findViewById(R.id.perif);

        Set<BluetoothDevice> setAppareilsApparies = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        final BluetoothDevice[] appareilsAppareilles = (BluetoothDevice[]) setAppareilsApparies.toArray(new BluetoothDevice[setAppareilsApparies.size()]);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int off = spinner.getBaseline(); //distance a la premiere ligne
                //BluetoothDevice btd = appareilsAppareilles[off-109];
                String btd = (String)spinner.getSelectedItem();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("bluetooth",btd);
                BtParseur.sendIp(context);
            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

        for (BluetoothDevice btd : appareilsAppareilles) {
            adapter.add(btd.getName());
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        passer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BluetoothConnexion.this, SynchronisationAdmin.class);
                startActivity(i);
            }
        });
    }

    protected void onResume (){

        super.onResume();

        Set<BluetoothDevice> setAppareilsApparies = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        final BluetoothDevice[] appareilsAppareilles = (BluetoothDevice[]) setAppareilsApparies.toArray(new BluetoothDevice[setAppareilsApparies.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);

        for (BluetoothDevice btd : appareilsAppareilles) {
            adapter.add(btd.getName());
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bluetooth_connexion, menu);
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
