/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.ui.bean;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author bruno
 */
public class Casilla extends JPanel {
    private int posX, posY;
    private String tipoCasilla;
    private TropaJL tropa;
    private Image image;

    public TropaJL getTropa() {
        return tropa;
    }

    public void setTropa(TropaJL tropa) {
        this.tropa = tropa;
        add(tropa);
    }
    
    public void removeTropa(){
        remove(tropa);
        tropa=null;
    }
    
    public String getTipoCasilla() {
        return tipoCasilla;
    }

    public void setTipoCasilla(String tipoCasilla) {
        this.tipoCasilla = tipoCasilla;
    }
    
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Casilla(int posX, int posY, String tipoCasilla) {
        this.posX = posX;
        this.posY = posY;
        this.tipoCasilla = tipoCasilla;
        switch(tipoCasilla){
            case "agua": 
                try{
                    ImageIcon icon = new ImageIcon(getClass().getResource("../imagenes/mapa/Agua.png"));
                    image = icon.getImage();
                }catch(Exception ex){};
                break;
            case "grama": 
                try{
                    ImageIcon icon = new ImageIcon(getClass().getResource("../imagenes/mapa/Grama.png"));
                    image = icon.getImage();
                }catch(Exception ex){};
                break;            
            case "arbol": 
                try{
                    ImageIcon icon = new ImageIcon(getClass().getResource("../imagenes/mapa/Arbol.png"));
                    image = icon.getImage();
                }catch(Exception ex){};
                break;
            case "carretera": 
                try{
                    ImageIcon icon = new ImageIcon(getClass().getResource("../imagenes/mapa/Carretera.png"));
                    image = icon.getImage();
                }catch(Exception ex){};
                break;
            case "montania": 
                try{
                    ImageIcon icon = new ImageIcon(getClass().getResource("../imagenes/mapa/Montaña.png"));
                    image = icon.getImage();
                }catch(Exception ex){};
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
        if(image != null)
        grphcs.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
