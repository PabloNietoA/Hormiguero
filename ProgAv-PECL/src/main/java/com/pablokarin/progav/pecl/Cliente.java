/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.pablokarin.progav.pecl;

import com.pablokarin.progav.conexion.*;
import com.pablokarin.progav.jframe.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 *
 * @author Karín
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        VentanaRemota ventana = new VentanaRemota();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        try
        {
            InterfazOperaciones op = (InterfazOperaciones) Naming.lookup("//127.0.0.1/ObjOperador");
            while(true)
            {
                while (true && (!pulsado))
                {
                   int nObrerasFuera = op.getNObrerasFuera();
                   ventana.modificar(ventana.getCampoNObrerasFuera(), Integer.toString(nObrerasFuera));
                   int nObrerasDentro = op.getNObrerasDentro();
                   ventana.modificar(ventana.getCampoNObrerasDentro(), Integer.toString(nObrerasDentro));
                   int nSoldadosInstruc = op.getNSoldadosInstruc();
                   ventana.modificar(ventana.getCampoNSoldadosInstruc(), Integer.toString(nSoldadosInstruc));
                   int nSoldadosAmenaza = op.getNSoldadosAmenaza();
                   ventana.modificar(ventana.getCampoNSoldadosAmenaza(), Integer.toString(nSoldadosAmenaza));
                   int nCriasComedor = op.getNCriasComedor();
                   ventana.modificar(ventana.getCampoNCriasComedor(), Integer.toString(nCriasComedor));
                   int nCriasRefugiadas = op.getNCriasRefugio();
                   ventana.modificar(ventana.getCampoNCriasRefugio(), Integer.toString(nCriasRefugiadas));
                }
                generarAmenaza();
                pulsado = false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    public static void amenaza()
    {
        try
        {
            InterfazOperaciones op = (InterfazOperaciones) Naming.lookup("//127.0.0.1/ObjOperador");
            op.generarAmenaza();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
    

