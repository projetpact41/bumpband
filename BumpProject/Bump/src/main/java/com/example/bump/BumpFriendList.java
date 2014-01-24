package com.example.bump;

import com.example.bump.actions.BumpFriend;

import java.util.ArrayList;

/**
 * Created by jjuulliieenn on 20/01/14.
 */
public class BumpFriendList {

    static ArrayList<BumpFriend> l =new ArrayList<BumpFriend>();

    public static void add(BumpFriend bf) {
        l.add(bf);
    }
}
