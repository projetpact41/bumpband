package com.example.bump;

import android.content.Intent;
import android.widget.TextView;

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

public class TestMessageActivity {

    private MessageActivity activity;
    private ShadowActivity shadowActivity;

    @Before

    public void setup () {
        activity = new MessageActivity();
        activity.onCreate(null);
        shadowActivity = Robolectric.shadowOf(activity);

        BumpFriend bf = new BumpFriend("Bob", InetAddress.getLoopbackAddress());
        shadowActivity.startActivity(new Intent().putExtra("nom", "Bob").putExtra("message","Salut"));
    }

    @Test
    public void testInitialisation(){
        TextView expediteur = (TextView) activity.findViewById(R.id.expedi);
        TextView message = (TextView) activity.findViewById(R.id.messagerie);
        Assert.assertNotNull(expediteur);
        Assert.assertNotNull(message);
        Assert.assertTrue(expediteur.getText().equals("Bob"));
        Assert.assertTrue(message.getText().equals("Salut"));
    }

}
