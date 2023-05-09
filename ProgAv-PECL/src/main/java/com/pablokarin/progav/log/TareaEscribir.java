/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.log;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

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
            case 0://nacer
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", ha nacido.";
                break;
            }
            case 1: //comer
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está comiendo.";
                break;
            }
            case 2://Descansar
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está descansando.";
                break;
            }
            case 3: //Recoger comida
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está recogiendo comida.";
                break;
            }
            case 4://Guardar comida
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está guardando comida en el almacén.";
                break;
            }
            case 5://Sacar comida
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está sacando comida del almacén.";
                break;
            }
            case 6: //Llevar comida
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está llevando comida del almacén al comedor.";
                break;
            }
            case 7://Dejar comida
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está dejando comida en el comedor.";
                break;
            }
            case 8://Instruirse
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está instruyéndose.";
                break;
            }
            case 9://Defender
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", va a defender a la colonia de una amenaza.";
                break;
            }
            case 10://Refugiada
            {
                mensaje = tiempo + " : la hormiga " + tipoH + ", " + nombre + ", está refugiada.";
                break;
            }

        }
        toText(mensaje);
    }
    
    public void toText(String mensaje)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(mensaje);
        sb.append((System.lineSeparator()));
        mensaje = sb.toString();
        try 
        {
            FileWriter fileWriter = new FileWriter("src/main/java/com/pablokarin/progav/log/evolucionColonia.txt", true);
            //Escribe el string mensaje en el documento de texto
            fileWriter.write(mensaje);
            fileWriter.close();
        } 
        catch (IOException e) {System.out.println("No se pudo escribir en el archivo. Error de I/0: " + e);}
        
    }
    
    
}
