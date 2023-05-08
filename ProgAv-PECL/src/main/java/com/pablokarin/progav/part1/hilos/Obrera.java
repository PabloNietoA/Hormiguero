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
 * @author Slend
 */
public class Obrera implements Hormiga{
    private int iteracion = 0;
    private int id;
    private String nombre;
    
    
    public Obrera (int id)
   {
       this.id = id;
       if (id < 10)
        {
            nombre = "HO000" + id;
        }
        else
        {
            if (id < 100)
            {
                nombre = "HO00" + id;
            }
            else
            {    
                if (id<1000)
                {
                    nombre = "HO0" + id;
                }
                else
                {
                    if (id<10000)
                    {
                        nombre = "HO" + id;
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
    
    @Override
    public void run(){
        //las obreras pares
        if (id%2==0)
        {
            while (true)
            {
                Almacen.decStock(5);
                //camina del almacén al comedor
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 6, timestamp);
                Escritor.logger.execute(entrada);
                try
                {
                    Thread.sleep((new Random().nextInt(2) + 1) * 1000);
                }
                catch(InterruptedException IE)
                {
                    System.out.println(IE.getMessage());
                }
                Comedor.incStock(5);
                //cada 10 iteraciones
                if (iteracion%10==0)
                {
                    Hormiguero.getComer().add(this);
                    Comedor.comer(1, 3);
                    Hormiguero.getComer().remove(this);
                    Hormiguero.getDescanso().add(this);
                    Descanso.descansar(1);
                    Hormiguero.getDescanso().remove(this);
                }
                iteracion++;
            }
        }
        //las obreras impares
        else
        {
            while (true)
            {
                //Hormiguero.getFuera()
                //hormiguero.recolectar(); //lleva salir y entrar dentro
                Almacen.incStock(5);
                //cada 10 iteraciones              
                if (iteracion%10==0)
                {
                    Hormiguero.getComer().add(this);
                    Comedor.comer(1, 3);
                    Hormiguero.getComer().remove(this);
                    Hormiguero.getDescanso().add(this);
                    Descanso.descansar(1);
                    Hormiguero.getDescanso().remove(this);
                }
                iteracion++;
            }
            
        }
    }
}
