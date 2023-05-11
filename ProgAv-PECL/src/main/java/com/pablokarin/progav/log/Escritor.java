/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Karín
 */
public class Escritor {
    //se crea el pool de un solo hilo que gestiona la escritura en log
    public static final ExecutorService logger = Executors.newSingleThreadExecutor();
    
}
