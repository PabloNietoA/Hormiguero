/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import com.pablokarin.progav.part1.*;
import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Slend
 */
public class Obrera implements Hormiga{
    private int iteracion = 0;
    private int id;
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
    public String getNombre()
    {
        return nombre;
    }
    
    @Override
    public void run()
    {
        Thread.currentThread().setName(nombre);
        Hormiguero.setNHormigasVivas(Hormiguero.getNHormigasVivas()+1);
        Hormiguero.setNObreras(Hormiguero.getNObreras()+1);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 0, timestamp1);
        Escritor.logger.execute(entrada1);
        
        try {
            Hormiguero.entrar();
        } catch (InterruptedException ex) {
           interrumpido();
        }
        //las obreras pares
        if (id%2==0)
        {
            while (true)
            {
                try
                {
                    Almacen.decStock(5, this);
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                
                
                //camina del almacén al comedor
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 6, timestamp);
                Escritor.logger.execute(entrada);
                
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
                iteracion++;
            }
        }
        //las obreras impares
        else
        {
            while (true)
            {
                //recolecta comida
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 3, timestamp);
                Escritor.logger.execute(entrada);
                
                try
                {
                    Hormiguero.salir();
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                
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
                try
                { 
                    Hormiguero.entrar();
                }
                catch (InterruptedException IE)
                {
                    interrumpido();
                }
                
                //hormiguero.recolectar(); //lleva salir y entrar dentro //sigue haciendo falta?
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
                iteracion++;
            }
        }
    }
    
    public void interrumpido()
    {
        try {
            latch.await();
        } catch (InterruptedException ex) {}
    }
}
