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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        try 
        {
            Hormiguero.entrar();
        }
        catch (InterruptedException ex) 
        {
            interrumpido();
        }
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
                    boolean pausado = false;
                    if (!Hormiguero.isPausa())
                    {
                        Hormiguero.getComer().remove(this);
                    }    
                    else pausado = true;
                    
                    interrumpido();
                    if (pausado) Hormiguero.getComer().remove(this);
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
                    boolean pausado = false;
                    if (!Hormiguero.isPausa())
                    {
                        Hormiguero.getInstruc().remove(this);
                    }
                    else pausado = true;

                    interrumpido();
                    
                    if (pausado) Hormiguero.getInstruc().remove(this);
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
                    boolean pausado = false;
                    if (!Hormiguero.isPausa())
                    {
                        Hormiguero.getDescanso().remove(this);
                    }
                    else pausado = true;

                    interrumpido();
                    if (pausado) Hormiguero.getDescanso().remove(this);
                        
                }
            }
            iteracion++;
        }
    }
    
    //se llama al manejar la interrupción generada por una ataque
    public void interrumpido()
    {
        if(Hormiguero.isPausa())
        {
            try
            {
                latch.await();
            }
            catch (InterruptedException IE){}
        }
        else
        {
            Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
            TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 10, timestamp1);
            Escritor.logger.execute(entrada1);
            try {
                //sale del hormiguero
                Hormiguero.salir();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
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
           try{ 
            Hormiguero.entrar();
           }
           catch(Exception e)
           {
               System.out.println(e.getMessage());
               e.printStackTrace();
           }
        }
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

    public static void setLatch(CountDownLatch latch) {
        Soldado.latch = latch;
    }
    
    
}
