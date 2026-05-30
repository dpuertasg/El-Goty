/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author ESTUDIANTE
 */
public class GestionarArchivo {
   //escribir en el disco
   public static void guardarJson(String rutaArchivo, String contenidoJson) {
        try {
            File archivo = new File(rutaArchivo);
            FileWriter escritor = new FileWriter(archivo);
            escritor.write(contenidoJson);
            escritor.close(); 
            
            System.out.println("¡Datos guardados con éxito en: " + archivo.getAbsolutePath() + "!");
            
            // --- 2. ¡AQUÍ AÑADIMOS LA VENTANA EMERGENTE! ---
            // Parámetros: (Componente padre, Mensaje interno, Título de la ventana, Tipo de mensaje)
            JOptionPane.showMessageDialog(
                    null, 
                    "¡Los datos del comprador se guardaron con éxito!", 
                    "Guardado Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE
            );
            
        } catch (IOException e) {
            System.out.println("Error crítico: No se pudo guardar el archivo JSON.");
            e.printStackTrace();
            
            // --- Opcional: También podemos avisar si algo falló ---
            JOptionPane.showMessageDialog(
                    null, 
                    "No se pudo guardar el archivo JSON.", 
                    "Error de Guardado", 
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
