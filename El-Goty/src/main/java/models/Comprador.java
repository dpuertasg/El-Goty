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
    
    //actualizacion para colisiones con objetos (listaMuebles)
    public void actualizarIA(Obstaculo[] listaMuebles) {
        tiempoCaminando--;

            if (tiempoCaminando <= 0) {
                // Elige dirección aleatoria (-1, 0 o 1)
                direccionX = aleatorio.nextInt(3) - 1; //nuero aleatorio entre 0 y 2, luego se le resta 1 para que sea -1,0 o 2
                direccionY = aleatorio.nextInt(3) - 1;
                // Elige tiempo aleatorio entre 60 y 120 ciclos
                tiempoCaminando = aleatorio.nextInt(60) + 60; //tiempo aleatorio antes de tomar una decicion (60 ciclos es 1 segundo) 
            }
    
        int velocidad = 2;
        int dx = direccionX * velocidad;
        int dy = direccionY * velocidad;
        
        //para calcular que el npc comprador no se salga del borde del mapa
        int proximaX = this.getX() + dx;//posicion futura de x
        int proximaY = this.getY() + dy;//posicion futura de y

        //detectamos colision
        if(this.colisionaConMuebles(proximaX, proximaY, listaMuebles)){
            dy = 0;
            dx = 0;
        }
        
        //si se sale por el borde izquierdo (80) o por el derecho (1275)
        if(proximaX <= 80 || proximaX >= 1275){ //los limites de la tienda
            dx = 0; //detenemos el movimiento
            tiempoCaminando = 0;//obligamos a pensar una nueva ruta
        }
        //lo mismo si se sale por arriba (70) o por abajo (870)
        if(proximaY <= 70 || proximaY > 870){
            dy = 0;
            tiempoCaminando = 0;
        }
        
        // Ejecuta el método Moverse que heredó de Persona
        this.Moverse(dx, dy); 
    }
    
    public String convertirAJson() {
        return "{\n" +
           "  \"cedula\": " + this.getCedula() + ",\n" +
           "  \"nombre\": \"" + this.getNombre() + "\",\n" +
           "  \"telefono\": " + this.getTelefono() + ",\n" +
           "  \"tipoCliente\": \"" + this.getTipoCliente() + "\",\n" +
           "  \"paciencia\": \"" + this.getPaciencia() + "\",\n" +
           "  \"posicionX\": " + this.getX() + ",\n" +
           "  \"posicionY\": " + this.getY() + "\n" +
           "}";
    }
   
}
