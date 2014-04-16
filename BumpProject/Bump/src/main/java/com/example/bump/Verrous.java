package com.example.bump;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jjuulliieenn on 30/03/14.
 */
public class Verrous {

    public static Lock enCours = new ReentrantLock();
    public static Lock monSC = new ReentrantLock();
    public static Lock tonSC = new ReentrantLock();
    public static Semaphore sync1 = new Semaphore(0);
    public static Semaphore sync2 = new Semaphore(0);
    public static Semaphore sync3 = new Semaphore(0);
    public static Semaphore sync4 = new Semaphore(0);
    public static boolean sms = false;
    public static boolean phone = false;

}
