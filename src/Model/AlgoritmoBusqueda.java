package Model;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by jeffer on 18/05/16.
 */
public class AlgoritmoBusqueda {
    protected byte matriz[][];//tablero del juego
    protected byte n;//tamano de la matriz

    protected long nodoCreados = 0;//los nodos que son creados (size = 2^64)
    protected long nodoExpandidos = 0;//los nodos que son expandidos (size = 2^64)
    protected double costoTotal = 0;//el costo total de la solución más optima

    protected byte numeroMetas = 0;//contador que permite conocer cuando se llega a la última meta

    //historial de padres
    protected LinkedList<Nodo> historialPadres;//guarda el historial de todos los padres
    protected int idsHistorialPadres = 0;

    public AlgoritmoBusqueda(byte[][] matriz, byte n) {
        this.matriz = matriz;
        this.n = n;
        this.historialPadres = new LinkedList<Nodo>();//se inicializa la cola
        this.idsHistorialPadres = 0;

    }

    @Override
    public String toString() {
        double factorRamificacion = Math.ceil((double)this.nodoCreados / this.nodoExpandidos);
        double nivel = Math.log10(this.nodoCreados) / Math.log10(factorRamificacion);
        return
                "\nPasos Solución: "+this.mostrarRuta() +
                "\nNodos Expandidos: " + this.nodoExpandidos +
                "\nNodos Creados: " + this.nodoCreados +
                "\nCosto total Solución: " + this.costoTotal +
                "\nFactor de Ramificación: " + factorRamificacion+
                "\nNivel: " + Math.ceil(nivel);
    }

    /**
     * Metodo que permite a partir de la posición de la solucion obtener
     * todos sus ancestros
     */
    protected String mostrarRuta() {

        Nodo nodo = this.historialPadres.getLast();
        int index = nodo.getPadre();
        String ruta = nodo.toString();
        while (true) {
            index = nodo.getPadre();
            nodo = this.historialPadres.get(index);
            nodo.mostrarMatriz();
            System.out.println("----------");
            ruta = nodo + "-->"+ruta;
            if(index == 0){
                break;
            }
        }
        return ruta;
    }

    /**
     * Identifica donde se cuentra el robot inicialmente
     *
     * @return Nodo
     */
    public Nodo getNodoInicial()
    {
        byte posX = 0;
        byte posY = 0;
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz.length; j++) {
                if(matriz[i][j] == 0)
                {
                    posX = i;
                    posY = j;
                }
            }
        }

        return  new Nodo(0, posX, posY, this.matriz, (byte) 0, Personaje.NEMO, (byte)0);
    }

}
