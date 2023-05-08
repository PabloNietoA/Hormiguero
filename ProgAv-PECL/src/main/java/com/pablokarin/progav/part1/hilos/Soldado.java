/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import com.pablokarin.progav.part1.*;
import java.sql.Timestamp;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author Karín
 */
public class Soldado implements Hormiga {
    private int id;
    private int iteracion;
    private static CyclicBarrier barrera;
    private static CountDownLatch latch;
    private String nombre;
    
    public Soldado(int id)
    {
        this.id = id;
               if (id < 10)
        {
            nombre = "HS000" + id;
        }
        else
        {
            if (id < 100)
            {
                nombre = "HS00" + id;
            }
            else
            {    
                if (id<1000)
                {
                    nombre = "HS0" + id;
                }
                else
                {
                    if (id<10000)
                    {
                        nombre = "HS" + id;
                    }
                }
            }
        }
        setName(nombre);
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void run()
    {
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 0, timestamp1);
        Escritor.logger.execute(entrada1);
        //bucle principal de comportamiento
        while (true)
        {
            //va a comer cada 6 iteraciones de comportamiento
            if (iteracion %6 == 0)
            {
                try
                {
                    Comedor.comer(1, 3);
                }
                catch(InterruptedException IE)
                {
                    interrumpido();
                }
            }
            else
            {
                try
                {
                    //entrena
                    Instruc.instruir();
                }
                catch(InterruptedException IE)
                {
                    interrumpido();
                }

                try
                {
                    //descansa 2 sec
                    Descanso.descansar(2);
                }
                catch(InterruptedException IE)
                {
                    interrumpido();
                }
            }
            iteracion++;
        }
    }
    
    //se llama al manejar la interrupción generada por una ataque
    public void interrumpido()
    {
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 0, timestamp1);
        Escritor.logger.execute(entrada1);
        //sale del hormiguero
        Hormiguero.salir();
        //entra en la cyclicbarrier de espera
        try
        {
            barrera.await();
        }
        catch(InterruptedException | BrokenBarrierException IE){}

        //entra a la pelea (CountdownLatch)
        try
        {
            latch.await();
        }
        catch (InterruptedException IE){}
        
        //vuelve a entrar en el hormiguero
        Hormiguero.entrar();
    }

    //se llama desde el hormiguero cada vez que hay un ataque
    public static void llamarAtaque(CyclicBarrier b, CountDownLatch l)
    {
        Thread.currentThread().interrupt();
        barrera = b;
        latch = l;

    }
}
