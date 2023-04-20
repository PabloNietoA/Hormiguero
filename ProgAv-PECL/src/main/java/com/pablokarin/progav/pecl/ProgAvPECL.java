/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pablokarin.progav.pecl;

import com.pablokarin.progav.part1.hilos.Test;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class ProgAvPECL {

    
    
    public static void main(String[] args) 
    {
        Lock l = new ReentrantLock();
        Condition c = l.newCondition();
        Test thread = new Test(1, l, c, true);
        Test thread2 = new Test (2, l, c, false);
        
        thread.start();
        thread2.start();
    }
}
