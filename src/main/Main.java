/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Scanner;
import rabbit.Emisor;
import rabbit.Receptor;

/**
 *
 * @author bruno
 */
//CLIENTE
public class Main {
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Receptor receptor = new Receptor();
        receptor.start();
        
        Emisor emisor = new Emisor();
        Scanner scanner = new Scanner(System.in);
        for(int i=0;i<30;i++){
            System.out.println("peticion");
            emisor.enviarPeticion(scanner.nextLine());
        }
    }
}
