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

/**
 *
 * @author Karín
 */
public class Cria implements Hormiga 
{
    
    private String nombre;
    //lista estatica de crias
    private static final ArrayList<Cria> listaCrias = new ArrayList();
    private static CountDownLatch latch;
    
    public Cria (int id)
    {
        //agrega a lista estatica de crias
        listaCrias.add(this);
        
        //genera el nombre de la hormiga
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
    
    @Override
    public String getNombre()
    {
        return nombre;
    }
    
    public void run()
    {
        //pone nombre al hilo
        Thread.currentThread().setName(nombre);
        
        //Aumenta el numero de hormigas vivas
        Hormiguero.setNHormigasVivas(Hormiguero.getNHormigasVivas()+1);
        
        //timestamps para el logger
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 0, timestamp1);
        Escritor.logger.execute(entrada1);
        
        //entra recien nacida
        try 
        {
            Hormiguero.entrar();
        } 
        catch (InterruptedException ex) 
        {
            interrumpido();
        }
        
        //comienza el comportamiento
        while (true)
        {
            //intenta dormir
            try
            {
                //actualiza las listas y come
                Hormiguero.getComer().add(this);
                Hormiguero.setNCriasComiendo(Hormiguero.getNCriasComiendo()+1);
                Comedor.comer(1, new Random().nextInt(2) + 3);
                Hormiguero.setNCriasComiendo(Hormiguero.getNCriasComiendo()-1);
                Hormiguero.getComer().remove(this);
            }
            catch(InterruptedException IE)
            {
                //gestión de listas en interrupciones
                boolean pausado = false;
                if (!Hormiguero.isPausa())
                {
                    Hormiguero.getComer().remove(this);
                }
                else 
                {
                    pausado = true;
                }
                
                //ocurre una interrupción
                interrumpido();

                if (pausado) 
                {
                    Hormiguero.getComer().remove(this);
                }
            }
            
            //intenta descansar
            try
            {
                //actualiza las listas y descansa
                Hormiguero.getDescanso().add(this);
                Descanso.descansar(4);
                Hormiguero.getDescanso().remove(this);
            }
            catch(InterruptedException IE)
            {
                //gestion de listas
                boolean pausado = false;
                if (!Hormiguero.isPausa())
                {
                    Hormiguero.getDescanso().remove(this);
                }
                else 
                {
                    pausado = true;
                }
                
                //gestiona la interrupcion
                interrumpido();

                if(pausado) 
                {
                    Hormiguero.getDescanso().remove(this);
                }
            }
        }
    }
    
    //protocolo en caso de ataque
    public static void llamarAtaque()
    {
        ArrayList<Thread> crias = new ArrayList();
        
        //toma todas las crias nacidas hasta el momento y
        // las interrumpe
        for(int i = 0; i < listaCrias.size(); i++)
        {
            String nombre = listaCrias.get(i).getNombre();
            //busca entre todos los hilos del programa
            for (Thread t : Thread.getAllStackTraces().keySet())
            {
                //si tiene nombre como la cria
                if(t.getName().equals(nombre)) 
                {
                    //interrumpe
                    t.interrupt();
                }
            }
        }
    }
    
    //protocolo a seguir en caso de interrupción
    public void interrumpido()
    {
        //distingue entre pausa y amenaza
        if (Hormiguero.isPausa())
        {
            //entra al CountDownLatch de la pausa
            try {
                latch.await();
            } catch (InterruptedException ex) 
            {
                //por si acaso
                interrumpido();
            }
        }
        else
        {
            try
            {
                //actualiza las listas y entra al refugio
                Hormiguero.getRefugio().add(this);
                
                Refugio.refugiar();
                
                Hormiguero.getRefugio().remove(this);
            }
            catch(InterruptedException IE)
            {
                //por si acaso
                interrumpido();
            }
        }
    }

    public static void setLatch(CountDownLatch latch) {
        Cria.latch = latch;
    }
}
