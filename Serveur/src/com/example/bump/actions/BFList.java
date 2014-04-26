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

package com.example.bump.actions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


public class BFList {

    private File liste;

    public BFList(String emplacementDuFichier) {
        liste = new File(emplacementDuFichier);
    }

    public void ajoutBF(BumpFriend bf){ //Permet d'ajouter un bumpfriend à la liste
        if(this.isBF(bf)) {return;} //On verifie qui'il n'est pas déjà dans la liste
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

    public int index(BumpFriend bf) { //Renvoie l'index d'un bumpfriend dans la liste
        String line_bf = bf.toString();
        int i = -1;
        try {
            FileReader reader= new FileReader(liste);
            BufferedReader readfile=new BufferedReader(reader);
            String line = readfile.readLine();
            int j = 0;
            while(line!=null) { //On parcourt tout le fichier
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

    public Boolean isBF(BumpFriend bf){ //bf est-il un bumpFriend?
        int i = this.index(bf); //Il suffit de regarder son indice dans la liste
        if (i<0) {return false;}
        return true;
    }
    
    public Boolean isBF(String ip) { //ip est-elle l'ip d'un bf?
        BufferedReader readfile = null;
        FileReader reader = null;
        try {
            reader= new FileReader(liste);
            readfile = new BufferedReader(reader);
            String line = readfile.readLine();
            while(line!=null) { //On parcourt la liste des bumpfriends.
                int k = line.indexOf(ip);
                if(k != -1) return true; //On a trouve l'ip
                line = readfile.readLine();
            }
        }
        catch(Exception e){
            System.err.println("Exception catched:");
            e.printStackTrace();
        }
        finally {
        	try {
				readfile.close() ;
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return false;
    }

    public ArrayList<BumpFriend> getBFliste(){ //Retroune la liste des bumpfriends
        ArrayList<BumpFriend> array = new ArrayList<BumpFriend>();
        try {
            FileReader reader= new FileReader(liste);
            BufferedReader readfile=new BufferedReader(reader);
            String line = readfile.readLine();
            while (line!=null) {//On parcourt le fichier en decodant ce que l'on a ecrit
                int i = line.indexOf("µ"); //separateur
                if (i == -1) break;
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

    public void effacerBF(BumpFriend bf){ //Efface un bumpFriend de la liste
        int i = this.index(bf); 
        this.effacerBF(i); //Efface la ligne ou se trouve le bumpFriend
    }
    
    public void effacerBF(int i) { // efface la ieme ligne
        if (i<0) {return;}
        File temp = new File("EMPLACEMENTTEMP.txt"); //On utilise un emplacement temporaire
        try {
            FileReader reader = new FileReader(liste);
            FileWriter writer = new FileWriter(temp);
            BufferedReader breader = new BufferedReader(reader);
            BufferedWriter bwriter = new BufferedWriter(writer);
            int j = 0;
            String line = breader.readLine();
            while(line != null) {
                if (j!=i) {
                    bwriter.write(line+"\n");
                }
                j++;
                line = breader.readLine();
            }
            bwriter.close();
            breader.close();
            writer.close();
            reader.close();
            System.out.println(liste.delete());
            System.out.println(liste.createNewFile());
            reader = new FileReader(temp);
            writer = new FileWriter(liste,true);
            breader = new BufferedReader(reader);
            bwriter = new BufferedWriter(writer);
            line = breader.readLine();
            while(line != null) {
            	System.out.println(line);
                bwriter.write(line+"\n");
                line = breader.readLine();
            }
            bwriter.close();
            breader.close();
            writer.close();
            reader.close();
            temp.delete();
        }
        catch(Exception e){
            System.err.println("Exception catched:");
            e.printStackTrace();
        }
    }

}
