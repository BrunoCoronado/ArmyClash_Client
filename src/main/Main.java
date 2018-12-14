/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import rabbit.Consumir;
import rabbit.c;
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
        c c =  new c();
        c.iniciar();
    }
}
