package com.example.bump;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jjuulliieenn on 30/03/14.
 */
public class Verrous {

    public static Lock enCours = new ReentrantLock();
    public static Lock monSC = new ReentrantLock();
    public static Lock tonSC = new ReentrantLock();

}
