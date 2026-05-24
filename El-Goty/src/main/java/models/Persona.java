/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author giral
 */
public class Persona {
    private int cedula;
    private String nombre;
    private int telefono;
    
    private int x, y; //para poder move a los personajes
    //variables para la animacion
    private int contadorAnimacion = 0;
    private boolean enMovimiento = false;
    
    public Persona(){
    }
    
    public Persona(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Persona(int cedula, String nombre, int telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    // metodo de movimiento para el vendedor y comprador
    public void Moverse(int velocidadX, int velocidadY){
        this.x += velocidadX;
        this.y += velocidadY;
        //detectar si el personaje se esta moviendo
        if (velocidadX != 0 || velocidadY != 0) {
            this.enMovimiento = true;
        }
    }
    
    public void actualizarAnimacion() {
        if (enMovimiento) {
            contadorAnimacion++;
                // Como son 6 fotogramas y queremos que cambie cada 6 ticks, el ciclo total dura 36
                if (contadorAnimacion >= 36) {
                    contadorAnimacion = 0;
                }
        } else {
            contadorAnimacion = 0; // Si se detiene, vuelve al fotograma base (quieto)
        }
        // Reseteamos para el próximo tick
        enMovimiento = false;
    }
    public int getContadorAnimacion() {
        return contadorAnimacion;
    }

    public boolean esEnMovimiento() {
        return enMovimiento;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
     
}
