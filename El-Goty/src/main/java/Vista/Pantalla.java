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
    //separar como se muestra el asfalto y los personajes
    //lo uso para mostrar el asfalto aparte y no se modifique su tamaño original
    public void mostrar(final int compensacionX, final int compensacionY, Sprite sprite){
        mostrarEscalado(compensacionX, compensacionY, sprite, 1);
    }
    
    //con este paso la escala de 3 para que los personajes sean mas grandes 
    public void mostrarPersonajes(final int compensacionX, final int compensacionY, Sprite sprite){
        mostrarEscalado(compensacionX, compensacionY, sprite, 3); 
    }
    
    //MÉTODO MAESTRO
    //metodo mostrar modificado para que pueda escalar los personajes y hacerlos mas grandes
    //este tambien imprime el asfalto pero con escala 1, osea, el tamaño original
    private void mostrarEscalado(final int compensacionX, final int compensacionY, Sprite sprite, final int escala) {
        //se lee el lado del sprite y
        for(int y = 0; y < sprite.getLado(); y++){
            
            //este es el bucle que escala el pixel haciendo que se imprima varias veces el mismo
            //dependiendo de la escala la seccionY va de 0 hasta la escala, por ejemplo de 0 a 2
            for(int seccionY = 0; seccionY < escala; seccionY++) {
                //como la escala es 3 el bucle se repite 3 veces imprimiendo el mismo pixel tres veces
                int posicionY = (y * escala) + seccionY + compensacionY;
                
                //si sale de la pantalla no imprime
                if(posicionY < 0 || posicionY >= alto){
                    continue;
                }
                //se lee el lado del sprite x
                for(int x = 0; x < sprite.getLado(); x++){
                    
                    //esta el bucle que escala en x
                    for(int seccionX = 0; seccionX < escala; seccionX++) {
                        
                        //imprimimos tres veces el mismo pixel
                        int posicionX = (x * escala) + seccionX + compensacionX;
                        
                        //si sale de la pantalla no imprime
                        if(posicionX < 0 || posicionX >= ancho){
                            continue;
                        }
                        
                        int pixelPantalla = posicionX + (posicionY * ancho); //indice del arreglo donde se va a insertar el color
                        int pixelSprite = x + (y * sprite.getLado());//parte de donde se saca el color de la imagen original
                        
                        if (pixelPantalla >= 0 && pixelPantalla < pixeles.length) {//Comprueba que el índice de la pantalla sea válido en la memoria
                            int colorPixel = sprite.pixeles[pixelSprite];//Extrae el color original del sprite y lo guarda en colorPixel
                            if(colorPixel != 0){// si el color es diferente de cero, entoces si se imprime
                                pixeles[pixelPantalla] = colorPixel;
                            }
                        }
                    }
                    
                }
            }
        }
    }
}
