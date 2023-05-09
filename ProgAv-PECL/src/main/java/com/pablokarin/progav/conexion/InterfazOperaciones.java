package com.pablokarin.progav.conexion;

import java.rmi.Remote;
import java.rmi.RemoteException;

//ACABADO

public interface InterfazOperaciones extends Remote
{
    void generarAmenaza() throws RemoteException; //se declara el método que genera la amenaza 
    int getNObrerasFuera() throws RemoteException;
    int getNObrerasDentro() throws RemoteException;
    int getNSoldadosInstruc() throws RemoteException;
    int getNSoldadosAmenaza() throws RemoteException;
    int getNCriasComedor() throws RemoteException;
    int getNCriasRefugio() throws RemoteException;
    
}
