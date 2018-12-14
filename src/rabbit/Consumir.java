/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import javax.swing.Timer;

/**
 *
 * @author bruno
 */
//CLIENTE
public class Consumir extends Thread{
    private final static String COLA = "pruebaHilo"; 
    @Override
    public void run(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(COLA, false, false, false, null);
            System.out.println("Esperando mensajes....");
            
            DeliverCallback deliverCallback = (consumerTag, delivery) ->{
                String msg = new String(delivery.getBody(), "UTF-8");  
                System.out.println("recibido: " + msg);
            };
            
            channel.basicConsume(COLA, true, deliverCallback, consumerTag -> {});
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
