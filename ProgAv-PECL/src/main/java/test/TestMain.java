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
