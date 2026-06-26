/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author giral
 */
public class Vendedor extends Persona {
    private String registroVentas;
    private int experiencia;
    private String rango;

    public Vendedor() {
    }

    public Vendedor(String registroVentas, int experiencia, String rango) {
        this.registroVentas = registroVentas;
        this.experiencia = experiencia;
        this.rango = rango;
    }

    public Vendedor(String registroVentas, int experiencia, String rango, int cedula, String nombre, int telefono) {
        super(cedula, nombre, telefono);
        this.registroVentas = registroVentas;
        this.experiencia = experiencia;
        this.rango = rango;
    }

    public String getRegistroVentas() {
        return registroVentas;
    }

    public void setRegistroVentas(String registroVentas) {
        this.registroVentas = registroVentas;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }
    
    @Override
    public void ejecutarAccion() {
        this.setMensajeFlotante("¡Atendiendo caja!");
    }
}
