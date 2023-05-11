package com.pablokarin.progav.part1;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import java.sql.Timestamp;
import java.util.Random;

public class Instruc {
    public static void instruir() throws InterruptedException
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 8, timestamp);
        Escritor.logger.execute(entrada);
        
        //gastan tiempo en instruirse
        Thread.sleep((new Random().nextInt(7) + 2) * 1000);
    }
}
