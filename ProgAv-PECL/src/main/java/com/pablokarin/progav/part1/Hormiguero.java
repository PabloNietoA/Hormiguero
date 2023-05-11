package com.pablokarin.progav.part1;

import com.pablokarin.progav.part1.hilos.*;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;


public class Hormiguero 
{
    //control para entradas y salidas
    private static final Semaphore salida = new Semaphore(2,true);
    private static final Lock entrada = new ReentrantLock();
    
    //contadores de hormigas
    private static int hormigasVivas = 0;
    private static int nObreras = 0;
    private static int nCriasComiendo = 0;
    
    //bloqueos para la amenaza
    private static CyclicBarrier barreraAtaque;
    private static CountDownLatch bloqueoPelea;
    
    //bloqueos para la pausa
    private static CountDownLatch latchPausa;
    
    private static boolean pausa = false;
    public static boolean isPausa() 
    {
        return pausa;
    }
    
    //listas de hormigas para ScreenUpdate
    private static ArrayList<Obrera> almacen = new ArrayList();
    private static ArrayList<Hormiga> comer = new ArrayList();
    private static ArrayList<Hormiga> descanso = new ArrayList();
    private static ArrayList<Obrera> fuera = new ArrayList();
    private static ArrayList<Obrera> movimiento = new ArrayList();
    private static ArrayList<Obrera> dejandoComida = new ArrayList();
    private static ArrayList<Soldado> defendiendo = new ArrayList();
    private static ArrayList<Soldado> instruc = new ArrayList();
    private static ArrayList<Cria> refugio = new ArrayList();
    
    //cantidad de soldados
    private static int soldados = 0;
    //aumenta la cantidad de soldados
    public static void aumentarSoldados() {
        soldados++;
    }
    
    //entrar a la colonia
    public static void entrar()throws InterruptedException
    {
        try
        {
            entrada.lock();
            Thread.sleep(100);            
        }
      
        finally
        {
            entrada.unlock();
        }
    }
    
    //salir de la colonia
    public static void salir() throws InterruptedException
    {
        try
        {
            salida.acquire();
            Thread.sleep(100); 
        }

        finally
        {
            salida.release();
        }
    }
    //se llama al iniciar un ataque
    public static void ataque()
    {
        if(soldados > 0)
        {
            //inicializa los bloqueos
            bloqueoPelea = new CountDownLatch(1);
            barreraAtaque = new CyclicBarrier( soldados, new Bicho(bloqueoPelea));
            
            //avisa a las soldado
            Soldado.llamarAtaque(barreraAtaque, bloqueoPelea);
            
            //avisa a las crias
            Cria.llamarAtaque();
        }
    }
    
    //logica del boton de pausa
    public static void cambiaPausa()
    {
        //se rige por un CountDownLatch
        if(!pausa)
        {
            latchPausa = new CountDownLatch(1);
            
            //referencia el latch a las hormigas
            Obrera.setLatch(latchPausa);
            Soldado.setLatch(latchPausa);
            Cria.setLatch(latchPausa);
            
            //pausa la simulacion
            pausa = true;
            
            //mira entre todas la threads de la ejecucion
            for (Thread t : Thread.getAllStackTraces().keySet())
            {
                //pilla las que son hormigas
                if (t.getName().contains("HO") || t.getName().contains("HS") || t.getName().contains("HC"))
                {
                    //System.out.println(t.getName());
                    t.interrupt();
                }
            }
        }
        else
        {
            //despausa
            pausa = false;
            //decrementa el latch para liberarlo
            latchPausa.countDown();
        }
        
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
