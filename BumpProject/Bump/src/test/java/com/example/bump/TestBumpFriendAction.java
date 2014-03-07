package com.example.bump;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import com.example.bump.actions.BumpFriend;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import java.net.InetAddress;

/**
 * Created by jjuulliieenn on 07/03/14.
 */
@RunWith(RobolectricTestRunner.class)


public class TestBumpFriendAction {

    private BumpFriendAction activity;
    private ShadowActivity shadowMyActivity;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Before

    public void setup() {
        activity = new BumpFriendAction();
        activity.onCreate(null);
        shadowMyActivity = Robolectric.shadowOf(activity);

        BumpFriend bf = new BumpFriend("Bob", InetAddress.getLoopbackAddress());
        shadowMyActivity.startActivity(new Intent().putExtra("nom", bf.getName()));

    }

    @Test
    public void testInitialisation(){

        Assert.assertNotNull(activity);
        Assert.assertNotNull(shadowMyActivity);

        EditText message = (EditText) activity.findViewById(R.id.message);
        Button envoyer = (Button) activity.findViewById(R.id.buttonEnvoi);

        Assert.assertNotNull(message);
        Assert.assertNotNull(envoyer);
    }

    @Test
    public void testEnvoie() {
        EditText message = (EditText) activity.findViewById(R.id.message);
        Button envoyer = (Button) activity.findViewById(R.id.buttonEnvoi);
        message.setText("Salut");
        envoyer.performClick();
        Assert.assertTrue(true);
    }

}
