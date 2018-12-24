/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.ui.bean;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author bruno
 */
public class TropaJL extends JLabel{
    private int id;
    private String nombre;
    private int posicionX;
    private int posicionY;
    private double vida;
    private int vidaTotal;
    private int ataque;
    private int alcanceMovimiento;
    private int alcanceAtaque;
    private int nivelDesplazamiento;//0->cualquier superficie/1->excepcion agua/2->solo grama,carretera,bosque/3->solo grama,carretera/4->solo carretera
    private int jugador;
    private boolean movido = false;
    private double bonus;

    public int getVidaTotal() {
        return vidaTotal;
    }

    public void setVidaTotal(int vidaTotal) {
        this.vidaTotal = vidaTotal;
    }
    
    public double getBonus() {
        return bonus;
    }

    public void setBonus(String tipoCasilla) {
        cargarBonus(tipoCasilla);
    }

    public boolean isMovido() {
        return movido;
    }

    public void setMovido(boolean movido) {
        this.movido = movido;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public double getVida() {
        return vida;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getAlcanceMovimiento() {
        return alcanceMovimiento;
    }

    public void setAlcanceMovimiento(int alcanceMovimiento) {
        this.alcanceMovimiento = alcanceMovimiento;
    }

    public int getAlcanceAtaque() {
        return alcanceAtaque;
    }

    public void setAlcanceAtaque(int alcanceAtaque) {
        this.alcanceAtaque = alcanceAtaque;
    }

    public int getNivelMovimiento() {
        return nivelDesplazamiento;
    }

    public void setNivelMovimiento(int nivelDesplazamiento) {
        this.nivelDesplazamiento = nivelDesplazamiento;
    }

    public int getNivelDesplazamiento() {
        return nivelDesplazamiento;
    }

    public void setNivelDesplazamiento(int nivelDesplazamiento) {
        this.nivelDesplazamiento = nivelDesplazamiento;
    }

    public int getJugador() {
        return jugador;
    }

    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    public TropaJL(int id) {
        this.id = id;
    }
    
    public TropaJL() {}

    public TropaJL(int id, String nombre, int posicionX, int posicionY, int jugador, String tipoCasilla) {
        this.id = id;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.jugador = jugador;
        cargarBonus(tipoCasilla);
        cargarAtributos(nombre);
    }
    
    private void cargarBonus(String casilla){
        switch(casilla){
            case "agua": bonus = -0.05;
                break;
            case "grama": bonus = 0.1;
                break;
            case "arbol": bonus = -0.1;
                break;
            case "carretera": bonus = 0.0;
                break;
            case "montania": bonus = 0.25;
                break;
            default: bonus = 0.0;
                break;
        }
    }
    
    private void cargarAtributos(String tipo){
        this.nombre = tipo;
        switch(nombre){
            case "infanteria":
                alcanceMovimiento = 3;
                vida = 50;
                vidaTotal = 50;
                ataque = 30;
                alcanceAtaque = 1;
                nivelDesplazamiento = 0;
                if(jugador == 1)
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador1/InfanteriaJ1.png")));
                else
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador2/InfanteriaJ2.png")));
                break;
            case "infanteriam":
                alcanceMovimiento = 2;
                vida = 50;
                vidaTotal = 50;
                ataque = 50;
                alcanceAtaque = 3;
                nivelDesplazamiento = 1;
                if(jugador == 1)
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador1/InfanteriaMecanizadaJ1.png")));
                else
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador2/InfanteriaMecanizadaJ2.png")));
                break;
            case "reconocimiento":
                alcanceMovimiento = 6;
                vida = 100;
                vidaTotal = 100;
                ataque = 50;
                alcanceAtaque = 2;
                nivelDesplazamiento = 2;
                if(jugador == 1)
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador1/ReconocimientoJ1.png")));
                else
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador2/ReconocimientoJ2.png")));
                break;
            case "tanque":
                alcanceMovimiento = 4;
                vida = 150;
                vidaTotal = 150;
                ataque = 80;
                alcanceAtaque = 2;
                nivelDesplazamiento = 3;
                if(jugador == 1)
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador1/TanqueJ1.png")));
                else
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador2/TanqueJ2.png")));
                break;
            case "mtanque":
                alcanceMovimiento = 3;
                vida = 200;
                ataque = 100;
                alcanceAtaque = 2;
                nivelDesplazamiento = 4;
                if(jugador == 1)
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador1/MegaTanqueJ1.png")));
                else
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador2/MegaTanqueJ2.png")));
                break;
            case "artilleria":
                alcanceMovimiento = 3;
                vida = 50;
                vidaTotal = 50;
                ataque = 150;
                alcanceAtaque = 6;
                nivelDesplazamiento = 4;
                if(jugador == 1)
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador1/ArtilleriaJ1.png")));
                else
                    this.setIcon(new ImageIcon(getClass().getResource("../imagenes/jugador2/ArtilleriaJ2.png")));
                break;
        }
    }
}
