/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author ESTUDIANTE
 */
public class GestionarArchivo {
 private static String rutaHistorial = "historial_ventas.txt";
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
public static void registrarVenta(String detalleVenta) {
        try {
            // el parámetro true le dice a Java que anada texto al final
            // en lugar de borrar el archivo completo.
            FileWriter escritor = new FileWriter(rutaHistorial, true);
            escritor.write(detalleVenta + "\n");
            escritor.close();
            System.out.println("Venta registrada en el archivo: " + detalleVenta);
        } catch (IOException e) {
            System.out.println("Error al escribir en el historial: " + e.getMessage());
        }
    }
public static String obtenerHistorialJson() {
        String resultado = "{\n  \"historial_ventas\": [\n";
        try {
            FileReader fr = new FileReader(rutaHistorial);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            boolean esPrimera = true;

            while ((linea = br.readLine()) != null) {
                if (!esPrimera) {
                    resultado += ",\n";
                }
                resultado += "    \"" + linea + "\"";
                esPrimera = false;
            }
            br.close();
        } catch (IOException e) {
            // Si el archivo no existe aún, devuelve un JSON de error controlado
            return "{\n  \"error\": \"No hay ventas registradas aun.\"\n}";
        }

        resultado += "\n  ]\n}";
        return resultado;
    }


}
