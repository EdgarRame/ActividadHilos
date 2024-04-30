package Controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Vista.JuegoAtrapaPelota;

public class Controlador extends KeyAdapter {
    private JuegoAtrapaPelota vista;

    public Controlador(JuegoAtrapaPelota vista) {
        this.vista = vista;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            if (vista.getJugadorX() > 0) {
                vista.setJugadorX(vista.getJugadorX() - 40);
            }
        } else if (key == KeyEvent.VK_RIGHT) {
            if (vista.getJugadorX() < vista.getAnchoPanel() - 60) {
                vista.setJugadorX(vista.getJugadorX() + 40);
            }
        }
    }
}

