/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author giral
 */
public class Categoria {
    private String nombre;
    private int id;
    private List<Producto> productos;

    public Categoria() {
    }
    
    
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.productos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    public void crearCategoria(){
        
        
    }
    
    public void agregarProductoCategoria (Producto p){
        this.productos.add(p);
    }
    public List<Producto> obtenerProductosPorCategoria(){
        return this.productos;
    }
   // regla de negocio "eliminarCategoria"
    
    public void eliminarCategoria(){
    // falta un if y el codigo para eliminar la categoria
    }
   
}
