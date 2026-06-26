/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author giral
 */
public abstract class Persona {
    private int cedula;
    private String nombre;
    private int telefono;
    private String mensajeFlotante = "";
    
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
    public String getMensajeFlotante() {
    return this.mensajeFlotante;
    }

    public void setMensajeFlotante(String mensaje) {
    this.mensajeFlotante = mensaje;
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
    
    //detectar si hay colision entredos personas
    public boolean colisionaCon(Persona otraPersona) {
        //definimos el tamaño real en pantalla de los personajes (escala 3 * 32 = 96)
        int tamaño = 96; 
        
        //rectangulo del personaje actual (A)
        int xA = this.x;
        int yA = this.y;
        
        //rectangulo de la otra persona (B)
        int xB = otraPersona.getX();
        int yB = otraPersona.getY();
        
        //comprobamos si los rectangulos se superponen en los ejes X e Y
        boolean colisionEnX = (xA < xB + tamaño) && (xA + tamaño > xB);
        boolean colisionEnY = (yA < yB + tamaño) && (yA + tamaño > yB);
        
        //si hay superposicion en ambos ejes, hay colision
        return colisionEnX && colisionEnY;
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
    
    public boolean colisionaConMuebles(int proximaX, int proximaY, Obstaculo[] listaMuebles) {
        int tamañoPersonaje = 96; //tamaño en pantalla por la escala 3
        
        for (Obstaculo mueble : listaMuebles) {//el ciclo recorre todos los objetos y uno por uno comprueba si hay colision
        
            //si colisoina en x
            boolean colisionX = (proximaX < mueble.getX() + mueble.getAncho()) && 
                            (proximaX + tamañoPersonaje > mueble.getX());
            //si colisiona en y                    
            boolean colisionY = (proximaY < mueble.getY() + mueble.getAlto()) && 
                            (proximaY + tamañoPersonaje > mueble.getY());
        
            if (colisionX && colisionY) {
                return true; // choco con este mueble en particular
            }
        }
        return false; // el camino esta limpio
    }
     public abstract void ejecutarAccion();
}
