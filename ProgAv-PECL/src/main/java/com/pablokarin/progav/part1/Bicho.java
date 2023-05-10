/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import static java.lang.Thread.sleep;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Slend
 */
public class Bicho implements Runnable
{
    private final CountDownLatch pelea;
    
    public Bicho(CountDownLatch l)
    {
        pelea = l;
    }
    
    @Override
    public void run()
    {
        //comienza la pelea (dura 20 s)
        try
        {
            sleep(20000);
        }
        catch(InterruptedException IE){}
        
        //al terminar la pelea libera a todas las soldado peleando
        pelea.countDown();
        Refugio.terminarAmenaza();
        
        //notifica a las crias de que la amenaza ha terminado
        Refugio.terminarAmenaza();
    }
}
