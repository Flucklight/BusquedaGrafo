package utility.tree;

import java.awt.*;
import java.util.LinkedList;

public class Nodo {
    private char nombre;
    private LinkedList<Arco> arcos;
    private Point posicion;

    public Nodo(char nombre, int x, int y) {
        this.nombre = nombre;
        this.arcos = new LinkedList<>();
        this.posicion = new Point(x, y);
    }

    public LinkedList<Arco> getArcos() {
        return this.arcos;
    }

    public char getNombre() {
        return nombre;
    }

    public Point getPosicion() {
        return posicion;
    }
}
