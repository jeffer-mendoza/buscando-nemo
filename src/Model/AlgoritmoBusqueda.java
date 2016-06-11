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
    protected byte nivel;

    public AlgoritmoBusqueda(byte[][] matriz, byte n) {
        this.matriz = matriz;
        this.n = n;
        this.historialPadres = new LinkedList<Nodo>();//se inicializa la cola
        this.idsHistorialPadres = 0;

    }

    @Override
    public String toString() {
        return
                this.mostrarRuta() +
                "\nNodos Expandidos: " + this.nodoExpandidos +
                "\nNodos Creados: " + this.nodoCreados +
                "\nCosto total Solución: " + this.costoTotal +
                "\nFactor de Ramificación: " +  Math.ceil(Math.pow(this.nodoCreados,1d/this.nivel))+
                "\nNivel: " + this.nivel;
    }

    /**
     * Metodo que permite a partir de la posición de la solucion obtener
     * todos sus ancestros
     */
    protected String mostrarRuta() {

        Nodo nodo = this.historialPadres.getLast();
        int index = nodo.getPadre();
        String ruta = nodo.toString();
        String costo = nodo.getCostoAcumulado()+"";
        String matriz = nodo.getFila()+","+nodo.getColumna()+ "\n" +nodo.strMatriz();
        while (true) {
            index = nodo.getPadre();
            nodo = this.historialPadres.get(index);
            nodo.setRobot();
            matriz =  nodo.getFila()+","+nodo.getColumna()+ "\n" +nodo.strMatriz() + matriz;
            ruta = nodo + "-->"+ruta;
            costo = nodo.getCostoAcumulado() + " --> "+costo;
            this.nivel++;
            if(index == 0){
                break;
            }
        }
        matriz = this.n + "\n" + matriz;
        return matriz + "\nPasos solución:"+ruta+"\nCostos nodos: "+costo;
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
