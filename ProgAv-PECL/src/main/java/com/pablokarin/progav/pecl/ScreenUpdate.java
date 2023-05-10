/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.pecl;

import com.pablokarin.progav.jframe.VentanaPrincipal;

/**
 *
 * @author Slend
 */
public class ScreenUpdate extends Thread 
{    
    private final VentanaPrincipal ventanaPrincipal;
    
    public ScreenUpdate(VentanaPrincipal ventana)
    {
        ventanaPrincipal = ventana;
    }
    
    @Override
    public void run()
    {
        this.setName("ScreenUpdater");
        while(true)
        {
            ventanaPrincipal.updateData();
        }
    }
}
