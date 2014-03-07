package com.example.bump;

import android.widget.Button;

import com.example.bump.actions.Color;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

/**
 * Created by jjuulliieenn on 07/03/14.
 */
public class TestColorMenu {

    private ColorMenu activity;

    @Before

    public void setup () {
        activity = Robolectric.buildActivity(ColorMenu.class).create().get();
    }

    @Test

    public void testInitialisation() {

        Assert.assertNotNull(activity);
        Button button_red = (Button) activity.findViewById(R.id.button_red);
        Button button_blue = (Button) activity.findViewById(R.id.button_blue);
        Button button_green = (Button) activity.findViewById(R.id.button_green);
        Button button_clign = (Button) activity.findViewById(R.id.button_clign);

        Assert.assertNotNull(button_blue);
        Assert.assertNotNull(button_red);
        Assert.assertNotNull(button_green);
        Assert.assertNotNull(button_clign);

    }
}
