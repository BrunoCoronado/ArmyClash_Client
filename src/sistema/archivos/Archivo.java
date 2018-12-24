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
                if(linea.contains("\uFEFF")){
                    linea = linea.replaceAll("\uFEFF", "");
                }
                contenido += linea+"\n";
                linea = reader.readLine();
            }
            if(jugador == 1)
                main.Main.emisor.enviarPeticion(contenido+">t1<0");
            else
                main.Main.emisor.enviarPeticion(contenido+">t2<0");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
