/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HojaSprites {
    private final int alto;
    private final int ancho;
    public final int[] pixeles;
    
    public static HojaSprites tienda = new HojaSprites("Recursos/Texturas/tienda.png", 320, 320);
    
    public HojaSprites(final String ruta, final int ancho, final int alto){
        this.ancho = ancho;
        this.alto = alto;
        this.pixeles = new int[ancho * alto];
        
        BufferedImage imagen;
        
        // Leer directamente el archivo del disco
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                throw new RuntimeException("No se pudo encontrar el archivo en la ruta física: " + archivo.getAbsolutePath());
            }
            
            imagen = ImageIO.read(new FileInputStream(archivo));
            imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAncho() {
        return ancho;
    }
}