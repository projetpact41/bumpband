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

    private MessageActivity activity6;
    private ShadowActivity shadowActivity6;

    @Before

    public void setup6 () {
        activity6 = new MessageActivity();
        activity6.onCreate(null);
        shadowActivity6 = Robolectric.shadowOf(activity6);

    }

    @Test
    public void testInitialisation(){
        BumpFriend bf = new BumpFriend("Bob", InetAddress.getLoopbackAddress());
        shadowActivity6.startActivity(new Intent().putExtra("nom", "Bob").putExtra("message","Salut"));
        TextView expediteur = (TextView) activity6.findViewById(R.id.expedi);
        TextView message = (TextView) activity6.findViewById(R.id.messagerie);
        Assert.assertNotNull(expediteur);
        Assert.assertNotNull(message);
        Assert.assertTrue(expediteur.getText().equals("Bob"));
        Assert.assertTrue(message.getText().equals("Salut"));
    }

}
