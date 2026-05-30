/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author USER
 */
public class teclado implements KeyListener{

    private final static int numeroTeclas = 120;
    private final boolean[] teclas = new boolean[numeroTeclas];
    
    public boolean arriba;
    public boolean abajo;
    public boolean izquierda;
    public boolean derecha;
    //tecla para guardar partida
    public boolean guardar;
    
    public void actualizar(){
        arriba = teclas[KeyEvent.VK_W];
        abajo = teclas[KeyEvent.VK_S];
        izquierda = teclas[KeyEvent.VK_A];
        derecha = teclas[KeyEvent.VK_D];
        guardar = teclas[KeyEvent.VK_G];
    }
    
    @Override
    public void keyPressed(KeyEvent e) {//cuando presionamos la tecla y no la hemos soltado
      teclas[e.getKeyCode()] = true;
    }
   
    @Override
    public void keyReleased(KeyEvent e) {//cuando soltamos la tecla
       teclas[e.getKeyCode()] = false;
    }
    
     @Override
    public void keyTyped(KeyEvent e) {//pulsar y soltar la tecla
      
    }

}
