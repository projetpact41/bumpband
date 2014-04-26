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

package admin;

import java.util.Hashtable;


/**
 * Created by Arturo on 07/04/2014.
 */

public class Banque {

        private static Hashtable<String,Byte> htab; //Contient les clients avec leur solde associe

        public static void create(String ip) { // cree un client
            htab.put(ip,(byte)20); //On attribue 20 credits au debut
        }

        public static void remove(String ip) { // retire un client
            htab.remove(ip);
        }
        
        public static byte getMoney (String ip) { //obtient le solde du client
        	return htab.get(ip);
        }
        
        public static void addMoney (String ip, byte ajout) { // Ajoute de l'argent au client
        	byte credit = getMoney(ip);
        	htab.put(ip, (byte) (credit+ajout));
        }
        
        public static int removeMoney (String ip, int retrait) { //Enleve de l'argent au client 
        	byte credit = getMoney(ip);
        	if (credit-retrait < 0) return -1; //On verifie s'il y a assez d'argent
        	htab.put(ip,(byte)(credit-retrait));
        	return 0;
        }
    }
