package utility.tree;

public class Arco {
    int ponderacion;
    Nodo nodo;

    public Arco(Nodo nodo, int ponderacion) {
        this.ponderacion = ponderacion;
        this.nodo = nodo;
    }

    public int getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(int ponderacion) {
        this.ponderacion = ponderacion;
    }

    public Nodo getNodo() {
        return nodo;
    }

    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }
}
