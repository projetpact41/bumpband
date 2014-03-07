package com.example.bump;

import android.widget.Button;
import android.widget.EditText;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by jjuulliieenn on 07/03/14.
 */

@RunWith(RobolectricTestRunner.class)
public class TestSimuBump {

    private SimuBump activity;

    @Before

    public void setup () {
        activity = Robolectric.buildActivity(SimuBump.class).create().get();
    }

    @Test

    public void testInitialisation() {
        EditText iptext = (EditText) activity.findViewById(R.id.iptext);
        EditText monSC = (EditText) activity.findViewById(R.id.monSC);
        EditText tonSC = (EditText) activity.findViewById(R.id.tonSC);
        Button bouton = (Button) activity.findViewById(R.id.buttonBump);

        Assert.assertNotNull(iptext);
        org.junit.Assert.assertNotNull(monSC);
        org.junit.Assert.assertNotNull(tonSC);
        org.junit.Assert.assertNotNull(bouton);
    }

}
