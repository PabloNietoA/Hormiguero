package com.pablokarin.progav.pecl;

import com.pablokarin.progav.jframe.VentanaPrincipal;

//Este hilo se encarga de actualizar la pantalla por su cuenta

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
