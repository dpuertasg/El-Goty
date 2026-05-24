/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author giral
 */
public class Comprador extends Persona{
    private List<Producto> carritoCompras;
    private String tipoCliente;
    private String paciencia;
    
    private Random aleatorio;
    private int direccionX = 0;
    private int direccionY = 0;
    private int tiempoCaminando = 0;

    public Comprador(int x, int y) {
        super(x, y); // Le pasa las coordenadas a la clase Persona para que las guarde
        this.aleatorio = new Random();
    }

    public Comprador(String tipoCliente, String paciencia) {
        this.carritoCompras = new ArrayList<>();
        this.tipoCliente = tipoCliente;
        this.paciencia = paciencia;
        this.aleatorio = new Random();
    }

    public Comprador(String tipoCliente, String paciencia, int cedula, String nombre, int telefono) {
        super(cedula, nombre, telefono);
        this.carritoCompras = new ArrayList<>();
        this.tipoCliente = tipoCliente;
        this.paciencia = paciencia;
        this.aleatorio = new Random();
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
    
    public void actualizarIA() {
        tiempoCaminando--;

            if (tiempoCaminando <= 0) {
                // Elige dirección aleatoria (-1, 0 o 1)
                direccionX = aleatorio.nextInt(3) - 1; //nuero aleatorio entre 0 y 2, luego se le resta 1 para que sea -1,0 o 2
                direccionY = aleatorio.nextInt(3) - 1;
                // Elige tiempo aleatorio entre 60 y 120 ciclos
                tiempoCaminando = aleatorio.nextInt(60) + 60; //tiempo aleatorio antes de tomar una decicion (60 ciclos es 1 segundo) 
            }
    
        int velocidad = 1;
        int dx = direccionX * velocidad;
        int dy = direccionY * velocidad;

        // Ejecuta el método Moverse que heredó de Persona
        this.Moverse(dx, dy); 
    }
   
}
