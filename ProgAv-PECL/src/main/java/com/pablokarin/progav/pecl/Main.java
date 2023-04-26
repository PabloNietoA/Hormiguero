/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pablokarin.progav.pecl;

import com.pablokarin.progav.part1.hilos.Test;
import java.util.Random;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Main {

    
    
    public static void main(String[] args) 
    {
        Test.getA().add(7);
        System.out.println(Test.getA().get(0));
        Lock l = new ReentrantLock();
        Condition c = l.newCondition();
        Test thread = new Test(1, l, c, true);
        Test thread2 = new Test (2, l, c, false);
        
        thread.start();
        thread2.start();
        //creador de hormigas
        for (int i = 0; i < 10000; i++)
        {
            try
            {
                Thread.sleep(new Random().nextInt(2700)+800);
            }
            catch (InterruptedException IE)
            {
                System.out.println(IE.getMessage());
            }
            if ((i%5)+3==0)
            {
                //crear Soldado 
            }
            if ((i%5)+4==0)
            {
                //crear cría
            }
            else
            {
                //crear obrera
            }
        }
    }
}
