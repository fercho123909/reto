import java.util.ArrayList;
import java.util.List;

public class ABBRecorridos {
    private static class Nodo {
        int dato;
        Nodo izq;
        Nodo der;

        Nodo(int dato) {
            this.dato = dato;
        }
    }

    private Nodo raiz;

    private Nodo insertar(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }
        if (valor < nodo.dato) {
            nodo.izq = insertar(nodo.izq, valor);
        } else if (valor > nodo.dato) {
            nodo.der = insertar(nodo.der, valor);
        }
        return nodo;
    }

    private void insertar(int valor) {
        raiz = insertar(raiz, valor);
    }

    private static void traza(String mensaje, int profundidad) {
        StringBuilder sangria = new StringBuilder();
        for (int i = 0; i < profundidad; i++) {
            sangria.append("  ");
        }
        System.out.println(sangria + mensaje);
    }

    private static void preorden(Nodo nodo, int profundidad) {
        if (nodo == null) {
            traza("null -> regresa", profundidad);
            return;
        }
        traza("entra " + nodo.dato, profundidad);
        System.out.println("VISITA " + nodo.dato);
        preorden(nodo.izq, profundidad + 1);
        preorden(nodo.der, profundidad + 1);
    }

    private static void inorden(Nodo nodo, int profundidad) {
        if (nodo == null) {
            traza("null -> regresa", profundidad);
            return;
        }
        traza("entra " + nodo.dato, profundidad);
        inorden(nodo.izq, profundidad + 1);
        System.out.println("VISITA " + nodo.dato);
        inorden(nodo.der, profundidad + 1);
    }

    private static void posorden(Nodo nodo, int profundidad) {
        if (nodo == null) {
            traza("null -> regresa", profundidad);
            return;
        }
        traza("entra " + nodo.dato, profundidad);
        posorden(nodo.izq, profundidad + 1);
        posorden(nodo.der, profundidad + 1);
        System.out.println("VISITA " + nodo.dato);
    }

    private static void recolectar(Nodo nodo, char orden, List<Integer> valores) {
        if (nodo == null) {
            return;
        }
        if (orden == 'P') {
            valores.add(nodo.dato);
        }
        recolectar(nodo.izq, orden, valores);
        if (orden == 'I') {
            valores.add(nodo.dato);
        }
        recolectar(nodo.der, orden, valores);
        if (orden == 'O') {
            valores.add(nodo.dato);
        }
    }

    private static List<Integer> recorrido(Nodo nodo, char orden) {
        List<Integer> valores = new ArrayList<>();
        recolectar(nodo, orden, valores);
        return valores;
    }

    private static void imprimirArbol(Nodo nodo, String prefijo, String etiqueta, boolean ultimo) {
        if (nodo == null) {
            return;
        }
        System.out.println(prefijo + etiqueta + nodo.dato);
        String siguientePrefijo = prefijo + (etiqueta.isEmpty() ? "" : (ultimo ? "    " : "|   "));
        if (nodo.izq != null) {
            imprimirArbol(nodo.izq, siguientePrefijo, "|-- izq: ", nodo.der == null);
        }
        if (nodo.der != null) {
            imprimirArbol(nodo.der, siguientePrefijo, "\\-- der: ", true);
        }
    }

    private static ABBRecorridos crearArbol(int... valores) {
        ABBRecorridos arbol = new ABBRecorridos();
        for (int valor : valores) {
            arbol.insertar(valor);
        }
        return arbol;
    }

    private static void mostrarCasosLimite() {
        ABBRecorridos vacio = new ABBRecorridos();
        ABBRecorridos uno = crearArbol(10);
        ABBRecorridos cadena = crearArbol(10, 20, 30, 40);
        ABBRecorridos repetido = crearArbol(30, 30);

        System.out.println("Arbol vacio: " + recorrido(vacio.raiz, 'I'));
        System.out.println("Un nodo (pre/in/pos): "
                + recorrido(uno.raiz, 'P') + " / "
                + recorrido(uno.raiz, 'I') + " / "
                + recorrido(uno.raiz, 'O'));
        System.out.println("Cadena 10, 20, 30, 40 (pre/in/pos): "
                + recorrido(cadena.raiz, 'P') + " / "
                + recorrido(cadena.raiz, 'I') + " / "
                + recorrido(cadena.raiz, 'O'));
        System.out.println("Repetido 30 (inorden; se ignora el duplicado): "
                + recorrido(repetido.raiz, 'I'));
    }

    public static void main(String[] args) {
        ABBRecorridos arbol = crearArbol(45, 25, 70, 10, 30, 60, 90, 27, 65);

        System.out.println("ARBOL RESULTANTE");
        imprimirArbol(arbol.raiz, "", "", true);
        System.out.println();

        System.out.println("RECORRIDOS");
        System.out.println("Preorden: " + recorrido(arbol.raiz, 'P'));
        System.out.println("Inorden:  " + recorrido(arbol.raiz, 'I'));
        System.out.println("Posorden: " + recorrido(arbol.raiz, 'O'));
        System.out.println();

        System.out.println("TRAZA PREORDEN");
        preorden(arbol.raiz, 0);
        System.out.println("\nTRAZA INORDEN");
        inorden(arbol.raiz, 0);
        System.out.println("\nTRAZA POSORDEN");
        posorden(arbol.raiz, 0);
        System.out.println("\nCASOS LIMITE");
        mostrarCasosLimite();
    }
}