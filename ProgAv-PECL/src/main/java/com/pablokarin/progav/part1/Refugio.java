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
    
    private static final Lock control = new ReentrantLock();
    private static final Condition espera = control.newCondition();
    
    public static void refugiar() throws InterruptedException
    {
        espera.await();
    }
    public static void terminarAmenaza()
    {
        espera.signalAll();
    }
}
