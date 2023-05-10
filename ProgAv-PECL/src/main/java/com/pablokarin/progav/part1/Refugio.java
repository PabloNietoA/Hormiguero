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
        System.out.println("Se refugia");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 10, timestamp);
        Escritor.logger.execute(entrada);
        try
        {
            control.lock();
            System.out.println("Entra a refugio");
            espera.await();
            System.out.println("Unlock");
        }
        catch (InterruptedException IE)
        {
            System.out.println("Salgo del refugio");
            control.unlock();
            System.out.println("He salido del refugio");
        }
    }
    public static void terminarAmenaza()
    {
        System.out.println("Notifico");
        try
        {
            control.lock();
            espera.signalAll();
        }
        finally
        {
            control.unlock();
        }
        System.out.println("Notificado");
    }
}
