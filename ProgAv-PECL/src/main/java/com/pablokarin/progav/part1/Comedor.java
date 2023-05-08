/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Comedor 
{
    private static int stock = 0;
    private static Lock control = new ReentrantLock();
    private static Condition vacio = control.newCondition();
    
    //pilla del stock y luego come
    public static void comer(int comer, int tiempo) throws InterruptedException
    {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 1, timestamp);
        Escritor.logger.execute(entrada);
        
        //prueba a comer
        try
        {
            control.lock();
            //mira si hay stock y si no espera
            while (stock == 0)
            {
                //hace unlock?
                vacio.await();
            }
            //come
            stock -= comer;
            
        }
        finally
        {
            control.unlock();
        }
        
        Thread.sleep(tiempo * 1000);
    }
    
    public static void incStock(int inc)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 7, timestamp);
        Escritor.logger.execute(entrada);
        try 
        {
            Thread.sleep((new Random().nextInt(2) + 1) * 1000);
        } 
        catch (InterruptedException IE) 
        {
            System.out.println(IE.getMessage());
        }
        
        try 
        {
            control.lock();
            stock += inc;
            vacio.signalAll();
        }
        finally 
        {
            control.unlock();
        }
    }
     
}
