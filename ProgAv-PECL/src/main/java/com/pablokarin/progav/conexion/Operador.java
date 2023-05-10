package com.pablokarin.progav.conexion;

import com.pablokarin.progav.part1.Hormiguero;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Operador extends UnicastRemoteObject implements InterfazOperaciones
{
    
    public Operador () throws RemoteException{}
    
    public void generarAmenaza() throws RemoteException
    {
        Hormiguero.ataque();
    }
    
    public int getNObrerasFuera() throws RemoteException
    {
        return Hormiguero.getFuera().size();
    }
    
    public int getNObrerasDentro() throws RemoteException
    {
        return Hormiguero.getNObreras()-Hormiguero.getFuera().size();
    }
    
    public int getNSoldadosInstruc() throws RemoteException
    {
        return Hormiguero.getInstruc().size();
    }
    
    public int getNSoldadosAmenaza() throws RemoteException
    {
        return Hormiguero.getDefendiendo().size();
    }
    
    public int getNCriasComedor() throws RemoteException
    {
        return Hormiguero.getNCriasComiendo();
    }
    public int getNCriasRefugio() throws RemoteException
    {
        return Hormiguero.getRefugio().size();
    }
    
}
