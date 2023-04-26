/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1.hilos;

import java.util.ArrayList;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Test extends Thread{
    private static ArrayList<Integer> a = new ArrayList();
    private Lock l;
    private Condition c;
    private boolean cond;
    private int id;

    public Test(int i, Lock l, Condition c, boolean cond) {
        this.id = i;
        this.l = l;
        this.c = c;
        this.cond = cond;
    }
    
    
    public void run()
    {
        try
        {
            System.out.println("Inicio " + id);
            l.lock();
            while(cond)
            {
                System.out.println("Antes " + id);
                cond = false;
                c.await();
                System.out.println("Despues " + id);
            }
            Thread.sleep(3000);
            c.signalAll();
            System.out.println("Final " + id);
        }
        catch(InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
        finally
        {
            l.unlock();
        }
    }

    public static ArrayList<Integer> getA() {
        return a;
    }
    
}
