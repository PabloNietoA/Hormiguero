package com.pablokarin.progav.conexion;

import com.pablokarin.progav.part1.Hormiguero;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Operador extends UnicastRemoteObject implements InterfazOperaciones
{
    
    public Operador () throws RemoteException{}
    
    //genera amenaza en el hormiguero
    public void generarAmenaza() throws RemoteException
    {
        Hormiguero.ataque();
    }
    
    //devuelve el numero de Obreras fuera del hormiguero
    public int getNObrerasFuera() throws RemoteException
    {
        return Hormiguero.getFuera().size();
    }
    
    //devuelve el numero de Obreras dentro del hormiguero
    public int getNObrerasDentro() throws RemoteException
    {
        return Hormiguero.getNObreras()-Hormiguero.getFuera().size();
    }
    
    //devuelve el numero de Soldados instruyendose
    public int getNSoldadosInstruc() throws RemoteException
    {
        return Hormiguero.getInstruc().size();
    }
    //devuelve el numero de soldados defendiendo la amenaza
    public int getNSoldadosAmenaza() throws RemoteException
    {
        return Hormiguero.getDefendiendo().size();
    }
    
    //devuelve el numero de crias en el comedor
    public int getNCriasComedor() throws RemoteException
    {
        return Hormiguero.getNCriasComiendo();
    }
    
    //devuelve el numero de crias en el refugio
    public int getNCriasRefugio() throws RemoteException
    {
        return Hormiguero.getRefugio().size();
    }
    
}
