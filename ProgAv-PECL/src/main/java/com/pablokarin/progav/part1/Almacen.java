/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Almacen 
{
    private static int stock = 0;
    private static final Semaphore aforo = new Semaphore(10, true);
    private static final Lock control = new ReentrantLock();
    private static Condition vacio = control.newCondition();
    
    public static synchronized void incStock(int inc)
    {
        try 
        {
            aforo.acquire();
            Thread.sleep((new Random().nextInt(2) + 2)*1000);
            control.lock();
            stock += inc;
            //puede haber problemas si llegan 10 recolectores (que hacer?)
            vacio.signalAll();
        }
        catch(InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
        finally 
        {
            control.unlock();
            aforo.release();
        }
    }
    public static synchronized void decStock(int dec)
    {
        try
        {
            aforo.acquire();
            while (stock < dec)
            {
                aforo.release();
                //sigue desde el await cuando hace el signal
                vacio.await();
                
                aforo.acquire();
            }
            control.lock();
            Thread.sleep((new Random().nextInt(1) + 1) * 1000);
            stock -= dec;
            
        }
        catch (InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
        finally 
        {
            control.unlock();
            aforo.release();
        }
    }
}
