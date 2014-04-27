//The MIT License (MIT)
//
//Copyright (c) 2014 Julien ROMERO
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.


package com.example.bump;

import android.content.Context;
import android.util.Log;

import com.example.bump.actions.BumpFriend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.InetAddress;
import java.util.ArrayList;
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

    public Boolean isBF(String ip) {
        int i = -1;
        try {
            FileReader reader= new FileReader(liste);
            BufferedReader readfile = new BufferedReader(reader);
            String line = readfile.readLine();
            while(line!=null) {
                int k = line.indexOf(ip);
                if(k != -1) return true;
                line = readfile.readLine();
            }
            readfile.close() ;
            reader.close();
        }
        catch(Exception e){
            System.err.println("Exception catched:");
            e.printStackTrace();
        }
        return false;
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