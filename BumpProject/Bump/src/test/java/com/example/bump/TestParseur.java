package com.example.bump;

import android.annotation.TargetApi;
import android.os.Build;

import com.example.bump.actions.BumpFriend;
import com.example.bump.actions.Color;
import com.example.bump.actions.Connexion;
import com.example.bump.actions.ErreurTransmission;
import com.example.bump.actions.Message;
import com.example.bump.actions.Transmission;
import com.example.bump.serveur.Parseur;

import junit.framework.Assert;

import org.junit.Test;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * Created by jjuulliieenn on 07/03/14.
 */
public class TestParseur {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test
    public void testBF() {
        byte[] bytes = {0,'B','o','b','|','1','2','7','.','0','.','0','.','1'};
        BumpFriend bf = new BumpFriend("Bob", InetAddress.getLoopbackAddress());
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
        byte[] bytes = {2,'1','2','7','.','0','.','0','.','1','|',(byte)10,(byte)199};
        Connexion connexion = new Connexion((byte)10,(byte)199,InetAddress.getLoopbackAddress());
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
}
