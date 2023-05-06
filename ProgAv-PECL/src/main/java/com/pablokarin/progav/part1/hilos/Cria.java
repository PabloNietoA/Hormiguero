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
public class Cria extends Thread {
    
    private boolean triggered;
    
    public void run()
    {
        while (true)
        {
            while(!triggered)
            {
                Comedor.comer(1, new Random().nextInt(2) + 3);
                Descanso.descansar(4);
                //Comprobar Trigger
            }
            //TRIGGER
            Refugio.refugiar();
            triggered = false;
        }
    }
}
