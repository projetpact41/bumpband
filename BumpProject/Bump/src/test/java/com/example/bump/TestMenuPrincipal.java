package com.example.bump;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.bump.serveur.Serveur;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;

/**
 * Created by jjuulliieenn on 07/03/14.
 */
@RunWith(RobolectricTestRunner.class)

public class TestMenuPrincipal {

    private MenuPrincipal activity;

    @Before

    public void setup () {
        activity = Robolectric.buildActivity(MenuPrincipal.class).create().get();
    }

    @Test
    public void testInitialisation() {
        ListView listView = (ListView) activity.findViewById(R.id.list);
        Assert.assertNotNull(listView);
        ListAdapter adapter = listView.getAdapter();
        Assert.assertNotNull(adapter);

        listView.performItemClick(
                listView.getAdapter().getView(1, null, null),
                1,
                listView.getAdapter().getItemId(1));

        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(BumpFriendListe.class.getCanonicalName(), intent.getComponent().getClassName());

        listView.performItemClick(
                listView.getAdapter().getView(2, null, null),
                2,
                listView.getAdapter().getItemId(2));

        shadowActivity = Robolectric.shadowOf(activity);
        intent = shadowActivity.peekNextStartedActivity();
        assertEquals(ColorMenu.class.getCanonicalName(), intent.getComponent().getClassName());

        listView.performItemClick(
                listView.getAdapter().getView(3, null, null),
                3,
                listView.getAdapter().getItemId(3));

        shadowActivity = Robolectric.shadowOf(activity);
        intent = shadowActivity.peekNextStartedActivity();
        assertEquals(SimuBump.class.getCanonicalName(), intent.getComponent().getClassName());

        listView.performItemClick(
                listView.getAdapter().getView(4, null, null),
                4,
                listView.getAdapter().getItemId(4));

        shadowActivity = Robolectric.shadowOf(activity);
        intent = shadowActivity.peekNextStartedActivity();
        assertEquals(APropos.class.getCanonicalName(), intent.getComponent().getClassName());

    }
}
