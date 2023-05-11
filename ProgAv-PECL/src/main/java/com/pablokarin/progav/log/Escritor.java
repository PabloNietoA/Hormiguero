package com.pablokarin.progav.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Escritor {
    //se crea el pool de un solo hilo que gestiona la escritura en log
    public static final ExecutorService logger = Executors.newSingleThreadExecutor();
    
}
