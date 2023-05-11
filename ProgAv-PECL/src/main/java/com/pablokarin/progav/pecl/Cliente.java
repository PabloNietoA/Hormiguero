package com.pablokarin.progav.pecl;

import com.pablokarin.progav.conexion.*;
import com.pablokarin.progav.jframe.*;
import java.rmi.*;

public class Cliente {

    public static void main(String[] args) 
    {
        //crea la ventana
        VentanaRemota ventana = new VentanaRemota();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        try
        {
            InterfazOperaciones op = (InterfazOperaciones) Naming.lookup("//127.0.0.1/ObjOperador");
            while(true)
            {
                //actualiza los contadores del cliente
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
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    //genera una amenaza para la colonia
    //desde el cliente
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
        }
    }
}
    

