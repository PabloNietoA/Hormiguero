/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import java.sql.Timestamp;
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
    private static final Condition vacio = control.newCondition();
    
    public static synchronized void incStock(int inc)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 4, timestamp);
        Escritor.logger.execute(entrada);
        try 
        {
            aforo.acquire();
            Thread.sleep((new Random().nextInt(3) + 2)*1000);
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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 5, timestamp);
        Escritor.logger.execute(entrada);
        
        try
        {
            aforo.acquire();
            control.lock();
            while (stock < dec)
            {
                aforo.release();
                //sigue desde el await cuando hace el signal
                vacio.await();
                
                aforo.acquire();
            }
            Thread.sleep((new Random().nextInt(2) + 1) * 1000);
            stock -= dec;
            
        }
        catch (InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
        finally 
        {
            try
            {
                control.unlock();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            try
            {
                aforo.release();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
