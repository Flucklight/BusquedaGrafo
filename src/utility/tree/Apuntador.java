package utility.tree;

import java.util.Stack;

public class Apuntador {
    private Nodo puntero;
    private Stack<Nodo> ruta;

    public Apuntador(Nodo raiz) {
        this.puntero = raiz;
        this.ruta = new Stack<>();
        enrutar();
    }

    private void enrutar() {
        this.ruta.add(this.puntero);
    }

    public void retrocederNodo() {
        this.puntero = this.ruta.pop();
        if (this.ruta.isEmpty()) {
            this.enrutar();
        }
    }

    public void avanzarNodo(Nodo puntero) {
        if (!puntero.equals(this.puntero)) {
            this.puntero = puntero;
            enrutar();
        }
    }

    public Nodo getPuntero() {
        return puntero;
    }

    public String getRuta() {
        String ruta = "";
        for (Nodo nodo : this.ruta) {
            ruta += nodo.getNombre() + "/";
        }
        return ruta;
    }

}
