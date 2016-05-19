package Model;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by jeffer on 18/05/16.
 */
public class AlgoritmoBusqueda {
    protected byte matriz[][];//tablero del juego
    protected byte n;//tamano de la matriz

    protected byte metaActual; //objetivo actual que debe ser búscado
    protected byte numeroMetas = 0;//numero de metas cumplidas

    protected long nodoCreados = 0;//los nodos que son creados (size = 2^64)
    protected long nodoExpandidos = 0;//los nodos que son expandidos (size = 2^64)

    //historial de padres
    protected LinkedList<int[]> historialPadres;//guarda el historial de todos los padres
    protected int idsHistorialPadres = 0;

    public AlgoritmoBusqueda(byte[][] matriz, byte n) {
        this.matriz = matriz;
        this.n = n;
        this.numeroMetas = 0;
        this.metaActual = Personaje.NEMO;
        this.historialPadres = new LinkedList<int[]>();//se inicializa la cola
        this.idsHistorialPadres = 0;

    }

    @Override
    public String toString() {
        return "AlgoritmoBusqueda{" +
                "Nodos Creados=" + nodoCreados +
                ", Nodos Expandidos=" + nodoExpandidos +
                '}';
    }

    /**
     * Metodo que permite a partir de la posición de la solucion obtener
     * todos sus ancestros
     */
    protected void mostrarRuta() {
        int nodo[] = this.historialPadres.getLast();
        int id = 0;
        while (true) {
            System.out.println(nodo[0] + "- (" + nodo[1] + ", " + nodo[2] +")");
            id = nodo[3];
            nodo = this.historialPadres.get(id);
            if(id == 0){
                break;
            }
        }
        System.out.println("Root" );
    }

}
