/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
/**
 *
 * @author USER
 */
public class Ventana extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    private static final int ANCHO = 800;
    private static final int ALTO = 600;
    private static volatile boolean enFuncionamiento = false;//definir si el juego esta corriendo o no
    private static final String NOMBRE = "theStore";
    private static JFrame ventana;
    private static Thread thread;//ayuda a manejar cosas en paralelo para ello se impementa el runnable
    
    public Ventana(){
        setPreferredSize(new Dimension(ANCHO,ALTO));
        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this,BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
    public synchronized void iniciar(){
        enFuncionamiento = true;
        thread = new Thread(this, "Graficos");
        thread.start();
    }
    public synchronized void detener(){
        try {
            enFuncionamiento = false;

            thread.join();// para no parar el thread de forma aburpta
        } catch (InterruptedException ex) {
            System.getLogger(Ventana.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    @Override
    public void run() {
        System.nanoTime();
      while(enFuncionamiento = true){//si enFuncionamiento es falso, el juego se para
          
      }
     
    }
}
