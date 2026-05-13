/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author USER
 */
public class HojaSprites {
    private final int alto;
    private final int ancho;
    public final int[] pixeles;
    public HojaSprites(final String ruta,final int ancho, final int alto){
        this.ancho = ancho;
        this.alto = alto;
        
        pixeles = new int[ancho * alto];
        BufferedImage imagen;
        
        try{
        imagen = ImageIO.read(HojaSprites.class.getResource(ruta));
        imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
        
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getAncho() {
        return ancho;
    }

}
