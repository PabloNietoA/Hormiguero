/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.part1.*;
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
        setName(nombre);
        
    }
    public String getNombre()
    {
        return nombre;
    }
    public void run()
    {
        while (true)
        {
            while(true)
            {
                try
                {
                Comedor.comer(1, new Random().nextInt(2) + 3);
                }
                catch(InterruptedException IE)
                {
                    interrumpido();
                }
                try
                {
                Descanso.descansar(4);
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
        Refugio.refugiar();
    }
}
