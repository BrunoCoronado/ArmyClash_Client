/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Scanner;
import rabbit.Emisor;
import rabbit.Receptor;
import sistema.ui.VentanaCliente;

/**
 *
 * @author bruno
 */
//CLIENTE
public class Main {
    /**
     * @param args the command line arguments
     */
    public static Emisor emisor = new Emisor();
    public static VentanaCliente ventanaCliente = new VentanaCliente(); 
    public static void main(String[] args) {
        Receptor receptor = new Receptor();
        receptor.start();
        ventanaCliente.setVisible(true);
    }
}
