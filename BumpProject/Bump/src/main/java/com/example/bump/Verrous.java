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

import java.util.concurrent.Semaphore;

/**
 * Created by jjuulliieenn on 30/03/14.
 */
public class Verrous {

    //public static Lock enCours = new ReentrantLock();
    //public static Lock monSC = new ReentrantLock();
    //public static Lock tonSC = new ReentrantLock();
    //public static Semaphore sync1 = new Semaphore(0);
    //public static Semaphore sync2 = new Semaphore(0);
    public static Semaphore sync3 = new Semaphore(0);
    public static Semaphore sync4 = new Semaphore(0);
    public static boolean sms = false;
    public static boolean phone = false;

}
