/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pablokarin.progav.pecl;

import com.pablokarin.progav.part1.hilos.*;
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
        
        int soldados = 0;
        int crias = 0;
        int obreras = 0;
        
        
        thread.start();
        thread2.start();
        //creador de hormigas
        for (int i = 0; i < 10000; i++)
        {
            try
            {
                Thread.sleep(new Random().nextInt(2701)+800);
            }
            catch (InterruptedException IE)
            {
                System.out.println(IE.getMessage());
            }
            if ((i%5)==0)
            {
                new Thread(new Soldado(soldados)).start();
                soldados++;
            }
            if (((i%5)-1)==0)
            {
                new Thread(new Cria(crias)).start();
                crias++;
            }
            else
            {
                new Thread(new Obrera(obreras)).start();
                obreras++;
            }
        }
    }
}
