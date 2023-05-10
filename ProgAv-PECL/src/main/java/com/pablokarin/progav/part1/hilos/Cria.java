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
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karín
 */
public class Cria implements Hormiga 
{
    
    private String nombre;
    private static final ArrayList<Cria> listaCrias = new ArrayList();
    private static CountDownLatch latch;
    
    public Cria (int id)
    {
        listaCrias.add(this);
        if (id < 10)
        {
            nombre = "HC000" + id;
        }
        else
        {
            if (id < 100)
            {
                nombre = "HC00" + id;
            }
            else
            {    
                if (id<1000)
                {
                    nombre = "HC0" + id;
                }
                else
                {
                    if (id<10000)
                    {
                        nombre = "HC" + id;
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
        while (true)
        {
            while(true)
            {
                try
                {
                    Hormiguero.getComer().add(this);
                    Hormiguero.setNCriasComiendo(Hormiguero.getNCriasComiendo()+1);
                    Comedor.comer(1, new Random().nextInt(2) + 3);
                    Hormiguero.setNCriasComiendo(Hormiguero.getNCriasComiendo()-1);
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
                try
                {
                    Hormiguero.getDescanso().add(this);
                    Descanso.descansar(4);
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
                    
                    if(pausado) Hormiguero.getDescanso().remove(this);
                }
            }
        }
    }
    public static void llamarAtaque()
    {
        ArrayList<Thread> crias = new ArrayList();
        for(int i = 0; i < listaCrias.size(); i++)
        {
            String nombre = listaCrias.get(i).getNombre();
            for (Thread t : Thread.getAllStackTraces().keySet())
            {

                if(t.getName().equals(nombre)) 
                {
                    t.interrupt();
                }
            }
        }
    }
    public void interrumpido()
    {
        if (Hormiguero.isPausa())
        {
            try {
                latch.await();
            } catch (InterruptedException ex) {}
        }
        else
        {
            try
            {
                Hormiguero.getRefugio().add(this);
                Refugio.refugiar();

                Hormiguero.getRefugio().remove(this);
            }
            catch(InterruptedException IE){}
        }
    }

    public static void setLatch(CountDownLatch latch) {
        Cria.latch = latch;
    }
}
