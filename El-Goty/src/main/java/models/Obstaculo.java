/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author User
 */
public class Obstaculo {
    private int x, y, ancho, alto;

    public Obstaculo(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }
    
    public int getX() { 
        return x; 
    }
    public int getY() { 
        return y; 
    }
    public int getAncho() { 
        return ancho; 
    }
    public int getAlto() { 
        return alto; 
    }
}
