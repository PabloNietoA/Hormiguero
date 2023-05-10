/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import com.pablokarin.progav.part1.hilos.Cria;
import java.sql.Timestamp;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Refugio {
    
    private static final Lock control = new ReentrantLock();
    private static final Condition espera = control.newCondition();
    
    public static void refugiar() throws InterruptedException
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 10, timestamp);
        Escritor.logger.execute(entrada);
        try
        {
            control.lock();
            espera.await();
        }
        catch (InterruptedException IE)
        {
            control.unlock();
        }
    }
    public static void terminarAmenaza()
    {
        Cria.setAmenazado(false);
        espera.signalAll();
    }
}
