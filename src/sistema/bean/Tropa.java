/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.bean;

/**
 *
 * @author bruno
 */
public class Tropa {
    private int id;
    private String nombre;
    private int posicionX;
    private int posicionY;
    private int vida;
    private int ataque;
    private int alcanceMovimiento;
    private int alcanceAtaque;
    private int nivelDesplazamiento;//0->cualquier superficie/1->excepcion agua/2->solo grama,carretera,bosque/3->solo grama,carretera/4->solo carretera

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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
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

    public Tropa(int id) {
        this.id = id;
    }

    public Tropa(int id, String nombre, int posicionX, int posicionY) {
        this.id = id;
        cargarAtributos(nombre);
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }
    
    private void cargarAtributos(String tipo){
        this.nombre = tipo;
        switch(nombre){
            case "infanteria":
                alcanceMovimiento = 2;
                vida = 50;
                ataque = 30;
                alcanceAtaque = 1;
                nivelDesplazamiento = 0;
                break;
            case "infanteriam":
                alcanceMovimiento = 2;
                vida = 50;
                ataque = 50;
                alcanceAtaque = 3;
                nivelDesplazamiento = 1;
                break;
            case "reconocimiento":
                alcanceMovimiento = 6;
                vida = 100;
                ataque = 50;
                alcanceAtaque = 2;
                nivelDesplazamiento = 2;
                break;
            case "tanque":
                alcanceMovimiento = 4;
                vida = 150;
                ataque = 80;
                alcanceAtaque = 2;
                nivelDesplazamiento = 3;
                break;
            case "mtanque":
                alcanceMovimiento = 3;
                vida = 200;
                ataque = 100;
                alcanceAtaque = 2;
                nivelDesplazamiento = 4;
                break;
            case "artilleria":
                alcanceMovimiento = 3;
                vida = 50;
                ataque = 150;
                alcanceAtaque = 6;
                nivelDesplazamiento = 4;
                break;
        }
    }
}
