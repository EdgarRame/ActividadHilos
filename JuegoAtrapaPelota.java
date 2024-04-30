package Vista;

import Modelo.Pelota;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Controlador.Controlador;
public class JuegoAtrapaPelota extends JPanel {
    private static final int ANCHO_PANEL = 300;
    private static final int ALTO_PANEL = 400;
    private static final int ANCHO_JUGADOR = 60;
    private static final int ALTO_JUGADOR = 10;
    private static final int RADIO_PELOTA = 10;
    private static final Color COLOR_JUGADOR = Color.BLUE;
    private static final Color COLOR_PELOTA = Color.RED;
    private int puntuacion;
    private int Dificultad=1700;
    private static final int MIN_DIFICULTAD = 300;
    

    private boolean juegoEnEjecucion = true;

    private List<Pelota> pelotas = new ArrayList<>();
    private int jugadorX = ANCHO_PANEL / 2 - ANCHO_JUGADOR / 2;

    public JuegoAtrapaPelota() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(ANCHO_PANEL, ALTO_PANEL));
        addKeyListener(new Controlador(this));
        setFocusable(true);

        Timer timer = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mover();
                detectarColision();
                repaint();
            }
        });
        timer.start();

        Thread pelotaThread = new Thread(new Runnable() {
            public void run() {
                while (juegoEnEjecucion) {
                    crearPelota();
                    try {
                        Thread.sleep(Dificultad);
                    
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        pelotaThread.start();
    }
 
    private void mover() {
        for (Pelota pelota : pelotas) {
            pelota.mover();
        }
    }

    private void detectarColision() {
        Rectangle jugadorRect = new Rectangle(jugadorX, ALTO_PANEL - ALTO_JUGADOR, ANCHO_JUGADOR, ALTO_JUGADOR);
        for (int i = 0; i < pelotas.size(); i++) {
            Pelota pelota = pelotas.get(i);
            Rectangle pelotaRect = new Rectangle(pelota.getX() - RADIO_PELOTA, pelota.getY() - RADIO_PELOTA, RADIO_PELOTA * 2, RADIO_PELOTA * 2);
            if (jugadorRect.intersects(pelotaRect)) {
                pelotas.remove(i);
                puntuacion++;
                if (puntuacion % 2 == 0) {
                    Dificultad = Math.max(Dificultad - 100, MIN_DIFICULTAD); 
                }
                break;
            }
            else if (pelota.estaFuera(ALTO_PANEL)) {
                    mostrarMensajePerdiste();
                    break;
            }
        }
    }
    private void mostrarMensajePerdiste() {
         int respuesta = JOptionPane.showConfirmDialog(null,  "¿Volver a Jugar?", 
                "Reintentar", 
                JOptionPane.YES_NO_OPTION);
   
     if (respuesta == JOptionPane.YES_OPTION) {
            reiniciarJuego();
        } else {
            salirDelJuego();
        }
    }
    private void reiniciarJuego() {
        pelotas.clear();
        juegoEnEjecucion = true;
        puntuacion = 0;
        Dificultad = 1700;
    }
    private void salirDelJuego() {
        JOptionPane.showMessageDialog(
            null,"Fin del Juego:(");
        System.exit(0);
    }

    private void crearPelota() {
        Random rand = new Random();
        int x = rand.nextInt(ANCHO_PANEL - 2 * RADIO_PELOTA);
        pelotas.add(new Pelota(x + RADIO_PELOTA, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar jugador
        g2d.setColor(COLOR_JUGADOR);
        g2d.fillRect(jugadorX, ALTO_PANEL - ALTO_JUGADOR, ANCHO_JUGADOR, ALTO_JUGADOR);

        // Dibujar pelotas
        g2d.setColor(COLOR_PELOTA);
        for (Pelota pelota : pelotas) {
            g2d.fillOval(pelota.getX() - RADIO_PELOTA, pelota.getY() - RADIO_PELOTA, RADIO_PELOTA * 2, RADIO_PELOTA * 2);
        }
        // Mostrar puntuación
        g2d.setColor(Color.BLACK);
        g2d.drawString("Puntuación: " + puntuacion, 10, 20);
    }
    public int getAnchoPanel() {
        return ANCHO_PANEL;
    }
    public int getAltoPanel() {
        return ALTO_PANEL;
    }
    public int getJugadorX() {
        return jugadorX;
    }
        public void setJugadorX(int jugadorX) {
        this.jugadorX = jugadorX;
    }
}