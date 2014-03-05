package com.example.bump;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.net.InetAddress;
import java.util.*;
import com.example.bump.actions.BumpFriend;
/**
 * Created by Félix on 10/02/14.
 */
public class BFList {

    private File liste;
    private final String TAG = "BFLIST";

    public BFList(String emplacementDuFichier,Context context) {
        liste = new File(context.getFilesDir(),emplacementDuFichier);
        Log.i(TAG, "Création BFLIST");
        //this.initialiser();
    }

    public void ajoutBF(BumpFriend bf){
        //this.initialiser();
        if(this.isBF(bf)) {return;}
        try {
            Log.i(TAG,"Ajout !");
            FileWriter writer=new FileWriter(liste,true);
            BufferedWriter writefile=new BufferedWriter(writer);
            String line=bf.toString();
            writefile.write(line);
            writefile.newLine();
            writefile.close();
            writer.close() ;
        }
        catch(Exception e){
            System.err.println("Exception catched:");
            e.printStackTrace();
        }
    }

    private int index(BumpFriend bf) {
        String line_bf = bf.toString();
        int i = -1;
        try {
            FileReader reader= new FileReader(liste);
            BufferedReader readfile=new BufferedReader(reader);
            String line = readfile.readLine();
            int j = 0;
            while(line!=null) {
                if(line.equals(line_bf)) {
                    i=j;
                }
                j++;
                line = readfile.readLine();
            }
            readfile.close() ;
            reader.close();
        }
        catch(Exception e){
            System.err.println("Exception catched:");
            e.printStackTrace();
        }
        return i;
    }

    public Boolean isBF(BumpFriend bf){
        int i = this.index(bf);
        if (i<0) {return false;}
        return true;
    }

    public ArrayList<BumpFriend> getBFliste(){
        ArrayList<BumpFriend> array = new ArrayList<BumpFriend>();
        try {
            FileReader reader= new FileReader(liste);
            BufferedReader readfile=new BufferedReader(reader);
            String line = readfile.readLine();
            while (line!=null) {
                int i = line.indexOf("µ");
                String name = line.substring(0,i);
                InetAddress adresse = InetAddress.getByName(line.substring(i+1));
                BumpFriend bf = new BumpFriend(name,adresse);
                array.add(bf);
                line = readfile.readLine();
            }
            readfile.close() ;
            reader.close();
        }
        catch(Exception e){
            System.err.println("Exception catched:");
            e.printStackTrace();
        }
        return array;
    }

    public void effacerBF(BumpFriend bf){
        int i = this.index(bf);
        if (i<0) {return;}
        File temp = new File("EMPLACEMENTTEMP.txt");
        try {
            FileReader reader = new FileReader(liste);
            FileWriter writer = new FileWriter(temp,true);
            BufferedReader breader = new BufferedReader(reader);
            BufferedWriter bwriter = new BufferedWriter(writer);
            int j = 0;
            String line = breader.readLine();
            while(line != null) {
                if (j!=i) {
                    bwriter.write(line);
                }
                j++;
                line = breader.readLine();
            }
            temp.renameTo(liste);
            this.liste = temp;
            bwriter.close();
            breader.close();
            writer.close();
            reader.close();
        }
        catch(Exception e){
            System.err.println("Exception catched:");
            e.printStackTrace();
        }
    }

    public void initialiser(){
        liste.delete();
        try {
            liste.createNewFile();
        }
        catch (Exception e) {
            System.err.println("Exception catched:");
            e.printStackTrace();
        }
    }


}