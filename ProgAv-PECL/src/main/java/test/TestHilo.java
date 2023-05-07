/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author Slend
 */
public class TestHilo extends Thread {
    
    public void run()
    {
        while(true)
        {
            try
            {
            System.out.println("Comienza");
            sleep(2000);
            System.out.println("Termina");
            }
            catch(InterruptedException ie)
            {
                
            }
        }
    }
    
    public void interrumpir()
    {
        System.out.println("Interrumpido");
        try
        {    
        sleep(5000);
        }
        catch(InterruptedException ie)
        {
            
        }
        System.out.println("FinInterrumpido");
    }
    
}
