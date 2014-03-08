package com.example.bump;

import android.widget.Button;

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

public class TestColorMenu {

    private ColorMenu activity4;

    @Before

    public void setup4 () {
        activity4 = Robolectric.buildActivity(ColorMenu.class).create().get();
    }

    @Test

    public void testInitialisation4() {

        Assert.assertNotNull(activity4);
        Button button_red = (Button) activity4.findViewById(R.id.button_red);
        Button button_blue = (Button) activity4.findViewById(R.id.button_blue);
        Button button_green = (Button) activity4.findViewById(R.id.button_green);
        Button button_clign = (Button) activity4.findViewById(R.id.button_clign);

        Assert.assertNotNull(button_blue);
        Assert.assertNotNull(button_red);
        Assert.assertNotNull(button_green);
        Assert.assertNotNull(button_clign);

    }
}
