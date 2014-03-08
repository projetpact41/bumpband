package com.example.bump;

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
    private MainActivity activity2;

    @Before

    public void setup2() {
        activity2 = Robolectric.buildActivity(MainActivity.class).create().get();
        bfList = new BFList("listeBF.txt",activity2);
    }

    @Test

    public void test2(){
        Assert.assertNotNull(bfList);
        ShadowContext shadowContext = (ShadowContext) Robolectric.shadowOf(activity2);

        File file = new File(activity2.getFilesDir(),"listeBF.txt");
        org.junit.Assert.assertTrue(file.exists());


    }

}
