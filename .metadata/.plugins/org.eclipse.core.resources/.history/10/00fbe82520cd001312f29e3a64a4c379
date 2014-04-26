package admin;

import com.example.bump.actions.BumpFriend;

import java.util.Hashtable;


/**
 * Created by Arturo on 07/04/2014.
 */
public class Banque {

        private static Hashtable<String,Byte> htab;

        public static void create(String ip) {
            htab.put(ip,(byte)20); //On attribue 20 credits au debut
        }

        public static void remove(String ip) {
            htab.remove(ip);
        }
        
        public static byte getMoney (String ip) {
        	return htab.get(ip);
        }
        
        public static void addMoney (String ip, byte ajout) {
        	byte credit = getMoney(ip);
        	htab.put(ip, (byte) (credit+ajout));
        }
        
        public static void removeMoney (String ip, int retrait) {
        	byte credit = getMoney(ip);
        	htab.put(ip,(byte)(credit-retrait));
        }
    }
