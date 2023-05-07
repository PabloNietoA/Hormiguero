/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Karín
 */
public class TareaEscribir implements Runnable{
    private String nombre;
    private String id;
    private String tipoH; //tipo de hormiga (obrera, soldado o cría)
    private int tipo; //tipo de escritura que se va a realizar
    private Timestamp tiempo;
    private String mensaje; //lo que se escribe en el log
    
    public TareaEscribir(String nombre, int tipo, Timestamp tiempo)
    {
        this.nombre = nombre;
        switch(nombre.charAt(1))
        {
            case 'O':
            {
                tipoH = "Obrera";
                break;
            }
            case 'S':
            {
                tipoH = "Soldado";
                break;
            }
            case 'C':
            {
                tipoH = "Cría";
                break;
            }
               
        }
        this.tipo = tipo;
        this.tiempo = tiempo;
    }
    public void run()
    {
        switch (tipo)
        {
            case 0:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", ha nacido.";
                break;
            }
            case 1:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está comiendo.";
                break;
            }
            case 2:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está descansando.";
                break;
            }
            case 3:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está recogiendo comida.";
                break;
            }
            case 4:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está guardando comida en el almacén.";
                break;
            }
            case 5:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está sacando comida del almacén.";
                break;
            }
            case 6:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está llevando comida del almacén al comedor.";
                break;
            }
            case 7:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está dejando comida en el comedor.";
                break;
            }
            case 8:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está instruyéndose.";
                break;
            }
            case 9:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está defendiendo a la colonia de una amenaza.";
                break;
            }
            case 10:
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está refugiada.";
                break;
            }
            //escribir (mensaje, dirección del fichero .txt a modificar)
        }
    }
    public void toText(String mensaje, String dir){
        StringBuilder sb = new StringBuilder();
        sb.append(mensaje);
        sb.append((System.lineSeparator()));
        mensaje = sb.toString();
        try {
            FileWriter fileWriter = new FileWriter(dir, true); //el primer param es la dirección del archivo a escribir, true es para escribir al final
            //Escribe el string recibo en el documento de texto
            fileWriter.write(mensaje);
            fileWriter.close();
        } 
        catch (IOException e) {System.out.println("No se pudo escribir en el archivo. Error de I/0: " + e);}
        
    }
    
    
}
