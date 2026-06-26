/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import static Controlador.GestionarArchivo.guardarJson;
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
import models.Obstaculo;
/**
 *
 * @author USER
 */
public class Ventana extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    private static final int ANCHO = 1448, ANCHO_MIN=800;
    private static final int ALTO = 1086, ALTO_MIN=600;
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
    private static boolean mostrarBotonInteraccion = false; //para interactuar con los clientes
    private static boolean mostrarBotonCaja = false;// para interactuar con la caja
    private boolean cajaPresionada = false;
    
    private static Vendedor jugador;
    private static Comprador cliente;
    private static Comprador cliente2;
    
    private static Obstaculo[] muebles = {
        //estas son las posiciones de x , y ademas de el ancho y alto de cada mueble
        new Obstaculo(610, 20, 90, 130),       //entrada
        new Obstaculo(80, 70, 445, 135),   //estanteria izquierda sup 1
        new Obstaculo(750, 70, 595, 140),   //estanteria derecha sup 1
        new Obstaculo(80, 350, 50, 180),    //estanteria centro izquierda
        new Obstaculo(497, 494, 53, 33),    //carrito 1
        new Obstaculo(614, 436, 223, 91),  //estanteria central 1
        new Obstaculo(611, 695, 226, 87),   //estanteria 2 bajo la 1
        new Obstaculo(965, 660, 37, 20),   //carrito 2
        new Obstaculo(1070, 437, 223, 91),   //estanteria 3 a la derecha
        new Obstaculo(1070, 695, 223, 91),    //estanteria 4 a la derecha abajo
        new Obstaculo(225 ,690, 45,147)   //caja
    };
    
    public Ventana(){
        setPreferredSize(new Dimension(ANCHO,ALTO));
        pantalla = new Pantalla(ANCHO,ALTO);
        Teclado = new teclado();
        addKeyListener(Teclado);
        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setMinimumSize(new Dimension(ANCHO_MIN, ALTO_MIN));
        ventana.setLayout(new BorderLayout());
        ventana.add(this,BorderLayout.CENTER);
        ventana.pack();
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        // Crear el primer jugador (vendedor)
        jugador = new Vendedor("Ninguno", 0, "Novato", 12345, "Pedro", 3001234);
        jugador.setX(300);
        jugador.setY(500);
        
        //crear comprador
        cliente = new Comprador("nuevo","paciente",123456,"buho",30012345);
        cliente.setX(600);
        cliente.setY(200);
        //crear comprador2
        cliente2 = new Comprador("nuevo","impaciente",1234567,"azul",30213527);
        cliente2.setX(600);
        cliente2.setY(200);
    try {
            java.io.FileWriter fw = new java.io.FileWriter("historial_ventas.txt", false); // false borra todo lo viejo
            fw.write("");
            fw.close();
            System.out.println("🧹 Historial de ventas reiniciado con éxito.");
        } catch (java.io.IOException e) {
            System.err.println("Error al reiniciar el archivo: " + e.getMessage());
        }
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
        
        int velocidad = 3;
        int dx=0;
        int dy=0;     
        
        if(Teclado.arriba){
            dy = -velocidad; // Intencion de subir
        }
        if(Teclado.abajo){
            dy = velocidad;  // Intencion de bajar
        }
        if(Teclado.izquierda){
            dx = -velocidad; // Intencion de ir a la izquierda    
        }
        if(Teclado.derecha){
            dx = velocidad;  // Intencion de ir a la derecha
        }
        //calculamos la posicion futura en base al teclado (dx, dy)
        int proximaX = jugador.getX() + dx;
        int proximaY = jugador.getY() + dy;
        
        // para verificar si el paso que va a dar colisionara con otro personaje
        if (jugador.colisionaCon(cliente) || jugador.colisionaCon(cliente2) ) {
            mostrarBotonInteraccion = true;
            
            //acciones futuras aqui dentro
            
            if(Teclado.interactuar){
                Teclado.interactuar = false; //apagamos la tecla para evitar doble pulsación
            }
        }else{
                mostrarBotonInteraccion = false;
        }
        
        Obstaculo cajaRegistradora = muebles[10]; //extraer el objeto de la caja para comprobacion de que se este colisionando
        int tamañoJugador = 96;
        
        //con esto sabremos si colisiona con caja en x o y
        boolean cercaDeCajaX = (jugador.getX() < cajaRegistradora.getX() + cajaRegistradora.getAncho() +10) && 
                               (jugador.getX() + tamañoJugador +10 > cajaRegistradora.getX());
                               
        boolean cercaDeCajaY = (jugador.getY() < cajaRegistradora.getY() + cajaRegistradora.getAlto() +10) && 
                               (jugador.getY() + tamañoJugador +10 > cajaRegistradora.getY());
        
        //comprobamos
        if(cercaDeCajaX && cercaDeCajaY){
            mostrarBotonCaja = true;
            
            if(Teclado.abrirCaja){
        if (!cajaPresionada) {
                    
                    if (jugador != null && cliente != null) {
                        int distanciaX1 = Math.abs(jugador.getX() - cliente.getX());
                        int distanciaY1 = Math.abs(jugador.getY() - cliente.getY());

                        if (distanciaX1 < 80 && distanciaY1 < 80) {
                        int precio = (int)(Math.random() * 81) + 20; 
                            jugador.setMensajeFlotante("¡Son $" + precio + ", por favor!");
                            cliente.setMensajeFlotante("¡Gracias por mi compra!");
                            // Guardamos la venta usando la variable precio
                            Controlador.GestionarArchivo.registrarVenta("Venta realizada: $" + precio + " - Cliente 1");
                        }
                    }

                    // 2. Revisar si el cliente2 está cerca
                    if (jugador != null && cliente2 != null) {
                        int distanciaX2 = Math.abs(jugador.getX() - cliente2.getX());
                        int distanciaY2 = Math.abs(jugador.getY() - cliente2.getY());

                        if (distanciaX2 < 80 && distanciaY2 < 80) {
                            int precioPromo = (int)(Math.random() * 31) + 10;
                            
                            jugador.setMensajeFlotante("¡Llévalo por $" + precioPromo + "!");
                            cliente2.setMensajeFlotante("¡Qué buen servicio!");
                            // Guardamos la venta usando la variable precioPromo
                            Controlador.GestionarArchivo.registrarVenta("Venta con descuento: $" + precioPromo + " - Cliente 2");}
                    }
                    
                    cajaPresionada = true;
                }
                
            } else {
                cajaPresionada = false;
                
                // Al soltar la tecla F, limpiamos los mensajes de todos
                if (jugador != null) jugador.setMensajeFlotante("");
                if (cliente != null) cliente.setMensajeFlotante("");
                if (cliente2 != null) cliente2.setMensajeFlotante("");   
            }
     }
        
        //verificar colision con un objeto
        if (jugador.colisionaConMuebles(proximaX, proximaY, muebles)) {
            //si el paso que va a dar choca con un mueble, cancelamos el movimiento
            dx = 0;
            dy = 0;
        }
        
        //ajustamos los limites basados en las dimensiones de la tienda
        if (proximaX < 80 || proximaX > 1275) { dx = 0; }
        if (proximaY < 70 || proximaY > 870) { dy = 0; }
        
        jugador.Moverse(dx,dy);
        jugador.actualizarAnimacion();
        
        cliente.actualizarIA(muebles);
        cliente.actualizarAnimacion();
        cliente2.actualizarIA(muebles);
        cliente2.actualizarAnimacion();
        
        if (Teclado.guardar) {
            // Generamos el texto JSON con los datos del comprador en este instante
            String datosComprador = cliente.convertirAJson();
            
            // Definimos la ruta y el nombre del archivo en la raíz de tu proyecto
            String ruta = "comprador_guardado.json";
            
            // Invocamos al Gestor de Archivos para que lo congele en el disco duro
            guardarJson(ruta, datosComprador);
            
            // Apagamos la tecla temporalmente para que no guarde repetidamente en bucle
            Teclado.guardar = false; 
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
        //dibujar primero el asfalto
        pantalla.mostrar(0, 0, Sprite.asfalto);
        
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
        Sprite spriteCliente2 = Sprite.compradorCaminando2[cliente2.getContadorAnimacion() / 6];
        
        pantalla.mostrarPersonajes(jugador.getX(), jugador.getY(), spriteActual);//  mostramos el jugador
        if(cliente.isVisible()){
            pantalla.mostrarPersonajes(cliente.getX(),cliente.getY(),spriteCliente);
        }

        if(cliente2.isVisible()){
            pantalla.mostrarPersonajes(cliente2.getX(),cliente2.getY(),spriteCliente2);
        }
        
        System.arraycopy(pantalla.pixeles,0,pixeles,0,pixeles.length);
        /*
        for(int i = 0; i < pixeles.length; i++){
            pixeles[i] = pantalla.pixeles[i];
        }*/
        Graphics g = estrategia.getDrawGraphics();
        
        g.drawImage(imagen, 0, 0, getWidth(),getHeight(),null);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
   
        if (jugador != null && !jugador.getMensajeFlotante().equals("")) {
        String msj = jugador.getMensajeFlotante();
        
        int xPantalla = jugador.getX();
        int yPantalla = jugador.getY();
        
        // 48 es la mitad de 96 (el tamaño real del personaje escalado en pantalla)
        int xCentro = xPantalla + 48 - (g.getFontMetrics().stringWidth(msj) / 2);
        int yArriba = yPantalla - 15;
        
        // Sombra negra
        g.setColor(java.awt.Color.BLACK);
        g.drawString(msj, xCentro + 1, yArriba + 1);
        // Texto original en blanco
        g.setColor(java.awt.Color.WHITE);
        g.drawString(msj, xCentro, yArriba - 40);
    }

    if (cliente != null && !cliente.getMensajeFlotante().equals("")) {
        String msj = cliente.getMensajeFlotante();
        
        int xPantalla = cliente.getX();
        int yPantalla = cliente.getY();       
        
        int xCentro = xPantalla + 48 - (g.getFontMetrics().stringWidth(msj) / 2);
        int yArriba = yPantalla - 15;
        
        g.setColor(java.awt.Color.BLACK);
        g.drawString(msj, xCentro + 1, yArriba + 1);
        g.setColor(java.awt.Color.WHITE);
        g.drawString(msj, xCentro, yArriba - 40);
    }
    if (cliente2 != null && !cliente2.getMensajeFlotante().equals("")) {
        String msj = cliente2.getMensajeFlotante();
        
        int xPantalla = cliente2.getX();
        int yPantalla = cliente2.getY();
        
        int xCentro = xPantalla + 48 - (g.getFontMetrics().stringWidth(msj) / 2);
        int yArriba = yPantalla - 15;
        
        g.setColor(java.awt.Color.BLACK);
        g.drawString(msj, xCentro + 1, yArriba + 1);
        g.setColor(java.awt.Color.WHITE);
        g.drawString(msj, xCentro, yArriba - 40);
    }
        
//boton interactuar
        if (mostrarBotonInteraccion) {
            // Calculamos el centro de la pantalla abajo de forma dinamica usando las dimensiones
            int anchoBoton = 300;
            int altoBoton = 50;
            int botonX = (getWidth() / 2) - (anchoBoton / 2); //centrado horizontal
            int botonY = getHeight() - 100;                  //abajo en la pantalla

            //dibujamos el fondo del boton (un rectangulo negro con borde blanco)
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(botonX, botonY, anchoBoton, altoBoton);
            
            g.setColor(java.awt.Color.WHITE);
            g.drawRect(botonX, botonY, anchoBoton, altoBoton);

            //dibujamos el texto centrado dentro del boton
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
            g.drawString("PRESIONA 'E' PARA HABLAR", botonX + 35, botonY + 32);
        }
        
        if(mostrarBotonCaja){
            int altoBoton = 50;
            int anchoBoton = 320;
            //posicion del boton            
            int botonX = (getWidth()/2) - (anchoBoton / 2);//ancho de la ventana y lo divide entre 2 para centrarlo
            int botonY = getHeight() - 100;//determina a la altura de la pantalla (-100 para que no quede pegado abajo)
            
            g.setColor(new java.awt.Color(20, 40, 80)); //le doy color nuevo
            g.fillRect(botonX, botonY, anchoBoton, altoBoton);// se pinta el fondo del boton
            g.setColor(java.awt.Color.CYAN); //cambio de color
            g.drawRect(botonX, botonY, anchoBoton, altoBoton); //dibujamos el contorno con ese color
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15)); //tipografia 
            g.setColor(java.awt.Color.WHITE);//otro cambio de color xd
            g.drawString("PRESIONA 'F' PARA ABRIR LA TIENDA", botonX + 15, botonY + 31); //escribimos el contenido en blanco
        }
        
        //TEMPORAL PARA SABER LA POSICION DEL JUGADOR
        g.setColor(new java.awt.Color(0, 0, 0, 180)); //el cuarto parametro da opacidad
        g.fillRect(15, 15, 240, 85);
        
        //añadimos un borde blanco
        g.setColor(java.awt.Color.WHITE);
        g.drawRect(15, 15, 240, 85);
        
        //configuramos la fuente del texto
        g.setFont(new java.awt.Font("Consolas", java.awt.Font.BOLD, 14));
        g.setColor(java.awt.Color.GREEN);
        
        //calculamos las celdas basandonos en el tamaño de 96 pixeles
        int celdaX = jugador.getX() / 96;
        int celdaY = jugador.getY() / 96;
        
        //pintamos las cadenas de texto con las posiciones del jugador en tiempo real
        g.drawString("DEV MODE - POSICION", 25, 35);
        g.setColor(java.awt.Color.WHITE);
        g.drawString("Pixeles : X: " + jugador.getX() + " | Y: " + jugador.getY(), 25, 55);
        g.drawString("Matriz  : Col: " + celdaX + " | Fila: " + celdaY, 25, 75);

        if (Teclado.verHistorial && jugador != null) { 
            String textoJson = Controlador.GestionarArchivo.obtenerHistorialJson();
            String[] lineas = textoJson.split("\n");
            
            int xCaja = jugador.getX() - 100;
            int yCaja = jugador.getY() - 180;
            
            // 1. Fondo negro semitransparente
            g.setColor(new java.awt.Color(0, 0, 0, 210)); 
            g.fillRect(xCaja, yCaja, 340, (lineas.length * 16) + 20);
            
            // 2. Borde de la ventanita
            g.setColor(java.awt.Color.WHITE);
            g.drawRect(xCaja, yCaja, 340, (lineas.length * 16) + 20);
            
            // 3. Texto verde estilo programación
            g.setColor(java.awt.Color.GREEN);
            g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 13));
            
            int yTexto = yCaja + 20;
            for (int i = 0; i < lineas.length; i++) {
                g.drawString(lineas[i], xCaja + 15, yTexto);
                yTexto += 16;
            }
        }
      
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
