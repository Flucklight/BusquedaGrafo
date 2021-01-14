package utility.procces;

import utility.graphics.Ventana;
import utility.tree.Arco;
import utility.tree.Grafo;
import utility.tree.Nodo;

import java.io.IOException;
import java.util.LinkedList;

public class Init {
    public static Grafo grafo;
    public static LinkedList<Nodo> visitados;

    public Init() throws IOException {
        grafo = new Grafo();
        visitados = new LinkedList<>();
        new Ventana().setVisible(true);
        BusquedaAltura();
        BusquedaAnchura();
    }

    private static void BusquedaAltura() {
        System.out.println(" ------------- Busqueda por Altura ------------- ");
        grafo.goToRaiz();
        visitados = new LinkedList<>();
        Ventana.panel.run();
        grafo.mostrarPuntero();
        while (grafo.getApuntador().getPuntero().getNombre() != 'R') {
            if (!grafo.getApuntador().getPuntero().getArcos().isEmpty()) {
                for (Arco arco : grafo.getApuntador().getPuntero().getArcos()) {
                    if (!visitados.contains(arco.getNodo())) {
                        visitados.add(arco.getNodo());
                        grafo.getApuntador().avanzarNodo(arco.getNodo());
                        break;
                    } else if (arco.equals(grafo.getApuntador().getPuntero().getArcos().getLast())) {
                        grafo.retrocederNodo();
                    }
                }
            } else {
                grafo.retrocederNodo();
            }
            grafo.mostrarPuntero();
            Ventana.panel.run();
        }
    }

    private static void BusquedaAnchura() {
        System.out.println(" ------------- Busqueda por Anchura ------------- ");
        grafo.goToRaiz();
        visitados = new LinkedList<>();
        Ventana.panel.run();
        LinkedList<Nodo> visitar = new LinkedList<>();
        LinkedList<Nodo> tmp;
        visitar.add(grafo.getInicial());
        while (grafo.getApuntador().getPuntero().getNombre() != 'R') {
            tmp = (LinkedList<Nodo>) visitar.clone();
            for (Nodo n : tmp) {
                visitar.remove(n);
                visitados.add(n);
                grafo.getApuntador().avanzarNodo(n);
                grafo.mostrarPuntero();
                Ventana.panel.run();
                if (grafo.getApuntador().getPuntero().getNombre() == 'R') {
                    break;
                } else if (!grafo.getApuntador().getPuntero().getArcos().isEmpty()) {
                    for (Arco arco : grafo.getApuntador().getPuntero().getArcos()) {
                        if (!visitados.contains(arco.getNodo()) && !visitar.contains(arco.getNodo())) {
                            visitar.add(arco.getNodo());
                        }
                    }
                }
            }
        }
    }

}
