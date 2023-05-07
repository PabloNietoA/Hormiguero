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
public class Cria implements Hormiga {
    
    private String 
    private int id;
    private boolean triggered;
    
    public Cria (int id)
    {
        this.id = id;
    }
    
    public void run()
    {
        while (true)
        {
            while(!triggered)
            {
                try
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
