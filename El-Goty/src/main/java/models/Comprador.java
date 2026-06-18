/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

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
    private int objetivoCompras;
    private boolean irACaja = false;
    private boolean visible = false;
    private long tiempoAparicion;
    private int puntoActual = 0;
    private long retrasoAparicion;
    private int offsetX;
    private int offsetY;
    

    private static final int CAJA_X = 276;
    private static final int CAJA_Y = 740;

    private Point[] ruta = {
    
        new Point(180,250),
        new Point(450,250),
        new Point(850,250),
        new Point(850,560),
        new Point(1150,560),
        new Point(670,560),
        new Point(450,560),
        new Point(276 + offsetX,740 + offsetY)
    
    };

    public Comprador(int x, int y) {
        super(x, y); // Le pasa las coordenadas a la clase Persona para que las guarde
        this.aleatorio = new Random();
        this.offsetX = aleatorio.nextInt(80) - 40;
        this.offsetY = aleatorio.nextInt(150) - 40;
        this.carritoCompras = new ArrayList<>();
        this.objetivoCompras = aleatorio.nextInt(5) + 1;
        this.tiempoAparicion = System.currentTimeMillis();
        this.retrasoAparicion = aleatorio.nextInt(8000);
    }

    public Comprador(String tipoCliente, String paciencia) {
        this.carritoCompras = new ArrayList<>();
        this.tipoCliente = tipoCliente;
        this.paciencia = paciencia;
        this.aleatorio = new Random();
        this.offsetX = aleatorio.nextInt(80) - 40;
        this.offsetY = aleatorio.nextInt(150) - 40;
        this.objetivoCompras = aleatorio.nextInt(5) + 1;    
        this.tiempoAparicion = System.currentTimeMillis();
        this.retrasoAparicion = aleatorio.nextInt(8000);
    }

    public Comprador(String tipoCliente, String paciencia, int cedula, String nombre, int telefono) {
        super(cedula, nombre, telefono);
        this.carritoCompras = new ArrayList<>();
        this.tipoCliente = tipoCliente;
        this.paciencia = paciencia;
        this.aleatorio = new Random();
        this.offsetX = aleatorio.nextInt(80) - 40;
        this.offsetY = aleatorio.nextInt(150) - 40;
        this.objetivoCompras = aleatorio.nextInt(5) + 1;
        this.tiempoAparicion = System.currentTimeMillis();
        this.retrasoAparicion = aleatorio.nextInt(8000);
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
    
    public boolean isVisible() {
    return visible;
}
    
    private Producto generarProductoAleatorio() {

    int opcion = aleatorio.nextInt(7);

    switch(opcion){

        case 0:
            return new Producto(
                1,
                "Jabon",
                3500,
                2500,
                new Categoria(1,"Aseo")
            );

        case 1:
            return new Producto(
                2,
                "Pastas",
                2500,
                1800,
                new Categoria(2,"Alimentos")
            );

        case 2:
            return new Producto(
                3,
                "Agua",
                1800,
                1200,
                new Categoria(3,"Bebidas")
            );

        case 3:
            return new Producto(
                4,
                "Cafe Instantaneo",
                12000,
                9000,
                new Categoria(3,"Bebidas")
            );

        case 4:
            return new Producto(
                5,
                "Sopa Instantanea",
                2200,
                1500,
                new Categoria(2,"Alimentos")
            );

        case 5:
            return new Producto(
                6,
                "Cloro",
                5500,
                4000,
                new Categoria(4,"Limpieza")
            );

        default:
            return new Producto(
                7,
                "Ambientador",
                8000,
                6000,
                new Categoria(4,"Limpieza")
            );
    }
}
    
    //actualizacion para colisiones con objetos (listaMuebles)
    public void actualizarIA(Obstaculo[] listaMuebles) {
        if(!visible){

            if(System.currentTimeMillis() - tiempoAparicion >= 5000 + retrasoAparicion){
                visible = true;
            }

            return;
        }
        
        if(!irACaja){
            Point destino = ruta[puntoActual];

            moverHacia(destino.x, destino.y,listaMuebles);

            if(Math.abs(getX() - destino.x) < 10 &&Math.abs(getY() - destino.y) < 10){
                Producto producto =generarProductoAleatorio();
                carritoCompras.add(producto);
                
                System.out.println(getNombre()+ " tomó "+ producto.getNombre());
                puntoActual++;

                if(puntoActual >= ruta.length){
                    irACaja = true;
                }
            }

            return;
        }
        
        tiempoCaminando--;   
        
        Point caja = new Point(CAJA_X, CAJA_Y);

        moverHacia(caja.x + offsetX,caja.y + offsetY,listaMuebles);
        return;
    }
    
    private void moverHacia(int destinoX, int destinoY,Obstaculo[]muebles){
        int dx = 0;
        int dy = 0;
        int velocidad = 2;
        

        if(getX() < destinoX)
            dx = velocidad;
        else if(getX() > destinoX)
            dx = -velocidad;

        if(getY() < destinoY)
            dy = velocidad;
        else if(getY() > destinoY)
            dy = -velocidad;

        int proximaX = getX() + dx;
        int proximaY = getY() + dy;

        if(colisionaConMuebles(proximaX,proximaY,muebles)){
        return;
        }

        Moverse(dx,dy);
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
