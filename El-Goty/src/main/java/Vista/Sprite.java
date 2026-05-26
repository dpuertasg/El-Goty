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
    private final int lado; 
    private int x;
    private int y; 
    public int[] pixeles;
    private final HojaSprites hoja;
    
    public static Sprite asfalto = new Sprite(32, 0, 0, HojaSprites.tienda);
    
    //se recorta el png de la animacion y se guardan las partes cortadas en un array (cada parte de 32 por 32);
    public static Sprite[] monstruoCaminando = new Sprite[] {
        new Sprite(32, 0, 0, HojaSprites.hojaMonstruo), //fotograma 1
        new Sprite(32, 1, 0, HojaSprites.hojaMonstruo), //fotograma 2
        new Sprite(32, 2, 0, HojaSprites.hojaMonstruo), //fotograma 3
        new Sprite(32, 3, 0, HojaSprites.hojaMonstruo), //fotograma 4
        new Sprite(32, 4, 0, HojaSprites.hojaMonstruo), //fotograma 5
        new Sprite(32, 5, 0, HojaSprites.hojaMonstruo)  //fotograma 6
    };
    //recortar el comprador 
    public static Sprite[] compradorCaminando = new Sprite[] {
        new Sprite(32, 0, 0, HojaSprites.hojaComprador), 
        new Sprite(32, 1, 0, HojaSprites.hojaComprador), 
        new Sprite(32, 2, 0, HojaSprites.hojaComprador), 
        new Sprite(32, 3, 0, HojaSprites.hojaComprador), 
        new Sprite(32, 4, 0, HojaSprites.hojaComprador), 
        new Sprite(32, 5, 0, HojaSprites.hojaComprador)  
    };
    
    public Sprite(final int lado, final int columna, final int fila, final HojaSprites hoja){
        this.lado = lado;
        this.pixeles = new int[lado * lado];
        this.x = columna * lado;
        this.y = fila * lado;
        this.hoja = hoja;
        
        // Sin desbordes de Array
        for(int y = 0; y < lado; y++){
            for(int x = 0; x < lado; x++){
                // Posición exacta en el array unidimensional del Sprite pequeñito
                int pixelSprite = x + y * lado; 
                
                // Posición exacta calculando la fila en la Hoja de Sprites grande
                int pixelHoja = (x + this.x) + (y + this.y) * hoja.getAncho();
                
                // Controlamos que no lea basura si la fórmula llega a tocar el borde
                if (pixelHoja < hoja.pixeles.length) {
                    pixeles[pixelSprite] = hoja.pixeles[pixelHoja];
                }
            }
        }
    }

    public int getLado() {
        return lado;
    }
    
}
