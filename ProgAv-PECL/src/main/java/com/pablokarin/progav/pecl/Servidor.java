/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pablokarin.progav.pecl;

import com.pablokarin.progav.conexion.Operador;
import com.pablokarin.progav.jframe.VentanaPrincipal;
import com.pablokarin.progav.part1.Hormiguero;
import com.pablokarin.progav.part1.hilos.*;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Servidor {

    
    
    public static void main(String[] args) 
    {
        //se inicia el servidor
        try
        {
            Operador op = new Operador();
            Registry reg = LocateRegistry.createRegistry(1099);
            Naming.rebind("//127.0.0.1/ObjOperador", op);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        //se muestra la pantalla
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        
        //se crea el hilo que actualiza la pantalla
        ScreenUpdate screenUpdater = new ScreenUpdate(ventana);
        screenUpdater.start();
        
        //prepara el log
        String inicio ="=======COMIENZA LA EJECUCIÓN DEL PROGRMA=======";
        StringBuilder sb = new StringBuilder();
        sb.append((System.lineSeparator()));
        sb.append(inicio);
        sb.append((System.lineSeparator()));
        inicio = sb.toString();
        try 
        {

            FileWriter fileWriter = new FileWriter("src/main/java/com/pablokarin/progav/log/evolucionColonia.txt", true);
            //Se escribe en el log que comienza el programa
            fileWriter.write(inicio);
            fileWriter.close();
        } 
        catch (IOException e) {System.out.println("No se pudo escribir en el archivo. Error de I/0: " + e);}
        
        
       
        Lock l = new ReentrantLock();
        Condition c = l.newCondition();
        
        //contadores
        int soldados = 0;
        int crias = 0;
        int obreras = 0;
        
        //generador de hormigas
        for (int i = 1; i <= 10000; i++)
        {
            //en caso de que esté pausado
            //el bucle no avanza
            if(Hormiguero.isPausa())
            {
                i--;
            }
            else
            {
                //espera para crear
                try
                {
                    Thread.sleep(new Random().nextInt(2701)+800);
                }
                catch (InterruptedException IE)
                {
                    System.out.println(IE.getMessage());
                }
                //comprueba si se ha parado la simulacion mientras descansaba
                if (!Hormiguero.isPausa())
                {
                    if ((i%5)==0)
                    {
                        
                        //genera una soldado
                        new Thread(new Soldado(soldados)).start();
                        Hormiguero.aumentarSoldados();
                        soldados++;
                    }
                    else if (((i%5)-1)==0)
                    {

                        //genera una cria
                        new Thread(new Cria(crias)).start();
                        crias++;
                    }
                    else
                    {

                        //genera una obrera
                        new Thread(new Obrera(obreras)).start();
                        obreras++;
                    }
                }
            }
        }
    }
}
