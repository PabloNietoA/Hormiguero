package com.pablokarin.progav.conexion;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor 
{//mover al main
    public static void main (String [] args)
    {
        try
        {
            Operador op = new Operador();
            Registry reg = LocateRegistry.createRegistry(1099);
            Naming.rebind("//127.0.0.1/ObjOperador", op);
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
