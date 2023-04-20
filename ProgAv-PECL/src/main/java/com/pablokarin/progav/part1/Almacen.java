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
    private int stock;
    private final Semaphore aforo;
    private Lock control = new ReentrantLock();
    private Condition vacio = control.newCondition();

    public Almacen(int stock, int aforo) 
    {
        this.stock = stock;
        this.aforo = new Semaphore(aforo, true);
    }
    
    public synchronized void incStock(int inc)
    {
        try 
        {
            aforo.acquire();
            Thread.sleep((new Random().nextInt(2) + 2)*1000);
            control.lock();
            stock += inc;
            //puede haber problemas si llegan 10 recolectores (que hacer?)
            vacio.signalAll();
            aforo.release();
        }
        catch(InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
        finally 
        {
            control.unlock();
        }
    }
    public synchronized void decStock(int dec)
    {
        try
        {
            aforo.acquire();
            while (stock < dec)
            {
                aforo.release();
                vacio.await();
                aforo.acquire();
            }
            Thread.sleep((new Random().nextInt(1) + 1) * 1000);
            
        }
        catch (InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
    }
}
