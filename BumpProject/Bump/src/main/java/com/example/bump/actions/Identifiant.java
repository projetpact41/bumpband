package com.example.bump.actions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by jjuulliieenn on 03/03/14.
 */
public class Identifiant implements Transmissible{

    private int id;
    public Identifiant(int id) {
        this.id=id;

    }

    public Transmissible execute(Context context)
    {
        //@TODO Faire la verification d'administrateur
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ID",id);
        editor.commit();


        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(context.getFilesDir(), "fichePerso.txt")
                            )
                    )
            );

            BumpFriend moi = (BumpFriend) ois.readObject();

            moi.setId(id);

            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(context.getFilesDir(), "fichePerso.txt")
                            )
                    )
            );
            oos.writeObject(moi);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Transmission(true);
    }
    @Override
    public byte[] toBytes() {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.put((byte) 5);
        b.putInt(id);
        byte[] bytes = b.array();
        return bytes;
    }
}
