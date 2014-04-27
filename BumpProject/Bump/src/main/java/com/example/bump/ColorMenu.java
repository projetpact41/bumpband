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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.bump.actions.Color;
import com.example.bump.bluetooth.BtParseur;

public class ColorMenu extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener{

    private Button send,clignote,vibre;
    private String TAG = "ColorMenu";
    private Context context = this;

    SeekBar rsb, gsb, bsb;
    int red, green, blue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_menu);

        rsb = (SeekBar)findViewById(R.id.seekBarRed);
        gsb = (SeekBar)findViewById(R.id.seekBarGreen);
        bsb = (SeekBar)findViewById(R.id.seekBarBlue);
        clignote = (Button) findViewById(R.id.clignote);
        vibre = (Button) findViewById(R.id.vibre);

        rsb.setOnSeekBarChangeListener(this);
        gsb.setOnSeekBarChangeListener(this);
        bsb.setOnSeekBarChangeListener(this);

        clignote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtParseur.clignote((byte) 200, (byte) 15,context);
            }
        });

        vibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtParseur.vibre((byte) 2,context);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }



        /*Button button_red = (Button) findViewById(R.id.button_red);
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
                BtParseur.clignote((byte) 1,(byte) 10, context);
            }
        });*/

    }

    private void updateLED(){
        red = rsb.getProgress();
        green = gsb.getProgress();
        blue = bsb.getProgress();
        BtParseur.sendColor(new Color((byte) red,(byte) green,(byte) blue), context);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updateLED();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
