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
   public void mostrar(final int compensacionX, final int compensacionY, Sprite sprite){
        for(int y = 0; y < sprite.getLado(); y++){
            int posicionY = y + compensacionY;
            
            // Si el píxel se sale por arriba o por abajo de la pantalla, no lo dibujamos
            if(posicionY < 0 || posicionY >= alto){
                continue;
            }
            
            for(int x = 0; x < sprite.getLado(); x++){
                int posicionX = x + compensacionX;
                
                // Si el píxel se sale por la izquierda o derecha de la pantalla, no lo dibujamos
                if(posicionX < 0 || posicionX >= ancho){
                    continue;
                }
                
                // CORRECCIÓN FÓRMULA DE PANTALLA: x + y * ancho
                int pixelPantalla = posicionX + (posicionY * ancho);
                
                // CORRECCIÓN FÓRMULA DE SPRITE: x + y * lado
                int pixelSprite = x + (y * sprite.getLado());
                
                // Dibujamos de forma segura comprobando que no nos salgamos del array
                if (pixelPantalla >= 0 && pixelPantalla < pixeles.length) {
                    // ACTUALIZO PARA QUE HAYA TRANSPARENCIA EN LOS PERSONAJES
                    int colorPixel = sprite.pixeles[pixelSprite];
                    
                    //SI EL PIXEL ES NEGRO NO SE PINTA
                    if(colorPixel != 0){
                        pixeles[pixelPantalla] = colorPixel;
                    }
                }
            }
        }
    }
}
