package utility.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class Grafo {
    private Nodo inicial;
    private Apuntador apuntador;
    private LinkedList<Nodo> nodos;
    private File archivoFuente;
    private Integer[][] matriz;
    private BufferedReader cargar;

    public Grafo() throws IOException {
        this.inicial = new Nodo('A', 512, 360);
        this.nodos = new LinkedList<>();
        this.nodos.add(this.inicial);
        this.apuntador = new Apuntador(this.inicial);
        this.matriz = new Integer[28][28];
        this.archivoFuente = new File(System.getProperty("user.dir") + "/src/items/archivoFuente.tree");
        this.cargar();
        this.generar();
    }

    private void cargar() throws IOException {
        this.cargar = new BufferedReader(new FileReader(this.archivoFuente));
        String s;
        String[] tmp;
        int i = 0;
        while ((s = cargar.readLine()) != null) {
            tmp = s.split(",");
            for (int j = 0; j < tmp.length; j++) {
                this.matriz[i][j] = Integer.parseInt(tmp[j]);
            }
            i++;
        }
    }

    public void generar() {
        Nodo auxNodo;
        Arco auxArco;

        for (int i = 1; i < this.matriz.length; i++) {
            int x = new Random().nextInt(950);
            int y = new Random().nextInt(680);
            this.nodos.add(new Nodo((char) (i + 65), x, y));
        }

        for (int i = 0; i < this.matriz.length; i++) {
            this.apuntador.avanzarNodo(this.nodos.get(i));
            for (int j = 0; j < this.matriz[i].length; j++) {
                if (this.matriz[i][j] != 0) {
                    for (Nodo nodo : this.nodos) {
                        if ((char) (j + 65) == nodo.getNombre()) {
                            auxNodo = nodo;
                            auxArco = new Arco(auxNodo, this.matriz[i][j]);
                            this.apuntador.getPuntero().getArcos().add(auxArco);
                            break;
                        }
                    }
                }
            }
        }
    }

    //Navegacion

    public void retrocederNodo() {
        this.apuntador.retrocederNodo();
    }

    public void mostrarPuntero() {
        System.out.println("Puntero -------------> " + this.apuntador.getRuta());
    }

    public Apuntador getApuntador() {
        return apuntador;
    }

    public Nodo getInicial() {
        return inicial;
    }

    public void setInicial(Nodo inicial) {
        this.inicial = inicial;
    }

    public LinkedList<Nodo> getNodos() {
        return nodos;
    }

    public void goToRaiz() {
        this.apuntador = new Apuntador(this.inicial);
    }
}
