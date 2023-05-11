package com.pablokarin.progav.part1;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import java.sql.Timestamp;

public class Descanso {
    public static void descansar(int t) throws InterruptedException
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 2, timestamp);
        Escritor.logger.execute(entrada);
        
        //duerme t segundos
        Thread.sleep(t*1000);
    }
}
