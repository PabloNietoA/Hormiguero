/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.pecl;

import com.pablokarin.progav.jframe.VentanaPrincipal;
import com.pablokarin.progav.part1.Almacen;

/**
 *
 * @author Slend
 */
public class ScreenUpdate extends Thread 
{    
    private VentanaPrincipal ventanaPrincipal;
    
    public ScreenUpdate(VentanaPrincipal ventana)
    {
        ventanaPrincipal = ventana;
    }
    
    public void run()
    {
        this.setName("ScreenUpdater");
        while(true)
        {
            ventanaPrincipal.updateData();
        }
    }
}
