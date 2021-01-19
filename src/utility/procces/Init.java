package utility.procces;

import utility.graphics.Ventana;
import utility.tree.Arco;
import utility.tree.Grafo;
import utility.tree.Nodo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Init {
    public static Grafo grafo;
    public static LinkedList<Nodo> visitados;
    public static char inicio = 'A';
    public static char fin = 'R';
    public static Integer[][] matriz;

    public Init() throws IOException {
        grafo = new Grafo();
        grafo.setInicial(inicio);
        visitados = new LinkedList<>();
        cargar();
        new Ventana().setVisible(true);
        BusquedaAltura();
        BusquedaAnchura();
        BusquedaEscaladaSimple();
        BuesquedaMaximaPendiente();
        BusquedaPrimeroMejor();
    }

    private static void cargar() throws IOException {
        matriz = new Integer[28][28];
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/src/items/busquedaHeuristica.tree")));
        String s;
        String[] tmp;
        int i = 0;
        while ((s = bufferedReader.readLine()) != null) {
            tmp = s.split(",");
            for (int j = 0; j < tmp.length; j++) {
                matriz[i][j] = Integer.parseInt(tmp[j]);
            }
            i++;
        }
    }

    private static void BusquedaAltura() {
        System.out.println(" ------------- Busqueda por Altura ------------- ");
        grafo.goToRaiz();
        visitados = new LinkedList<>();
        Ventana.panel.run();
        grafo.mostrarPuntero();
        while (grafo.getApuntador().getPuntero().getNombre() != fin) {
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
        while (grafo.getApuntador().getPuntero().getNombre() != fin) {
            tmp = (LinkedList<Nodo>) visitar.clone();
            for (Nodo n : tmp) {
                visitar.remove(n);
                visitados.add(n);
                grafo.getApuntador().avanzarNodo(n);
                grafo.mostrarPuntero();
                Ventana.panel.run();
                if (grafo.getApuntador().getPuntero().getNombre() == fin) {
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

    private static void BusquedaEscaladaSimple() {
        System.out.println(" ------------- Busqueda por Escalada Simple ------------- ");
        grafo.goToRaiz();
        visitados = new LinkedList<>();
        int index = matriz[(int) fin - 65][(int) grafo.getApuntador().getPuntero().getNombre() - 65];
        Ventana.panel.run();
        while (grafo.getApuntador().getPuntero().getNombre() != fin) {
            System.out.println("Costo Actual -> " + index);
            for (Arco a : grafo.getApuntador().getPuntero().getArcos()) {
                int tempIndex = matriz[(int) fin - 65][(int) a.getNodo().getNombre() - 65];
                if (tempIndex < index) {
                    index = tempIndex;
                    visitados.add(grafo.getApuntador().getPuntero());
                    grafo.getApuntador().avanzarNodo(a.getNodo());
                    grafo.mostrarPuntero();
                    Ventana.panel.run();
                    break;
                }
            }
            if (index == 0 && grafo.getApuntador().getPuntero().getNombre() != fin) {
                System.out.println("Fin de la ruta tabla rota");
                break;
            }
        }
        visitados.add(grafo.getApuntador().getPuntero());
        Ventana.panel.run();
    }

    private static void BuesquedaMaximaPendiente() {
        System.out.println(" ------------- Busqueda por Maxima Pendiente ------------- ");
        grafo.goToRaiz();
        visitados = new LinkedList<>();
        int index = matriz[(int) fin - 65][(int) grafo.getApuntador().getPuntero().getNombre() - 65];
        Nodo tempNodo = null;
        Ventana.panel.run();
        while (grafo.getApuntador().getPuntero().getNombre() != fin) {
            System.out.println("Costo Actual -> " + index);
            for (Arco a : grafo.getApuntador().getPuntero().getArcos()) {
                int tempIndex = matriz[(int) fin - 65][(int) a.getNodo().getNombre() - 65];
                if (tempIndex < index) {
                    index = tempIndex;
                    tempNodo = a.getNodo();
                }
            }
            visitados.add(grafo.getApuntador().getPuntero());
            grafo.getApuntador().avanzarNodo(tempNodo);
            grafo.mostrarPuntero();
            Ventana.panel.run();
            if (index == 0 && grafo.getApuntador().getPuntero().getNombre() != fin) {
                System.out.println("Fin de la ruta tabla rota");
                break;
            }
        }
        visitados.add(grafo.getApuntador().getPuntero());
        Ventana.panel.run();
    }

    private static void BusquedaPrimeroMejor() {
        System.out.println(" ------------- Busqueda por Primero Mejor ------------- ");
        grafo.goToRaiz();
        visitados = new LinkedList<>();
        Nodo tempNodo = null;
        int f, g = 0, h;
        Ventana.panel.run();
        while (grafo.getApuntador().getPuntero().getNombre() != fin) {
            f = Integer.MAX_VALUE;
            tempNodo = grafo.getApuntador().getAntecesor();
            if (tempNodo != null) {
                for (Arco a : grafo.getApuntador().getPuntero().getArcos()) {
                    if (a.getNodo().equals(tempNodo)) {
                        g = a.getPonderacion();
                        break;
                    }
                }
            }
            for (Arco a : grafo.getApuntador().getPuntero().getArcos()) {
                h = matriz[(int) fin - 65][(int) a.getNodo().getNombre() - 65];
                int tempF = g + h;
                if (tempF < f) {
                    f = tempF;
                    tempNodo = a.getNodo();
                }
            }
            if (grafo.getApuntador().getAntecesor() != null && grafo.getApuntador().getAntecesor().equals(tempNodo)) {
                System.out.println("Fin de la ruta tabla rota");
                break;
            }
            visitados.add(grafo.getApuntador().getPuntero());
            grafo.getApuntador().avanzarNodo(tempNodo);
            grafo.mostrarPuntero();
            Ventana.panel.run();
        }
    }

}
