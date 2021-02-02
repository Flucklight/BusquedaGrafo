package utility.tree;

import java.awt.*;
import java.util.LinkedList;

public class Nodo {
    private String nombre;
    private LinkedList<Arco> arcos;
    private Point posicion;

    public Nodo(String nombre, int x, int y) {
        this.nombre = nombre;
        this.arcos = new LinkedList<>();
        this.posicion = new Point(x, y);
    }

    public LinkedList<Arco> getArcos() {
        return this.arcos;
    }

    public String getNombre() {
        return nombre;
    }

    public Point getPosicion() {
        return posicion;
    }
}
