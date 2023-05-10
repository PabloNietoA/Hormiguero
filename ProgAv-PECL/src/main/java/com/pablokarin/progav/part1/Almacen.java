/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import com.pablokarin.progav.part1.hilos.Obrera;
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
    
    public static void incStock(int inc, Obrera obr)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 4, timestamp);
        Escritor.logger.execute(entrada);
        try 
        {
            aforo.acquire();
            Hormiguero.getAlmacen().add(obr);
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
            Hormiguero.getAlmacen().remove(obr);
            aforo.release();
        }
    }

    public static int getStock() {
        return stock;
    }
    public static synchronized void decStock(int dec, Obrera obr)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 5, timestamp);
        Escritor.logger.execute(entrada);
        
        try
        {
            aforo.acquire();
            Hormiguero.getAlmacen().add(obr);
            control.lock();
            while (stock < dec)
            {
                Hormiguero.getAlmacen().remove(obr);
                aforo.release();
                //sigue desde el await cuando hace el signal
                vacio.await();
                
                aforo.acquire();
                Hormiguero.getAlmacen().add(obr);
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
                Hormiguero.getAlmacen().remove(obr);
                aforo.release();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
