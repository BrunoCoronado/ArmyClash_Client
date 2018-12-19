/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.archivos;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author bruno
 */
public class Archivo {
    public void cargarTropas(String path, int jugador){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String contenido = "";
            String linea = reader.readLine();
            while(linea != null){
                contenido += linea+"\n";
                linea = reader.readLine();
            }
            System.out.println(contenido);
            if(jugador == 1)
                main.Main.emisor.enviarInformacion(contenido+">1");
            else
                main.Main.emisor.enviarInformacion(contenido+">2");
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
