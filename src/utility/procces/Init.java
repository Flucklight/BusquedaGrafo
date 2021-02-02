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
import java.util.Scanner;

public class Init {
    public static Grafo grafo;
    public static LinkedList<Nodo> visitados;
    public static String inicio;
    public static String fin;
    public static boolean sentido;
    public static Integer[][] matriz;
    private Scanner sc;

    public Init() throws IOException {
        sc = new Scanner(System.in);
        grafo = new Grafo();
        System.out.print("Inicio de la busqueda: ");
        inicio = sc.nextLine();
        System.out.print("Fin de la busqueda: ");
        fin = sc.nextLine();
        System.out.println("0 --> Horario");
        System.out.println("1 --> Antihorario");
        System.out.print("Sentido de la busqueda: ");
        switch (sc.nextInt()) {
            case 0:
                sentido = false;
                break;
            case 1:
                sentido = true;
                break;
            default :
                sentido = false;
                break;
        }
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
        visitados.add(grafo.getApuntador().getPuntero());
        while (Integer.parseInt(grafo.getApuntador().getPuntero().getNombre()) != Integer.parseInt(fin)) {
            if (!grafo.getApuntador().getPuntero().getArcos().isEmpty()) {
                if (sentido) {
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
                    for (int i = grafo.getApuntador().getPuntero().getArcos().size() - 1; i >= 0; i--) {
                        Arco arco = grafo.getApuntador().getPuntero().getArcos().get(i);
                        if (!visitados.contains(arco.getNodo())) {
                            visitados.add(arco.getNodo());
                            grafo.getApuntador().avanzarNodo(arco.getNodo());
                            break;
                        } else if (arco.equals(grafo.getApuntador().getPuntero().getArcos().getLast())) {
                            grafo.retrocederNodo();
                        }
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
        while (Integer.parseInt(grafo.getApuntador().getPuntero().getNombre()) != Integer.parseInt(fin)) {
            tmp = (LinkedList<Nodo>) visitar.clone();
            for (Nodo n : tmp) {
                visitar.remove(n);
                visitados.add(n);
                grafo.getApuntador().avanzarNodo(n);
                grafo.mostrarPuntero();
                Ventana.panel.run();
                if (Integer.parseInt(grafo.getApuntador().getPuntero().getNombre()) == Integer.parseInt(fin)) {
                    break;
                } else if (!grafo.getApuntador().getPuntero().getArcos().isEmpty()) {
                    if (sentido) {
                        for (Arco arco : grafo.getApuntador().getPuntero().getArcos()) {
                            if (!visitados.contains(arco.getNodo()) && !visitar.contains(arco.getNodo())) {
                                visitar.add(arco.getNodo());
                            }
                        }
                    } else {
                        for (int i = grafo.getApuntador().getPuntero().getArcos().size() - 1; i >= 0; i--) {
                            Arco arco = grafo.getApuntador().getPuntero().getArcos().get(i);
                            if (!visitados.contains(arco.getNodo()) && !visitar.contains(arco.getNodo())) {
                                visitar.add(arco.getNodo());
                            }
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
        int index = matriz[Integer.parseInt(fin)][Integer.parseInt(grafo.getApuntador().getPuntero().getNombre())];
        Ventana.panel.run();
        while (Integer.parseInt(grafo.getApuntador().getPuntero().getNombre()) != Integer.parseInt(fin)) {
            System.out.println("Costo Actual -> " + index);
            if (sentido) {
                for (Arco a : grafo.getApuntador().getPuntero().getArcos()) {
                    int tempIndex = matriz[Integer.parseInt(fin)][Integer.parseInt(a.getNodo().getNombre())];
                    if (tempIndex < index) {
                        index = tempIndex;
                        visitados.add(grafo.getApuntador().getPuntero());
                        grafo.getApuntador().avanzarNodo(a.getNodo());
                        grafo.mostrarPuntero();
                        Ventana.panel.run();
                        break;
                    }
                }
            } else {
                for (int i = grafo.getApuntador().getPuntero().getArcos().size() - 1; i >= 0; i--) {
                    Arco a = grafo.getApuntador().getPuntero().getArcos().get(i);
                    int tempIndex = matriz[Integer.parseInt(fin)][Integer.parseInt(a.getNodo().getNombre())];
                    if (tempIndex < index) {
                        index = tempIndex;
                        visitados.add(grafo.getApuntador().getPuntero());
                        grafo.getApuntador().avanzarNodo(a.getNodo());
                        grafo.mostrarPuntero();
                        Ventana.panel.run();
                        break;
                    }
                }
                if (index == 0 && Integer.parseInt(grafo.getApuntador().getPuntero().getNombre()) != Integer.parseInt(fin)) {
                    System.out.println("Fin de la ruta tabla rota");
                    break;
                }
            }
            visitados.add(grafo.getApuntador().getPuntero());
            Ventana.panel.run();
        }
    }

    private static void BuesquedaMaximaPendiente() {
        System.out.println(" ------------- Busqueda por Maxima Pendiente ------------- ");
        grafo.goToRaiz();
        visitados = new LinkedList<>();
        int index = matriz[Integer.parseInt(fin)][Integer.parseInt(grafo.getApuntador().getPuntero().getNombre())];
        Nodo tempNodo = null;
        Ventana.panel.run();
        while (Integer.parseInt(grafo.getApuntador().getPuntero().getNombre()) != Integer.parseInt(fin)) {
            System.out.println("Costo Actual -> " + index);
            if (sentido) {
                for (Arco a : grafo.getApuntador().getPuntero().getArcos()) {
                    int tempIndex = matriz[Integer.parseInt(fin)][Integer.parseInt(a.getNodo().getNombre())];
                    if (tempIndex < index) {
                        index = tempIndex;
                        tempNodo = a.getNodo();
                    }
                }
            } else {
                for (int i = grafo.getApuntador().getPuntero().getArcos().size() - 1; i >= 0; i--) {
                    Arco a = grafo.getApuntador().getPuntero().getArcos().get(i);
                    int tempIndex = matriz[Integer.parseInt(fin)][Integer.parseInt(a.getNodo().getNombre())];
                    if (tempIndex < index) {
                        index = tempIndex;
                        tempNodo = a.getNodo();
                    }
                }
            }
            visitados.add(grafo.getApuntador().getPuntero());
            grafo.getApuntador().avanzarNodo(tempNodo);
            grafo.mostrarPuntero();
            Ventana.panel.run();
            if (index == 0 && Integer.parseInt(grafo.getApuntador().getPuntero().getNombre()) != Integer.parseInt(fin)) {
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
        while (Integer.parseInt(grafo.getApuntador().getPuntero().getNombre()) != Integer.parseInt(fin)) {
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
            if (sentido) {
                for (Arco a : grafo.getApuntador().getPuntero().getArcos()) {
                    h = matriz[Integer.parseInt(fin)][Integer.parseInt(a.getNodo().getNombre())];
                    int tempF = g + h;
                    if (tempF < f) {
                        f = tempF;
                        tempNodo = a.getNodo();
                    }
                }
            } else {
                for (int i = grafo.getApuntador().getPuntero().getArcos().size() - 1; i >= 0; i--) {
                    Arco a = grafo.getApuntador().getPuntero().getArcos().get(i);
                    h = matriz[Integer.parseInt(fin)][Integer.parseInt(a.getNodo().getNombre())];
                    int tempF = g + h;
                    if (tempF < f) {
                        f = tempF;
                        tempNodo = a.getNodo();
                    }
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
