/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import static java.lang.Thread.sleep;

/**
 *
 * @author Slend
 */
public class TestMain {
    public static void main(String[] args)
    {
        for (int i=1; i<0; i++)
        {
            System.out.println("Se ha metido tf");
        }
        System.out.println("No pasa na");
        TestHilo thread = new TestHilo();
        thread.start();
        try
        {
            sleep(3000);
        }
        catch(InterruptedException ie)
        {
            
        }
        thread.interrumpir();
    }
}
