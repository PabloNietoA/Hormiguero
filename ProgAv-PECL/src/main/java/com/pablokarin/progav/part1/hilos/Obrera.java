package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import com.pablokarin.progav.part1.*;
import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Obrera implements Hormiga{
    private int iteracion = 0;
    private final int id;
    private String nombre;
    private static CountDownLatch latch;

    public static void setLatch(CountDownLatch latch) {
        Obrera.latch = latch;
    }
    
    public Obrera (int id)
   {
       this.id = id;
       if (id < 10)
        {
            nombre = "HO000" + id;
        }
        else
        {
            if (id < 100)
            {
                nombre = "HO00" + id;
            }
            else
            {    
                if (id<1000)
                {
                    nombre = "HO0" + id;
                }
                else
                {
                    if (id<10000)
                    {
                        nombre = "HO" + id;
                    }
                }
            }
        }
    }
    
    @Override
    public String getNombre()
    {
        return nombre;
    }
    
    @Override
    public void run()
    {
        //pone nombre al hilo
        Thread.currentThread().setName(nombre);
        
        //actualiza el contador de hormigas vivas
        Hormiguero.setNHormigasVivas(Hormiguero.getNHormigasVivas()+1);
        //actualiza el contador de obreras
        Hormiguero.setNObreras(Hormiguero.getNObreras()+1);
        
        //logger y timestamps
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 0, timestamp1);
        Escritor.logger.execute(entrada1);
        
        //entra por primera vez
        try {
            Hormiguero.entrar();
        } 
        catch (InterruptedException ex) 
        {
           interrumpido();
        }
        //las obreras pares
        if (id%2==0)
        {
            //funcionamiento de las obreras pares
            while (true)
            {
                //saca comida del almacen
                try
                {
                    Almacen.decStock(5, this);
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 6, timestamp);
                Escritor.logger.execute(entrada);
                
                //camina del almacen al comedor
                Hormiguero.getMovimiento().add(this);
                try
                {
                    Thread.sleep((new Random().nextInt(3) + 1) * 1000);
                }
                catch(InterruptedException IE)
                {
                    interrumpido();
                }
                Hormiguero.getMovimiento().remove(this);
                
                //deja la comida en el comedor
                Hormiguero.getDejandoComida().add(this);
                try
                {
                    Comedor.incStock(5);
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                Hormiguero.getDejandoComida().remove(this);
                
                //cada 10 iteraciones
                if (iteracion%10==0&& iteracion !=0)
                {
                    //Entra a comer
                    Hormiguero.getComer().add(this);
                    try
                    {
                        Comedor.comer(1, 3);
                    }
                    catch(InterruptedException IE)
                    {
                        interrumpido();
                    }
                    Hormiguero.getComer().remove(this);
                    
                    
                    //Entra a descansar
                    Hormiguero.getDescanso().add(this);
                    try
                    {
                        Descanso.descansar(1);
                    }
                    catch(InterruptedException IE)
                    {
                        interrumpido();
                    }
                    Hormiguero.getDescanso().remove(this);
                }
                //sube el contador de iteracion
                iteracion++;
            }
        }
        //las obreras impares
        else
        {
            //comportamiento de las obreras impares
            while (true)
            {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 3, timestamp);
                Escritor.logger.execute(entrada);
                
                //sale del hormiguero
                try
                {
                    Hormiguero.salir();
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                
                //recolecta comida
                Hormiguero.getFuera().add(this);
                try
                {
                    Thread.sleep(4000);
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                Hormiguero.getFuera().remove(this);
                
                //entra al hormiguero
                try
                { 
                    Hormiguero.entrar();
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                
                //deja la comida en el almacen
                try
                {
                    Almacen.incStock(5, this);
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                
                //cada 10 iteraciones              
                if (iteracion%10==0&& iteracion !=0)
                {
                    //Entra a comer
                    Hormiguero.getComer().add(this);
                    try
                    {
                        Comedor.comer(1, 3);
                    }
                    catch(InterruptedException IE)
                    {
                        interrumpido();
                    }
                    Hormiguero.getComer().remove(this);
                    
                    //Entra a descansar
                    Hormiguero.getDescanso().add(this);
                    try
                    {
                        Descanso.descansar(1);
                    }
                    catch(InterruptedException IE)
                    {
                        interrumpido();
                    }
                    Hormiguero.getDescanso().remove(this);
                }
                //aumenta las iteraciones del bucle
                iteracion++;
            }
        }
    }
    
    //comportamiento en caso de interrupcion
    public void interrumpido()
    {
        //solo se interrumpe por pausa
        try 
        {
            latch.await();
        } 
        catch (InterruptedException ex) 
        {
            //por si acaso
            interrumpido();
        }
    }
}
