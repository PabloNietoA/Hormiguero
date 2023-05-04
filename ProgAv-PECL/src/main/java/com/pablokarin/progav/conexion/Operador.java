package com.pablokarin.progav.conexion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Operador extends UnicastRemoteObject implements InterfazOperaciones
{
    public Operador () throws RemoteException{}
    public void generarAmenaza() throws RemoteException
    {
        //código de generación de amenaza
    }
    
}
