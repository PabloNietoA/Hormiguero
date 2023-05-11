package com.pablokarin.progav.conexion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazOperaciones extends Remote
{
    //se declaran métodos para usar remotamente
    void generarAmenaza() throws RemoteException;
    int getNObrerasFuera() throws RemoteException;
    int getNObrerasDentro() throws RemoteException;
    int getNSoldadosInstruc() throws RemoteException;
    int getNSoldadosAmenaza() throws RemoteException;
    int getNCriasComedor() throws RemoteException;
    int getNCriasRefugio() throws RemoteException;
    
}
