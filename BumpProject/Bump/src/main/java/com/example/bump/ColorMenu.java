package com.example.bump;

import android.content.Context;
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

import com.example.bump.actions.Color;
import com.example.bump.bluetooth.BtParseur;

public class ColorMenu extends ActionBarActivity {

    private Button send;
    private String TAG = "ColorMenu";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_menu);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Button button_red = (Button) findViewById(R.id.button_red);
        Button button_blue = (Button) findViewById(R.id.button_blue);
        Button button_green = (Button) findViewById(R.id.button_green);
        Button button_clign = (Button) findViewById(R.id.button_clign);

        //Ecoute du bouton
        button_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtParseur.sendColor(new Color((byte) 255,(byte) 0,(byte) 0), context);
            }
        });

        button_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtParseur.sendColor(new Color((byte) 0,(byte) 255,(byte) 0), context);
            }
        });

        button_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtParseur.sendColor(new Color((byte) 0,(byte) 0,(byte) 255), context);
            }
        });

        button_clign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtParseur.clignote((byte) 4,(byte) 10, context);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.color_menu, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_color_menu, container, false);
            return rootView;
        }
    }

}
