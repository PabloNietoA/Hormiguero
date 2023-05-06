/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.part1.*;

/**
 *
 * @author Karín
 */
public class Soldado implements Hormiga {
    private int id;
    private int iteracion;
    
    public Soldado(int id)
    {
        this.id = id;
    }
    
    public void run()
    {
        while (true)
        {
            if (iteracion %6 == 0)
            {
                Comedor.comer(1, 3);
            }
            else
            {
                Instruc.instruir();
                Descanso.descansar(2);
            }
            iteracion++;
        }
    }
}
