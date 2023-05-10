/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import com.pablokarin.progav.part1.hilos.*;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Slend
 */
public class Hormiguero 
{
    private static Semaphore salida = new Semaphore(2,true);
    private static Lock entrada = new ReentrantLock();
    private static int hormigasVivas = 0;
    private static int nObreras = 0;
    private static int nCriasComiendo = 0;
    private static CyclicBarrier barreraAtaque;
    private static CountDownLatch bloqueoPelea;
    
    
    
    private static ArrayList<Obrera> almacen = new ArrayList();
    private static ArrayList<Hormiga> comer = new ArrayList();
    private static ArrayList<Hormiga> descanso = new ArrayList();
    private static ArrayList<Obrera> fuera = new ArrayList();
    private static ArrayList<Obrera> movimiento = new ArrayList();
    private static ArrayList<Obrera> dejandoComida = new ArrayList();
    private static ArrayList<Soldado> defendiendo = new ArrayList();
    private static ArrayList<Soldado> instruc = new ArrayList();
    private static ArrayList<Cria> refugio = new ArrayList();
    private static ArrayList<Soldado> listaSoldados = new ArrayList();
    private static int soldados = 0;

    public static void aumentarSoldados() {
        soldados++;
    }
    
    public static void entrar()
    {
        try
        {
            entrada.lock();
            Thread.sleep(100);            
        }
        catch(InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
        finally
        {
            entrada.unlock();
        }
    }
    
    public static void salir()
    {
        try
        {
            salida.acquire();
            Thread.sleep(100); 
        }
         catch(InterruptedException IE)
        {
            System.out.println(IE.getMessage());
        }
        finally
        {
            salida.release();
        }
    }
    //se llama al iniciar un ataque
    public static void ataque()
    {
        //TRIGGER CRIAS
        bloqueoPelea = new CountDownLatch(1);
        barreraAtaque = new CyclicBarrier( soldados, new Bicho(bloqueoPelea));
        Soldado.llamarAtaque(barreraAtaque, bloqueoPelea);
        Cria.llamarAtaque();
    }
    
    
    // <editor-fold desc="GETTER AND SETTER">
    public synchronized static int getNObreras()
    {
        return nObreras;
    }
    public static void setNObreras(int nObreras)
    {
        Hormiguero.nObreras = nObreras;
    }
    public synchronized static int getNHormigasVivas()
    {
        return hormigasVivas;
    }
    public static void setNHormigasVivas(int hormigasVivas)
    {
        Hormiguero.hormigasVivas = hormigasVivas;
    }
    
    public synchronized static int getNCriasComiendo()
    {
        return nCriasComiendo;
    }
    
    public static void setNCriasComiendo(int nCriasComiendo)
    {
        Hormiguero.nCriasComiendo = nCriasComiendo;
    }
    
    public synchronized static ArrayList<Obrera> getAlmacen() {
        return almacen;
    }

    public static void setAlmacen(ArrayList<Obrera> almacen) {
        Hormiguero.almacen = almacen;
    }

    public synchronized static ArrayList<Hormiga> getComer() {
        return comer;
    }

    public static void setComer(ArrayList<Hormiga> comer) {
        Hormiguero.comer = comer;
    }

    public synchronized static ArrayList<Hormiga> getDescanso() {
        return descanso;
    }

    public static void setDescanso(ArrayList<Hormiga> descanso) {
        Hormiguero.descanso = descanso;
    }

    public synchronized static ArrayList<Obrera> getFuera() {
        return fuera;
    }

    public static void setFuera(ArrayList<Obrera> fuera) {
        Hormiguero.fuera = fuera;
    }

    public synchronized static ArrayList<Obrera> getMovimiento() {
        return movimiento;
    }

    public static void setMovimiento(ArrayList<Obrera> movimiento) {
        Hormiguero.movimiento = movimiento;
    }
    
    public synchronized static ArrayList<Obrera> getDejandoComida()
    {
        return dejandoComida;
    }
    
    public static void setDejandoComida(ArrayList<Obrera> dejandoComida)
    {
        Hormiguero.dejandoComida = dejandoComida;
    }

    public synchronized static ArrayList<Soldado> getDefendiendo() {
        return defendiendo;
    }

    public static void setDefendiendo(ArrayList<Soldado> defendiendo) {
        Hormiguero.defendiendo = defendiendo;
    }

    public synchronized static ArrayList<Soldado> getInstruc() {
        return instruc;
    }

    public static void setInstruc(ArrayList<Soldado> instruc) {
        Hormiguero.instruc = instruc;
    }

    public synchronized static ArrayList<Cria> getRefugio() {
        return refugio;
    }

    public static void setRefugio(ArrayList<Cria> refugio) {
        Hormiguero.refugio = refugio;
    }
    // </editor-fold>
}
