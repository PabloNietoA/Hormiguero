/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pablokarin.progav.jframe;

import javax.swing.JTextField;
/**
 *
 * @author Karín
 */
public class TareaActualizar implements Runnable{
    private JTextField campo;
    private String texto;
    
    public TareaActualizar (JTextField campo, String texto)
    {
        this.campo = campo;
        this.texto = texto;
    }
    
    public void run ()
    {
        campo.setText(texto);
    }
}
