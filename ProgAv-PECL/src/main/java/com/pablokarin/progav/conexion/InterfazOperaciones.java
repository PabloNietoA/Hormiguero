package com.pablokarin.progav.conexion;

import java.rmi.Remote;
import java.rmi.RemoteException;

//ACABADO

public interface InterfazOperaciones extends Remote
{
    void generarAmenaza() throws RemoteException; //se declara el método que genera la amenaza   
}
