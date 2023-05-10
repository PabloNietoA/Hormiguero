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
   }
    public String getNombre()
    {
        return nombre;
    }
    
    @Override
    public void run()
    {
        Thread.currentThread().setName(nombre);
        Hormiguero.setNHormigasVivas(Hormiguero.getNHormigasVivas()+1);
        Hormiguero.setNObreras(Hormiguero.getNObreras()+1);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 0, timestamp1);
        Escritor.logger.execute(entrada1);
        //las obreras pares
        if (id%2==0)
        {
            while (true)
            {
                
                Hormiguero.getAlmacen().add(this);
                Almacen.decStock(5);
                Hormiguero.getAlmacen().remove(this);
                
                
                //camina del almacén al comedor
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 6, timestamp);
                Escritor.logger.execute(entrada);
                
                Hormiguero.getMovimiento().add(this);
                try
                {
                    Thread.sleep((new Random().nextInt(3) + 1) * 1000);
                }
                catch(InterruptedException IE)
                {
                    System.out.println(IE.getMessage());
                }
                Hormiguero.getMovimiento().remove(this);
                
                Hormiguero.getDejandoComida().add(this);
                Comedor.incStock(5);
                Hormiguero.getDejandoComida().remove(this);
                
                //cada 10 iteraciones
                if (iteracion%10==0&& iteracion !=0)
                {
                    //Entra a comer
                    Hormiguero.getComer().add(this);
                    try
                    {
                        Comedor.comer(1, 3);
                    }
                    catch(InterruptedException IE){}
                    
                    Hormiguero.getComer().remove(this);
                    
                    
                    //Entra a descansar
                    Hormiguero.getDescanso().add(this);
                    
                    try
                    {
                        Descanso.descansar(1);
                    }
                    catch(InterruptedException IE){}
                    
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
                //recolecta comida
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                TareaEscribir entrada = new TareaEscribir(Thread.currentThread().getName(), 3, timestamp);
                Escritor.logger.execute(entrada);
                
                Hormiguero.salir();
                
                Hormiguero.getFuera().add(this);
                try
                {
                    Thread.sleep(4000);
                }
                catch (InterruptedException IE)
                {
                
                }
                Hormiguero.getFuera().remove(this);
                Hormiguero.entrar();
                
                
                //hormiguero.recolectar(); //lleva salir y entrar dentro //sigue haciendo falta?
                
                Hormiguero.getAlmacen().add(this);
                Almacen.incStock(5);
                Hormiguero.getAlmacen().remove(this);
                //cada 10 iteraciones              
                if (iteracion%10==0&& iteracion !=0)
                {
                    //Entra a comer
                    Hormiguero.getComer().add(this);
                    
                    try
                    {
                        Comedor.comer(1, 3);
                    }
                    catch(InterruptedException IE){}
                    
                    Hormiguero.getComer().remove(this);
                    
                    
                    //Entra a descansar
                    Hormiguero.getDescanso().add(this);
                    
                    try
                    {
                        Descanso.descansar(1);
                    }
                    catch(InterruptedException IE){}
                    
                    Hormiguero.getDescanso().remove(this);
                }
                iteracion++;
            }
            
        }
    }
}
