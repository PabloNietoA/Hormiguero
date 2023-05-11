package com.pablokarin.progav.part1.hilos;

import com.pablokarin.progav.log.Escritor;
import com.pablokarin.progav.log.TareaEscribir;
import com.pablokarin.progav.part1.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Soldado implements Hormiga {
    private final int id;
    private int iteracion;
    private static CyclicBarrier barrera;
    private static CountDownLatch latch;
    private String nombre;
    private static final ArrayList<Soldado> listaSoldados = new ArrayList();
    
    public Soldado(int id)
    {
        //se añade a la lista de soldados estatica
        listaSoldados.add(this);
        
        //genera el nombre de la hormiga
        this.id = id;
               if (id < 10)
        {
            nombre = "HS000" + id;
        }
        else
        {
            if (id < 100)
            {
                nombre = "HS00" + id;
            }
            else
            {    
                if (id<1000)
                {
                    nombre = "HS0" + id;
                }
                else
                {
                    if (id<10000)
                    {
                        nombre = "HS" + id;
                    }
                }
            }
        }
    }
    
    @Override
    public String getNombre()
    {
        return nombre;
    }
    
    @Override
    public void run()
    {
        //pone nombre de la hormiga al hilo
        Thread.currentThread().setName(nombre);
        
        //actualiza las hormigas vivas
        Hormiguero.setNHormigasVivas(Hormiguero.getNHormigasVivas()+1);
        
        //logger y timestamps
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 0, timestamp1);
        Escritor.logger.execute(entrada1);
        
        //entra en el hormiguero por primera vez
        try 
        {
            Hormiguero.entrar();
        }
        catch (InterruptedException ex) 
        {
            interrumpido();
        }
        
        //bucle principal de comportamiento
        while (true)
        {
            //va a comer cada 6 iteraciones de comportamiento
            if (iteracion %6 == 0 && iteracion !=0)
            {
                //va a comer
                try
                {
                    
                    Hormiguero.getComer().add(this);
                    Comedor.comer(1, 3);
                    Hormiguero.getComer().remove(this);
                }
                catch(InterruptedException IE)
                {
                    //gestion de listas
                    boolean pausado = false;
                    if (!Hormiguero.isPausa())
                    {
                        Hormiguero.getComer().remove(this);
                    }    
                    else 
                    {
                        pausado = true;
                    }
                    
                    //interrupcion
                    interrumpido();
                    
                    if (pausado) Hormiguero.getComer().remove(this);
                }
            }
            //comportamiento del resto de iteraciones
            else
            {
                //entrena
                try
                {
                    Hormiguero.getInstruc().add(this);
                    Instruc.instruir();
                    Hormiguero.getInstruc().remove(this);
                }
                catch(InterruptedException IE)
                {
                    //gestion de listas
                    boolean pausado = false;
                    if (!Hormiguero.isPausa())
                    {
                        Hormiguero.getInstruc().remove(this);
                    }
                    else 
                    {
                        pausado = true;
                    }

                    //interrupcion
                    interrumpido();
                    
                    if (pausado) Hormiguero.getInstruc().remove(this);
                }

                //descansa 2 segundos
                try
                {
                    Hormiguero.getDescanso().add(this);
                    Descanso.descansar(2);
                    Hormiguero.getDescanso().remove(this);
                }
                catch(InterruptedException IE)
                {
                    //gestion de listas
                    boolean pausado = false;
                    if (!Hormiguero.isPausa())
                    {
                        Hormiguero.getDescanso().remove(this);
                    }
                    else 
                    {
                        pausado = true;
                    }

                    //interrupcion
                    interrumpido();
                    
                    if (pausado) Hormiguero.getDescanso().remove(this);
                        
                }
            }
            iteracion++;
        }
    }
    
    //gestor de interrupciones
    public void interrumpido()
    {
        //en caso de que sea pausa
        if(Hormiguero.isPausa())
        {
            try
            {
                //entra al countdownlatch de pausa
                latch.await();
            }
            catch (InterruptedException IE)
            {
                //por si acaso
                interrumpido();
            }
        }
        //en caso de que sea una amenaza
        else
        {
            Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
            TareaEscribir entrada1 = new TareaEscribir(Thread.currentThread().getName(), 10, timestamp1);
            Escritor.logger.execute(entrada1);
            
            //sale a defender
            try 
            {
                Hormiguero.salir();
            } 
            catch (InterruptedException ex) 
            {
                //por si acaso
                interrumpido();
            }
            Hormiguero.getDefendiendo().add(this);
            
            //entra en la cyclicbarrier de espera
            try
            {
                barrera.await();
            }
            catch(Exception e)
            {
                //por si se pausa a mitad
                interrumpido();
            }

            //entra a la pelea (CountdownLatch)
            try
            {
                latch.await();
            }
            catch (InterruptedException IE)
            {
                //por si se pausa a mitad
                interrumpido();
            }

            //vuelve a entrar en el hormiguero
            Hormiguero.getDefendiendo().remove(this);
            try{ 
                Hormiguero.entrar();
            }
            catch(InterruptedException e)
            {
               interrumpido();
            }
        }
    }

    //se llama desde el hormiguero cada vez que hay un ataque
    public static void llamarAtaque(CyclicBarrier b, CountDownLatch l)
    {
        barrera = b;
        latch = l;
        
        //interrumpe a todos los soldados
        for(int i = 0; i < listaSoldados.size(); i++)
        {
            String nombre = listaSoldados.get(i).getNombre();
            //busca en los hilos activos
            for (Thread t : Thread.getAllStackTraces().keySet())
            {
                //mira si tienen el mismo nombre y lo interrumpe
                if(t.getName().equals(nombre)) t.interrupt();
            }
        }
    }

    public static void setLatch(CountDownLatch latch) {
        Soldado.latch = latch;
    }
    
    
}
