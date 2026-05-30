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
 
   // --- PASO 2: MÉTODO PARA ESCRIBIR EL TEXTO EN EL DISCO DURO ---
    public static void guardarJson(String rutaArchivo, String contenidoJson) {
        // Envolvemos en try-catch por seguridad de Entrada/Salida (I/O)
        try {
            File archivo = new File(rutaArchivo);
            
            FileWriter escritor = new FileWriter(archivo);
            escritor.write(contenidoJson);     
            // Cerramos el archivo
            escritor.close(); 
            
            System.out.println("Datos guardados con exito en: " + archivo.getAbsolutePath() + "!");
            
        } catch (IOException e) {
            System.out.println("Error critico: No se pudo guardar el archivo JSON.");
            e.printStackTrace();
        }
    }
}
