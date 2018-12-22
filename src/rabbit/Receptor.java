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
import static sistema.ui.VentanaCliente.txtLog;

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
                                txtLog.setText(txtLog.getText() + "-->>> Obteniendo Mapa del servidor...\n");
                                main.Main.ventanaCliente.cargarTablero(datosMapa[2], Integer.parseInt(datosMapa[0]), Integer.parseInt(datosMapa[1]));
                                break;
                            case "t":
                                txtLog.setText(txtLog.getText() + "-->>> Obteniendo Tropas del servidor...\n");
                                main.Main.ventanaCliente.cargarTropas(partesCuerpoMensaje[0]);
                                break;
                            default:
                                txtLog.setText(txtLog.getText() + "-->>> Respuesta no manejada...\n");
                                System.out.println("Destinatario: "+partesCuerpoMensaje[1]+" Mensaje: "+partesCuerpoMensaje[0]);
                                break;
                        }
                    }
                }else
                    txtLog.setText(txtLog.getText() + "-->>> No se obtuvo respuesta del servidor...\n");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            txtLog.setText(txtLog.getText() + "-->>> Error al manejar Respuesta del Servidor...\n");
        }
    }
}
