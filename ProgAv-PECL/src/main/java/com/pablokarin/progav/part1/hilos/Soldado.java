/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import com.pablokarin.progav.part1.*;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    private static ArrayList<Soldado> listaSoldados = new ArrayList<Soldado>();
    
    public Soldado(int id)
    {
        listaSoldados.add(this);
        
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
        Thread.currentThread().setName(nombre);
        Hormiguero.setNHormigasVivas(Hormiguero.getNHormigasVivas()+1);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 0, timestamp1);
        Escritor.logger.execute(entrada1);
        //bucle principal de comportamiento
        while (true)
        {
            //va a comer cada 6 iteraciones de comportamiento
            if (iteracion %6 == 0 && iteracion !=0)
            {
                try
                {
                    
                    Hormiguero.getComer().add(this);
                    Comedor.comer(1, 3);
                    Hormiguero.getComer().remove(this);
                }
                catch(InterruptedException IE)
                {
                    Hormiguero.getComer().remove(this);    
                    
                    interrumpido();
                }
            }
            else
            {
                try
                {
                    //entrena
                    Hormiguero.getInstruc().add(this);
                    Instruc.instruir();
                    Hormiguero.getInstruc().remove(this);
                }
                catch(InterruptedException IE)
                {
                    Hormiguero.getInstruc().remove(this);

                    interrumpido();
                }

                try
                {
                    //descansa 2 sec
                    
                    Hormiguero.getDescanso().add(this);
                    Descanso.descansar(2);
                    Hormiguero.getDescanso().remove(this);
                }
                catch(InterruptedException IE)
                {
                    Hormiguero.getDescanso().remove(this);

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
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 10, timestamp1);
        Escritor.logger.execute(entrada1);
        //sale del hormiguero
        Hormiguero.salir();
        Hormiguero.getDefendiendo().add(this);
        //entra en la cyclicbarrier de espera
        try
        {
            barrera.await();
        }
        catch(Exception e){}

        //entra a la pelea (CountdownLatch)
        try
        {
            latch.await();
        }
        catch (InterruptedException IE){}
        
        //vuelve a entrar en el hormiguero
        Hormiguero.getDefendiendo().remove(this);
        Hormiguero.entrar();
    }

    //se llama desde el hormiguero cada vez que hay un ataque
    public static void llamarAtaque(CyclicBarrier b, CountDownLatch l)
    {
        barrera = b;
        latch = l;
        for(int i = 0; i < listaSoldados.size(); i++)
        {
            String nombre = listaSoldados.get(i).getNombre();
            for (Thread t : Thread.getAllStackTraces().keySet())
            {
                if(t.getName().equals(nombre)) t.interrupt();
            }
        }
    }
}
