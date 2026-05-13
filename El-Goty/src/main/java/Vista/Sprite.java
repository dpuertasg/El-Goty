/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author USER
 */
public class Sprite {
    private final int lado; //dado que sera cuadrado todos sus lados seran iguales
    private int x;
    private int y; 
    public int[] pixeles;
    private final HojaSprites hoja;
    public Sprite(final int lado,final int columna, final int fila, final HojaSprites hoja){
        this.lado = lado;
        pixeles = new int[lado * lado];
        this.x = columna * lado;
        this.y = fila * lado;
        this.hoja = hoja;
        for(int y = 0; y < lado; y++){
            for(int x = 0;  x < lado; x++){
                pixeles[(x+y) * lado] = hoja.pixeles[((x + this.x) + (y + this.y)) * hoja.getAncho()];
            }
        }
    }
}
