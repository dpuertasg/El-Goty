/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.teclado;
import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
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
    
    private static int aps = 0;
    private static int fps = 0;
    
    private static int x = 0; 
    private static int y = 0;
    
    private static JFrame ventana;
    private static Thread thread;//ayuda a manejar cosas en paralelo para ello se impementa el runnable
    private static teclado Teclado;
    private static Pantalla pantalla;
    
    private static BufferedImage imagen = new BufferedImage(ANCHO,ALTO,BufferedImage.TYPE_INT_RGB);
    private static int[] pixeles = ((DataBufferInt) imagen.getRaster().getDataBuffer()).getData();
    
    public Ventana(){
        setPreferredSize(new Dimension(ANCHO,ALTO));
        pantalla = new Pantalla(ANCHO,ALTO);
        Teclado = new teclado();
        addKeyListener(Teclado);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void actualizar(){
        Teclado.actualizar();
        
        if(Teclado.arriba){
            System.out.println("arriba");
        }
        if(Teclado.abajo){
            System.out.println("abajo");
        }
        if(Teclado.izquierda){
            System.out.println("izquierda");
        }
        if(Teclado.derecha){
            System.out.println("derecha");
        }
        aps++;
    }
    private void mostrar(){
        BufferStrategy estrategia = getBufferStrategy();
        if(estrategia == null){
            createBufferStrategy(3);
            return;
        }
        pantalla.limpiar();
        pantalla.mostrar(x,y,Sprite.asfalto);
        
        System.arraycopy(pantalla.pixeles,0,pixeles,0,pixeles.length);
        /*
        for(int i = 0; i < pixeles.length; i++){
            pixeles[i] = pantalla.pixeles[i];
        }*/
        Graphics g = estrategia.getDrawGraphics();
        
        g.drawImage(imagen, 0, 0, getWidth(),getHeight(),null);
        g.dispose();
        estrategia.show();
        fps++;
    }
    @Override
    public void run() {
        //importantes para que el juego no se sienta lento
        final int NS_POR_SEGUNDO = 1000000000; //nanosegundos por segundo
        final byte APS_OBJETIVO = 60; //actualizaciones por segundo
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;
        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();
        double tiempoTranscurrido;
        double delta = 0; // cantidad de tiempo que ha transcurrido hasta una actualizacion 
        
        requestFocus();
        
      while(enFuncionamiento = true){//si enFuncionamiento es falso, el juego se para
          final long inicioBucle = System.nanoTime();
          tiempoTranscurrido = inicioBucle - referenciaActualizacion;
          referenciaActualizacion = inicioBucle;
          delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;
          
          while(delta >= 1){
              actualizar();
              delta --;
          }
          mostrar();
          
          if ( System.nanoTime() - referenciaContador > NS_POR_SEGUNDO){
              ventana.setTitle(NOMBRE + " || APS " + aps + "|| FPS " + fps);
              aps = 0;
              fps = 0; 
              referenciaContador = System.nanoTime();
          }
      }
     
    }
}
