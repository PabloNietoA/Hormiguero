/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import java.util.Random;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Comedor 
{
    private int stock;
    private Lock control = new ReentrantLock();
    private Condition vacio = control.newCondition();

    public Comedor(int stock) 
    {
        this.stock = stock;
    }
    
    //pilla del stock y luego come
    public void comer(int comer, int tiempo)
    {
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
        catch(InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
        finally
        {
            control.unlock();
        }
        
        try
        {
            Thread.sleep(tiempo * 1000);
        }
        catch(InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
    }
    
    public void incStock(int inc)
    {
        try 
        {
            Thread.sleep((new Random().nextInt() + 1) * 1000);
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
