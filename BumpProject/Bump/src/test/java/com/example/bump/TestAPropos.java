package com.example.bump;

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

public class TestAPropos {

    private APropos activity;


    @Before

    public void setup () {
        activity = Robolectric.buildActivity(APropos.class).create().get();
    }

    @Test

    public void testInitialisation(){
        Assert.assertNotNull(activity);
    }
}
