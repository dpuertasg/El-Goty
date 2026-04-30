/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author giral
 */
public class Producto {
   private int id;
   private String nombre;
   private float precioVenta;
   private float PrecioCompra;
   private Categoria categoria;
    public Producto() {
    }

    public Producto(int id, String nombre, float precioVenta, float PrecioCompra, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.PrecioCompra = PrecioCompra;
        this.categoria = categoria;
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

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioCompra() {
        return PrecioCompra;
    }

    public void setPrecioCompra(float PrecioCompra) {
        this.PrecioCompra = PrecioCompra;
    }
    public Categoria getCategoria(){
        return categoria;
        
    }
    public void crearProducto(){
   
   
    }
    
    public void actualizarPrecioVenta(){
        
        
    } 
}

