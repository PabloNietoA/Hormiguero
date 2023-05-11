package com.pablokarin.progav.part1;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import java.sql.Timestamp;
import java.util.concurrent.locks.*;

public class Refugio {
    
    private static final Lock control = new ReentrantLock();
    private static final Condition espera = control.newCondition();
    
    //mete a las crias en el refugio
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
        finally
        {
            control.unlock();
        }
    }
    
    //avisa de que tienen que salir del refugio
    public static void terminarAmenaza()
    {
        try
        {
            control.lock();
            espera.signalAll();
        }
        finally
        {
            control.unlock();
        }
    }
}
