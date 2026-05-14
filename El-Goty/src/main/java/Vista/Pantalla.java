/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author USER
 */
public class Pantalla {
    private final int ancho;
    private final int alto;
    
    public final int[] pixeles;
    
    //temporal
    private final static int LADO_SPRITE = 32;
    private final static int MASCARA_SPRITE = LADO_SPRITE - 1;
    //fin temporal
    public Pantalla(final int ancho,final  int alto) {
        this.ancho = ancho;
        this.alto = alto;
        
        pixeles = new int[ancho * alto];
    }
    
    public void limpiar(){ //limpiar lo que habia antes y volver a dibujar encima
        for(int i = 0; i < pixeles.length; i++){
            pixeles[i] = 0; 
        }
    }
    public void mostrar(final int compensacionX, final int compensacionY){
        for(int y = 0; y < alto; y++){
            int posicionY = y + compensacionY;
            if(posicionY < 0 || posicionY >= alto){
                continue;
            }
            for(int x = 0; x < ancho; x++){
                int posicionX = x + compensacionX;
                 if(posicionX < 0 || posicionX >= ancho){
                continue;
            }
                 pixeles[(posicionX + posicionY)* ancho] = Sprite.asfalto.pixeles[((x & MASCARA_SPRITE) + (y & MASCARA_SPRITE)) * LADO_SPRITE];
            }
        }
    }
}
