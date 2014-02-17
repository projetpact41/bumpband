package com.example.bump.actions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.InetAddress;
import java.util.ArrayList;


public class BFList {

    private File liste;
    private final String TAG = "BFLIST";

    public BFList(String emplacementDuFichier) {
        liste = new File(emplacementDuFichier);
    }

    public void ajoutBF(BumpFriend bf){
        if(this.isBF(bf)) {return;}
        try {
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
                int i = line.indexOf("�");
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