/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author giral
 */
public class Comprador extends Persona{
    private List<Producto> carritoCompras;
    private String tipoCliente;
    private String paciencia;

    public Comprador() {
    }

    public Comprador(String tipoCliente, String paciencia) {
        this.carritoCompras = new ArrayList<>();
        this.tipoCliente = tipoCliente;
        this.paciencia = paciencia;
    }

    public Comprador(String tipoCliente, String paciencia, int cedula, String nombre, int telefono) {
        super(cedula, nombre, telefono);
        this.carritoCompras = new ArrayList<>();
        this.tipoCliente = tipoCliente;
        this.paciencia = paciencia;
    }

    public List<Producto> getCarritoCompras() {
        return carritoCompras;
    }

    public void setCarritoCompras(List<Producto> carritoCompras) {
        this.carritoCompras = carritoCompras;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getPaciencia() {
        return paciencia;
    }

    public void setPaciencia(String paciencia) {
        this.paciencia = paciencia;
    }
    
    
    public void anadirCarrito(Producto p){
        
        this.carritoCompras.add(p);
        
    }
   
}
