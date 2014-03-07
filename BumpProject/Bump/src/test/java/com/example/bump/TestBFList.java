package com.example.bump;

import android.content.Context;
import android.os.Environment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowContext;

import java.io.File;

/**
 * Created by jjuulliieenn on 07/03/14.
 */

@RunWith(RobolectricTestRunner.class)

public class TestBFList {

    private BFList bfList;
    private MainActivity activity;

    @Before

    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
        bfList = new BFList("listeBF.txt",activity.getApplicationContext());
    }

    @Test

    public void test(){
        Assert.assertNotNull(bfList);
        ShadowContext shadowContext = (ShadowContext) Robolectric.shadowOf(activity.getApplicationContext());

        File file = new File(shadowContext.getFilesDir(),"listeBF.txt");
        org.junit.Assert.assertTrue(file.exists());


    }

}
