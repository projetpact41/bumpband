package graphique;

import java.util.Hashtable;
import java.util.Random;

import com.example.bump.actions.BumpFriend;
public class HashList {
private static Hashtable<BumpFriend,Integer> htab;
 
public static int add(BumpFriend ami) {
 Random rand = new Random();
 Integer i= 0;
 do {i=rand.nextInt();}
 while (htab.containsValue(i));
 htab.put(ami, i);
 return i;
 }
 
public static void remove(BumpFriend ami) {
 htab.remove(ami);
 }
}