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
