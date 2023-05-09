/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pablokarin.progav.pecl;

import com.pablokarin.progav.jframe.VentanaPrincipal;
import com.pablokarin.progav.part1.hilos.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Main {

    
    
    public static void main(String[] args) 
    {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        
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
        
        
        Test.getA().add(7);
        System.out.println(Test.getA().get(0));
        Lock l = new ReentrantLock();
        Condition c = l.newCondition();
        Test thread = new Test(1, l, c, true);
        Test thread2 = new Test (2, l, c, false);
        
        int soldados = 0;
        int crias = 0;
        int obreras = 0;
        
        
        
        thread.start();
        thread2.start();
        //creador de hormigas
        for (int i = 1; i <= 10000; i++)
        {
            try
            {
                Thread.sleep(new Random().nextInt(2701)+800);
            }
            catch (InterruptedException IE)
            {
                System.out.println(IE.getMessage());
            }
            if ((i%5)==0)
            {
                new Thread(new Soldado(soldados)).start();
                soldados++;
            }
            else
            {
                if (((i%5)-1)==0)
                {
                    new Thread(new Cria(crias)).start();
                    crias++;
                }
                else
                {
                    new Thread(new Obrera(obreras)).start();
                    obreras++;
                }
            }
        }
    }
}
