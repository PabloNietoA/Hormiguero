/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

import java.util.Random;

/**
 *
 * @author Slend
 */
public class Instruc {
    public static void instruir() throws InterruptedException
    {
           Thread.sleep((new Random().nextInt(6) + 2) * 1000);
    }
}
