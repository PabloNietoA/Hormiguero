/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import java.util.concurrent.locks.*;

/**
 *
 * @author Slend
 */
public class Refugio {
    
    private static Lock control = new ReentrantLock();
    private static Condition espera = control.newCondition();
    
    public static void refugiar ()
    {
        while (Bicho.getAmenaza() == true)
        {
            try
            {
                espera.await();
            }
            catch (InterruptedException IE)
            {
                System.out.println(IE.getMessage());
            }
        }
    }
    public static void terminarAmenaza()
    {
        espera.notifyAll();
    }
}
