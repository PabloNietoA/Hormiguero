/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.part1;

/**
 *
 * @author Slend
 */
public class Bicho {
    private static boolean amenaza = false;
            
    public static boolean getAmenaza()
    {
        return amenaza;
    }
    
    public static void amenazar()
    {
        Hormiguero.ataque();
    }
}
