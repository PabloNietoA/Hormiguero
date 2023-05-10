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

/**
 *
 * @author Karín
 */
public class Cria implements Hormiga 
{
    
    private String nombre;
    private int id;
    
    public Cria (int id)
    {
        this.id = id;
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
                    Comedor.comer(1, new Random().nextInt(2) + 3);
                    Hormiguero.getComer().remove(this);
                }
                catch(InterruptedException IE)
                {
                    interrumpido();
                }
                try
                {
                    Hormiguero.getDescanso().add(this);
                    Descanso.descansar(4);
                    Hormiguero.getDescanso().remove(this);
                }
                catch(InterruptedException IE)
                {
                    interrumpido();
                }
            }
        }
    }
    public static void llamarAtaque()
    {
        Thread.currentThread().interrupt();
    }
    public void interrumpido()
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
