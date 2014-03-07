package com.example.bump;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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

import com.example.bump.actions.Connexion;
import com.example.bump.client.Destinataire;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

public class SimuBump extends Activity {

    private final String TAG = "SimuBump";
    private EditText iptext;
    private EditText monSC;
    private EditText tonSC;
    private Button bouton;
    private int PORT = 4444;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "SimuBump creeé");
        setContentView(R.layout.activity_simubump);

        iptext = (EditText) findViewById(R.id.iptext);
        monSC = (EditText) findViewById(R.id.monSC);
        tonSC = (EditText) findViewById(R.id.tonSC);
        bouton = (Button) findViewById(R.id.buttonBump);

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoieBF();
            }
        });


    }

    private void envoieBF() {
        Log.i(TAG,"Debut protocole");
        if(iptext.getText()==null || monSC.getText()==null || tonSC.getText()==null) return;

        DataOutputStream dos = null;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(this.getFilesDir(),"enCours.txt")
                            )
                    )
            );
            oos.writeObject(InetAddress.getByName(iptext.getText().toString()));
            oos.flush();
            dos = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File (this.getFilesDir(),"monSC.txt")
                            )
                    )
            );
            dos.writeInt(Integer.parseInt(monSC.getText().toString()));
            dos.flush();
            dos.close();

            dos = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File (this.getFilesDir(),"tonSC.txt")
                            )
                    )
            );
            dos.writeInt(Integer.parseInt(tonSC.getText().toString()));
            dos.flush();

            Destinataire destinataire = new Destinataire(InetAddress.getByName(iptext.getText().toString()),PORT);
            destinataire.envoieObjet(new Connexion(Byte.parseByte(monSC.getText().toString()),Byte.parseByte(tonSC.getText().toString()),InetAddress.getByName(getIpAddr())),SimuBump.this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.i(TAG,"Sans doute un flux null");
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simu_bump, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_simubump, container, false);
            return rootView;
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
