/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.part1.*;
import java.util.Random;

/**
 *
 * @author Slend
 */
public class Obrera extends Thread{
    private int iteracion = 0;
    private int id;
    private Hormiguero h;
    private Comedor c;
    private Descanso d;
    private Almacen a;
    
    
    public Obrera (int id, Hormiguero h, Comedor c, Descanso d, Almacen a)
   {
       this.id = id;
       this.h = h;
       this.c = c;
       this.d = d;
       this.a = a;
   }
    
    
    public void run(){
        //las obreras pares
        if (id%2==0)
        {
            while (true)
            {
                //almacen.decStock(5);
                //camina del almacén al comedor
                try
                {
                    sleep((new Random().nextInt(2) + 1) * 1000);
                }
                catch(InterruptedException IE)
                {
                    
                }
                //comedor.incStock(5);
                //cada 10 iteraciones
                if (iteracion%10==0)
                {
                    //comedor.comer y luego descanso.descansar
                }
                iteracion++;
            }
        }
        //las obreras impares
        else
        {
            while (true)
            {
                //hormiguero.recolectar(); //lleva salir y entrar dentro
                //almacen.incStock(5);
                //cada 10 iteraciones              
                if (iteracion%10==0)
                {
                    //comedor.comer y luego descanso.descansar
                }
                iteracion++;
            }
            
        }
    }
}
