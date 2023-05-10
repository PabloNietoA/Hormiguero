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
    
    //incrementa el stock del almacen
    public static void incStock(int inc, Obrera obr) throws InterruptedException
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 4, timestamp);
        Escritor.logger.execute(entrada);
        
        try 
        {
            //entra al almacen
            aforo.acquire();
            //gestion de listas
            Hormiguero.getAlmacen().add(obr);
            //descarga
            Thread.sleep((new Random().nextInt(3) + 2)*1000);
            //obtiene el lock de control
            control.lock();
            //modifica la variable
            stock += inc;
            //avisa a los que estan esperando por stock
            vacio.signalAll();
        }
        finally 
        {
            try
            {
            control.unlock();
            }
            catch (Exception e) {}
            Hormiguero.getAlmacen().remove(obr);
            aforo.release();
        }
    }

    public static int getStock() {
        return stock;
    }
    
    //disminuye el stock del almacen
    public static synchronized void decStock(int dec, Obrera obr) throws InterruptedException
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 5, timestamp);
        Escritor.logger.execute(entrada);
        
        try
        {
            //entra al almacen
            aforo.acquire();
            //gestion de listas
            Hormiguero.getAlmacen().add(obr);
            control.lock();
            //mira la variable y si no hay stock se sale del almacen a esperar
            while (stock < dec)
            {
                Hormiguero.getAlmacen().remove(obr);
                
                //para evitar que se quede el almacen lleno de hormigas esperando por stock
                aforo.release();
                
                //sigue desde el await cuando ocurre el signal
                vacio.await();
                
                //vuelve a entrar al almacen
                aforo.acquire();
                
                Hormiguero.getAlmacen().add(obr);
            }
            //saca comida
            Thread.sleep((new Random().nextInt(2) + 1) * 1000);
            stock -= dec;
            
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
