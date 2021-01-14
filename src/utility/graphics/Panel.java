package utility.graphics;

import utility.procces.Init;
import utility.tree.Arco;
import utility.tree.Nodo;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 1024, 720);

        for (Nodo nodo : Init.grafo.getNodos()) {
            g.setColor(Color.white);
            for (Arco arco : nodo.getArcos()) {
                g.drawLine(nodo.getPosicion().x + 5, nodo.getPosicion().y + 15,
                        arco.getNodo().getPosicion().x + 5, arco.getNodo().getPosicion().y + 15);
            }
            g.setColor(Color.white);
            g.fillOval(nodo.getPosicion().x, nodo.getPosicion().y, 20, 20);
            g.setColor(Color.black);
            g.drawString(String.valueOf(nodo.getNombre()), nodo.getPosicion().x + 5, nodo.getPosicion().y + 15);
        }

        for (Nodo nodo : Init.visitados) {
            g.setColor(Color.green);
            g.fillOval(nodo.getPosicion().x, nodo.getPosicion().y, 20, 20);
            g.setColor(Color.black);
            g.drawString(String.valueOf(nodo.getNombre()), nodo.getPosicion().x + 5, nodo.getPosicion().y + 15);
        }

        g.setColor(Color.red);
        g.fillOval(Init.grafo.getApuntador().getPuntero().getPosicion().x, Init.grafo.getApuntador().getPuntero().getPosicion().y, 20, 20);
        g.setColor(Color.orange);
        g.drawString(
                String.valueOf(Init.grafo.getApuntador().getPuntero().getNombre()),
                Init.grafo.getApuntador().getPuntero().getPosicion().x + 5,
                Init.grafo.getApuntador().getPuntero().getPosicion().y + 15
        );

    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }
}
