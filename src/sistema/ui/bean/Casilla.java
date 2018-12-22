/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.ui.bean;

import javax.swing.JPanel;

/**
 *
 * @author bruno
 */
public class Casilla extends JPanel {
    private int posX, posY;
    private String tipoCasilla;
    private TropaJL tropa;

    public TropaJL getTropa() {
        return tropa;
    }

    public void setTropa(TropaJL tropa) {
        this.tropa = tropa;
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
    }
}
