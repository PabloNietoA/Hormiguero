/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.part1.*;
import static java.lang.Thread.sleep;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author Karín
 */
public class Soldado implements Hormiga {
    private int id;
    private int iteracion;
    private static CyclicBarrier barrera;
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
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void run()
    {
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
        //sale del hormiguero
        Hormiguero.salir();
        //entra en la cyclicbarrier de espera
        try
        {
            barrera.await();
        }
        catch(InterruptedException | BrokenBarrierException IE){}

        //pelea durante 20 segundos
        try
        {
            sleep(20000);
        }
        catch (InterruptedException IE){}
        //vuelve a entrar en el hormiguero
        Hormiguero.entrar();
    }

    //se llama desde el hormiguero cada vez que hay un ataque
    public static void llamarAtaque(CyclicBarrier b)
    {
        Thread.currentThread().interrupt();
        barrera = b;

    }
}
