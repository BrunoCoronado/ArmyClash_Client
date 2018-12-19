/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author bruno
 */
public class Emisor {
    
    public void enviarPeticion(String peticion){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("ArmyClash-Peticiones", false, false, false, null);
            channel.basicPublish("", "ArmyClash-Peticiones", null, peticion.getBytes());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void enviarInformacion(String peticion){
        peticion += "<0";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("ArmyClash-Peticiones", false, false, false, null);
            channel.basicPublish("", "ArmyClash-Peticiones", null, peticion.getBytes());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
