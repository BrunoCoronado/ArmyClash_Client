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

/**
 *
 * @author bruno
 */
public class Receptor extends Thread{

    @Override
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            Connection conexion = factory.newConnection();
            Channel canal = conexion.createChannel();
            canal.queueDeclare("ArmyClash-Respuestas", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) ->{
                //aca se captura la respuesta
                String msg = new String(delivery.getBody(), "UTF-8");  
                manejarRespuesta(msg);
            };
            canal.basicConsume("", true, deliverCallback, consumerTag -> {});
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void manejarRespuesta(String respuesta){
        try{
            if(respuesta != null){
                if(respuesta.contains("<0")){//estamos trabajando una entrada de informacion
                    String[] cuerpoMensaje = respuesta.split("<");
                    if(cuerpoMensaje[0].contains(">")){
                        String[] partesCuerpoMensaje = cuerpoMensaje[0].split(">");                    
                        switch(partesCuerpoMensaje[1]){
                            case "m":
                                String[] datosMapa = partesCuerpoMensaje[0].split("#");
                                main.Main.ventanaCliente.cargarTablero(datosMapa[2], Integer.parseInt(datosMapa[0]), Integer.parseInt(datosMapa[1]));
                                break;
                            default:
                                System.out.println("Destinatario: "+partesCuerpoMensaje[1]+" Mensaje: "+partesCuerpoMensaje[0]);
                        }
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("error al recibir mensaje\n"+respuesta);
        }
    }
}
