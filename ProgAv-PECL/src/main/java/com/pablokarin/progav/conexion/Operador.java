package com.pablokarin.progav.conexion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Operador extends UnicastRemoteObject implements InterfazOperaciones
{
    //sustituir todo esto por sizeof(?)
    private int nObrerasFuera;
    private int nObrerasDentro;
    private int nSoldadosInstruc;
    private int nSoldadosAmenaza;
    private int nCriasComedor;
    private int nCriasRefugio;
    
    public Operador () throws RemoteException{}
    
    public void generarAmenaza() throws RemoteException
    {
        //código de generación de amenaza
    }
    
    public int getNObrerasFuera() throws RemoteException
    {
        return nObrerasFuera;
    }
    
    public int getNObrerasDentro() throws RemoteException
    {
        return nObrerasDentro;
    }
    
    public int getNSoldadosInstruc() throws RemoteException
    {
        return nSoldadosInstruc;
    }
    
    public int getNSoldadosAmenaza() throws RemoteException
    {
        return nSoldadosAmenaza;
    }
    
    public int getNCriasComedor() throws RemoteException
    {
        return nCriasComedor;
    }
    public int getNCriasRefugio() throws RemoteException
    {
        return nCriasRefugio;
    }
    
}
