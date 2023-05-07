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
    private static CyclicBarrier barreraAtaque;
    private static CountDownLatch bloqueoPelea;
    
    private static ArrayList<Obrera> almacen = new ArrayList();
    private static ArrayList<Hormiga> comer = new ArrayList();
    private static ArrayList<Hormiga> descanso = new ArrayList();
    private static ArrayList<Obrera> fuera = new ArrayList();
    private static ArrayList<Obrera> movimiento = new ArrayList();
    private static ArrayList<Soldado> defendiendo = new ArrayList();
    private static ArrayList<Soldado> instruc = new ArrayList();
    private static ArrayList<Cria> refugio = new ArrayList();
    private static ArrayList<Soldado> soldados = new ArrayList();
    
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
        barreraAtaque = new CyclicBarrier(soldados.size(), new Bicho(bloqueoPelea));
        Soldado.llamarAtaque(barreraAtaque, bloqueoPelea);
    }
    
    
    // <editor-fold desc="GETTER AND SETTER">
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
