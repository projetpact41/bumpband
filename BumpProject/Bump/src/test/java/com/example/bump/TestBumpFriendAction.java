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

    private BumpFriendAction activity3;
    private ShadowActivity shadowMyActivity3;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Before

    public void setup3() {
        activity3 = new BumpFriendAction();
        activity3.onCreate(null);
        shadowMyActivity3 = Robolectric.shadowOf(activity3);

        BumpFriend bf = new BumpFriend("Bob", InetAddress.getLoopbackAddress());
        shadowMyActivity3.startActivity(new Intent().putExtra("nom", bf.getName()));

    }

    @Test
    public void testInitialisation3(){

        Assert.assertNotNull(activity3);
        Assert.assertNotNull(shadowMyActivity3);

        EditText message = (EditText) activity3.findViewById(R.id.message);
        Button envoyer = (Button) activity3.findViewById(R.id.buttonEnvoi);

        Assert.assertNotNull(message);
        Assert.assertNotNull(envoyer);
    }

    @Test
    public void testEnvoie3() {
        EditText message = (EditText) activity3.findViewById(R.id.message);
        Button envoyer = (Button) activity3.findViewById(R.id.buttonEnvoi);
        message.setText("Salut");
        envoyer.performClick();
        Assert.assertTrue(true);
    }

}
