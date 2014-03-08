package com.example.bump;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Color;
import com.example.bump.actions.Connexion;
import com.example.bump.actions.ErreurTransmission;
import com.example.bump.actions.Message;
import com.example.bump.actions.Transmission;
import com.example.bump.serveur.Parseur;
import com.example.bump.serveur.Serveur;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowContext;
import org.robolectric.shadows.ShadowNetworkInfo;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

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

        ShadowContext shadowContext = (ShadowContext) Robolectric.shadowOf(activity.getApplicationContext());

        File file = new File(shadowContext.getFilesDir(),"monSC.txt");
        org.junit.Assert.assertTrue(file.exists());

        file = new File(shadowContext.getFilesDir(),"fichePerso.txt");
        org.junit.Assert.assertTrue(file.exists());

        file = new File(shadowContext.getFilesDir(),"tonSC.txt");
        org.junit.Assert.assertTrue(file.exists());

        file = new File(shadowContext.getFilesDir(),"enCours.txt");
        org.junit.Assert.assertTrue(file.exists());

        file = new File(shadowContext.getFilesDir(),"BFList.txt");
        org.junit.Assert.assertTrue(file.exists());

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.peekNextStartedService();
        assertEquals(Serveur.class.getCanonicalName(), intent.getComponent().getClassName());

        intent = shadowActivity.peekNextStartedActivity();
        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(MenuPrincipal.class.getCanonicalName(), intent.getComponent().getClassName());

    }

    @Test
    public void getConnectivityManagerShouldNotBeNull() {
        ConnectivityManager cm = (ConnectivityManager) Robolectric.application.getSystemService(Context.CONNECTIVITY_SERVICE);
        assertNotNull(cm);
        assertNotNull(cm.getActiveNetworkInfo());
    }

    @Test
    public void testIP(){
        assertNotNull(activity.getIpAddr());
    }


    /*
     *   TestAPropos
     *
     */

    private APropos activity1;


    @Before

    public void setup1 () {
        activity1 = Robolectric.buildActivity(APropos.class).create().get();
    }

    @Test

    public void testInitialisation1(){
        Assert.assertNotNull(activity1);
    }


    /*
    *           TestBFList
    *
     */


    private BFList bfList;
    private MainActivity activity2;

    @Before

    public void setup2() {
        activity2 = Robolectric.buildActivity(MainActivity.class).create().get();
        bfList = new BFList("listeBF.txt",activity2.getApplicationContext());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test

    public void test2(){
        org.junit.Assert.assertNotNull(bfList);
        ShadowContext shadowContext = (ShadowContext) Robolectric.shadowOf(activity2.getApplicationContext());

        try {
            bfList.ajoutBF(new BumpFriend("Bob", InetAddress.getByName("127.0.0.1")));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        File file = new File(shadowContext.getFilesDir(),"listeBF.txt");
        Assert.assertTrue(file.exists());

    }


/*******
 *
 *          TestBumpFriendAction
*/


private BumpFriendAction activity3;
private ShadowActivity shadowMyActivity3;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Before

    public void setup3() {
        activity3 = Robolectric.buildActivity(BumpFriendAction.class).create().get();
        shadowMyActivity3 = Robolectric.shadowOf(activity3);



    }

    @Test
    public void testInitialisation3(){
        BumpFriendAction activityTest = Robolectric.buildActivity(BumpFriendAction.class).withIntent(new Intent().putExtra("nom", "Bob")).create().get();
        shadowMyActivity3.startActivity(new Intent().putExtra("nom", "Bob"));
        Assert.assertNotNull(activity3);
        Assert.assertNotNull(shadowMyActivity3);

        EditText message = (EditText) activityTest.findViewById(R.id.message);
        Button envoyer = (Button) activityTest.findViewById(R.id.buttonEnvoi);

        Assert.assertNotNull(message);
        Assert.assertNotNull(envoyer);
    }

    @Test
    public void testEnvoie3() {
        BumpFriendAction activityTest = Robolectric.buildActivity(BumpFriendAction.class).withIntent(new Intent().putExtra("nom", "Bob")).create().get();
        shadowMyActivity3.startActivity(new Intent().putExtra("nom", "Bob"));
        EditText message = (EditText) activityTest.findViewById(R.id.message);
        Button envoyer = (Button) activityTest.findViewById(R.id.buttonEnvoi);
        message.setText("Salut");
        envoyer.performClick();
    }

/****************
 *
 *              TestColorMenu
 *
 */

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

    /*************
     *
     *      TestMenuPrincipal
     *
     */

    private MenuPrincipal activity5;

    @Before

    public void setup5 () {
        activity5 = Robolectric.buildActivity(MenuPrincipal.class).create().get();
    }

    @Test
    public void testInitialisation5() {
        ListView listView = (ListView) activity5.findViewById(R.id.list);
        Assert.assertNotNull(listView);
        ListAdapter adapter = listView.getAdapter();
        Assert.assertNotNull(adapter);

        listView.performItemClick(
                listView.getAdapter().getView(0, null, null),
                0,
                listView.getAdapter().getItemId(0));

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity5);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(BumpFriendListe.class.getCanonicalName(), intent.getComponent().getClassName());

    }

    @Test

    public void testListe25() {
        ListView listView = (ListView) activity5.findViewById(R.id.list);
        Assert.assertNotNull(listView);
        ListAdapter adapter = listView.getAdapter();
        Assert.assertNotNull(adapter);

        listView.performItemClick(
                listView.getAdapter().getView(1, null, null),
                1,
                listView.getAdapter().getItemId(1));

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity5);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(ColorMenu.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test

    public void testListe35(){
        ListView listView = (ListView) activity5.findViewById(R.id.list);
        Assert.assertNotNull(listView);
        ListAdapter adapter = listView.getAdapter();
        Assert.assertNotNull(adapter);

        listView.performItemClick(
                listView.getAdapter().getView(2, null, null),
                2,
                listView.getAdapter().getItemId(2));

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity5);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(SimuBump.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test

    public void testListe45(){
        ListView listView = (ListView) activity5.findViewById(R.id.list);
        Assert.assertNotNull(listView);
        ListAdapter adapter = listView.getAdapter();
        Assert.assertNotNull(adapter);

        listView.performItemClick(
                listView.getAdapter().getView(3, null, null),
                3,
                listView.getAdapter().getItemId(3));

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity5);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(APropos.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    /***********
     *
     *          TestMessageActivity
     *
     */

    private MessageActivity activity6;
    private ShadowActivity shadowActivity6;

    @Before

    public void setup6 () {


    }

    @Test
    public void testInitialisation(){
        activity6 = Robolectric.buildActivity(MessageActivity.class).withIntent(new Intent().putExtra("nom", "Bob").putExtra("message","Salut")).create().start().get();
        try {
            BumpFriend bf = new BumpFriend("Bob", InetAddress.getByName("127.0.0.1"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        TextView expediteur = (TextView) activity6.findViewById(R.id.expedi);
        TextView message = (TextView) activity6.findViewById(R.id.messagerie);
        Assert.assertNotNull(expediteur);
        Assert.assertNotNull(message);
        Assert.assertTrue(expediteur.getText().equals("Bob"));
        Assert.assertTrue(message.getText().equals("Salut"));
    }

    /***************
     *
     *      TestParseur
     *
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test
    public void testBF() {
        BumpFriend bf = null;
        byte[] bytes = {0,'B','o','b','|','1','2','7','.','0','.','0','.','1'};
        try {
            bf = new BumpFriend("Bob", InetAddress.getByName("127.0.0.1"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(Parseur.parser(bytes).toString().equals(bf.toString()));
    }

    @Test
    public void testCouleur() {
        byte[] bytes = {1,(byte)156,(byte)76,(byte)222};
        Color color = new Color((byte)156,(byte)76,(byte)222);
        org.junit.Assert.assertTrue(Arrays.equals(Parseur.parser(bytes).toBytes(),color.toBytes()));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test
    public void testConnexion() {
        Connexion connexion = null;
        byte[] bytes = {2,'1','2','7','.','0','.','0','.','1','|',(byte)10,(byte)199};
        try {
            connexion = new Connexion((byte)10,(byte)199,InetAddress.getByName("127.0.0.1"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        org.junit.Assert.assertTrue(Arrays.equals(Parseur.parser(bytes).toBytes(), connexion.toBytes()));
    }

    @Test
    public void testMessage() {
        byte[] bytes = {3,'S','a','l','u','t','|','B','o','b'};
        Message message = new Message("Salut","Bob");
        org.junit.Assert.assertTrue(Arrays.equals(Parseur.parser(bytes).toBytes(), message.toBytes()));
    }

    @Test
    public void testTransmission() {
        byte[] bytes = {4,1};
        Transmission transmission = new Transmission(true);
        org.junit.Assert.assertTrue(Arrays.equals(Parseur.parser(bytes).toBytes(), transmission.toBytes()));

        byte[] bytes1 = {4,0,'L','O','N','G','U','E','U','R'};
        transmission = new Transmission(ErreurTransmission.LONGUEUR);
        org.junit.Assert.assertTrue(Arrays.equals(Parseur.parser(bytes1).toBytes(), transmission.toBytes()));
    }

    /**********
     *
     *      TestSimuBump
     *
     */

    private SimuBump activity7;

    @Before

    public void setup7 () {
        activity7 = Robolectric.buildActivity(SimuBump.class).create().get();
    }

    @Test

    public void testInitialisation7() {
        Assert.assertNotNull(activity7);

        EditText iptext = (EditText) activity7.findViewById(R.id.iptext);
        EditText monSC = (EditText) activity7.findViewById(R.id.monSC);
        EditText tonSC = (EditText) activity7.findViewById(R.id.tonSC);
        Button bouton = (Button) activity7.findViewById(R.id.buttonBump);

        Assert.assertNotNull(iptext);
        org.junit.Assert.assertNotNull(monSC);
        org.junit.Assert.assertNotNull(tonSC);
        org.junit.Assert.assertNotNull(bouton);
    }
}