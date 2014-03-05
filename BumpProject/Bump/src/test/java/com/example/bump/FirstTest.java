package com.example.bump;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowNetworkInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class FirstTest {

    private MainActivity activity;

    @Before

    public void setup () {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void testInstantiation() {
        assertNotNull(activity);
        EditText pseudo = (EditText) activity.findViewById(R.id.pseudo);
        Button connexion = (Button) activity.findViewById(R.id.buttonConnexion);
        TextView textView = (TextView) activity.findViewById(R.id.titreConfiguration);
        assertNotNull(pseudo);
        assertNotNull(connexion);
        assertNotNull(textView);
        connexion.performClick();

        AlertDialog sad = ShadowAlertDialog.getLatestAlertDialog();
        assertNotNull(sad);
    }

    @Test
    public void testNetwork1 () {
        EditText pseudo = (EditText) activity.findViewById(R.id.pseudo);
        Button connexion = (Button) activity.findViewById(R.id.buttonConnexion);
        pseudo.setText("bob");
        assertTrue(pseudo.getText().equals("bob"));

        ConnectivityManager cm = (ConnectivityManager) Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        ShadowNetworkInfo ni = Robolectric.shadowOf(cm.getActiveNetworkInfo());

        ni.setAvailableStatus(false);
        ni.setConnectionStatus(true);

        connexion.performClick();

        AlertDialog sad = ShadowAlertDialog.getLatestAlertDialog();
        assertNotNull(sad);
    }

    @Test
    public void testNetwork2 () {
        EditText pseudo = (EditText) activity.findViewById(R.id.pseudo);
        Button connexion = (Button) activity.findViewById(R.id.buttonConnexion);
        pseudo.setText("bob");

        ConnectivityManager cm = (ConnectivityManager) Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        ShadowNetworkInfo ni = Robolectric.shadowOf(cm.getActiveNetworkInfo());

        ni.setAvailableStatus(true);
        ni.setConnectionStatus(false);

        connexion.performClick();

        AlertDialog sad = ShadowAlertDialog.getLatestAlertDialog();
        assertNotNull(sad);
    }

    @Test
    public void testNetwork3 () {
        EditText pseudo = (EditText) activity.findViewById(R.id.pseudo);
        Button connexion = (Button) activity.findViewById(R.id.buttonConnexion);
        pseudo.setText("bob");

        ConnectivityManager cm = (ConnectivityManager) Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        ShadowNetworkInfo ni = Robolectric.shadowOf(cm.getActiveNetworkInfo());

        ni.setAvailableStatus(true);
        ni.setConnectionStatus(true);

        connexion.performClick();

        AlertDialog sad = ShadowAlertDialog.getLatestAlertDialog();
        assertNull(sad);
    }

    @Test
    public void getConnectivityManagerShouldNotBeNull() {
        ConnectivityManager cm = (ConnectivityManager) Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        assertNotNull(cm);
        assertNotNull(cm.getActiveNetworkInfo());
    }
}