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
import models.Vendedor;
import models.Comprador;
/**
 *
 * @author USER
 */
public class Ventana extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    private static final int ANCHO = 1448;
    private static final int ALTO = 1086;
    private static volatile boolean enFuncionamiento = false;//definir si el juego esta corriendo o no
    private static final String NOMBRE = "theStore";
    
    private static int aps = 0;
    private static int fps = 0;
    
    //private static int x = 0; 
    //private static int y = 0;
    
    private static JFrame ventana;
    private static Thread thread;//ayuda a manejar cosas en paralelo para ello se impementa el runnable
    private static teclado Teclado;
    private static Pantalla pantalla;
    
    private static BufferedImage imagen = new BufferedImage(ANCHO,ALTO,BufferedImage.TYPE_INT_RGB);
    private static int[] pixeles = ((DataBufferInt) imagen.getRaster().getDataBuffer()).getData();
    
    private static Vendedor jugador;
    private static Comprador cliente;
    
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
        // Crear el primer jugador (vendedor)
        jugador = new Vendedor("Ninguno", 0, "Novato", 12345, "Pedro", 3001234);
        jugador.setX(100);
        jugador.setY(100);
        
        //crear comprador
        cliente = new Comprador("nuevo","paciente",123456,"buho",30012345);
        cliente.setX(200);
        cliente.setY(200);
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
    // usamos el metodo moverse heredado de persona
    private void actualizar(){
        Teclado.actualizar();
        
        int velocidad = 1;
        int dx=0;
        int dy=0;
        
        if(Teclado.arriba){
            jugador.Moverse(0, -velocidad); // Resta en Y para subir
        }
        if(Teclado.abajo){
            jugador.Moverse(0, velocidad);  // Suma en Y para bajar
        }
        if(Teclado.izquierda){
            jugador.Moverse(-velocidad, 0); // Resta en X para ir a la izquierda    
        }
        if(Teclado.derecha){
            jugador.Moverse(velocidad, 0);  // Suma en X para ir a la derecha
        }
        
        jugador.Moverse(dx,dy);
        jugador.actualizarAnimacion();
        
        cliente.actualizarIA();
        cliente.actualizarAnimacion();
        
        aps++;
    }
    private void mostrar(){
        BufferStrategy estrategia = getBufferStrategy();
        if(estrategia == null){
            createBufferStrategy(3);
            return;
        }
        pantalla.limpiar();
        pantalla.mostrar(0, 0, Sprite.asfalto); //dibujar primero el asfalto
        
        //elejimos el fotograma 0 por defecto cuando esta quieto
        Sprite spriteActual = Sprite.monstruoCaminando[0];
        
        if (jugador.esEnMovimiento() || jugador.getContadorAnimacion() > 0) {
            // Dividimos el contador entre 6 para cambiar de cuadro cada 6 fotogramas del juego
            int indiceFotograma = jugador.getContadorAnimacion() / 6;
        
            // Control de seguridad para no desbordar el arreglo de 6 elementos
            if (indiceFotograma > 5) {
                indiceFotograma = 5;
            }
        spriteActual = Sprite.monstruoCaminando[indiceFotograma];
        }
        
        Sprite spriteCliente = Sprite.compradorCaminando[cliente.getContadorAnimacion() / 6];
        
        pantalla.mostrar(jugador.getX(), jugador.getY(), spriteActual);//  mostramos el jugador
        pantalla.mostrar(cliente.getX(), cliente.getY(), spriteCliente); // mostramos el cliente
        
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
        
      while(enFuncionamiento ){//si enFuncionamiento es falso, el juego se para
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
