package Model.NoInformada;

import Model.Nodo;
import Model.Personaje;

import java.util.LinkedList;

import Model.AlgoritmoBusqueda;

/**
 * Created by jeffer on 13/05/16.
 */
public class Amplitud extends AlgoritmoBusqueda {

    private LinkedList<Nodo> cola;//estructura que manejara los nodos que se vayan creando


    public Amplitud(byte[][] matriz, byte n) {
        super(matriz,n);
        this.cola = new LinkedList<Nodo>();//se inicializa la cola
        int[] nodo = {0, 0, 0, 0};
        this.expandirNodo(nodo);//se expanden los nodos de la raiz
    }



    public void run() {
        while (this.numeroMetas < 3) {
            int[] nodo = this.cola.getFirst();//obtiene el nodo de izquierda a derecha
            if (nodo[0] == this.metaActual) {//verifica si es meta
                this.numeroMetas++;
                //todo borrar la meta de la matriz
                if (this.numeroMetas == 1) {
                    this.metaActual = Personaje.MARLIN;
                } else {
                    if (this.numeroMetas == 2) {
                        this.metaActual = Personaje.DORI;
                    }else{
                        //si se da es porque se cumplieron las tres metas y no permite expandir mas nodos
                        this.historialPadres.add(nodo);
                        this.mostrarRuta();
                        break;
                    }
                }
                cola = new LinkedList<>();//se reinicia el camino apartir de este nodo
                this.expandirNodo(nodo);//se expanden los nodos apartir del nodo meta
                continue;
            }
            cola.removeFirst();//remover este nodo evaluado que no es meta
            this.expandirNodo(nodo);//expandir el nodo
        }
        System.out.println(this.toString());
    }


    /**
     * Determina los posibles cambios de estado que tiene el entorno, basicamente
     * agrega a la cola los posibles movimientos en el tablero.
     * Teoricamente es la expasión del nodo que no son meta.
     *
     * @param nodo
     */
    private void expandirNodo(Nodo nodo) {
        this.nodoExpandidos++;//se ha expandido un nuevo nodo
        this.historialPadres.add(nodo);//se agrega nodo al historial de padres
        byte i = nodo.getFila();//obtiene la fila del nodo actual
        byte j = nodo.getColumna();//obtiene la columna del noto actual
        if (i - 1 >= 0) {
            this.crearNodo((byte)(i - 1), j, this.idsHistorialPadres, nodo);
        }
        if (i + 1 < this.n) {
            this.crearNodo((byte)(i + 1), j, this.idsHistorialPadres, nodo);
        }
        if (j - 1 >= 0) {
            this.crearNodo(i, (byte)(j - 1), this.idsHistorialPadres, nodo);
        }
        if (j + 1 < this.n) {
            this.crearNodo(i, (byte)(j + 1), this.idsHistorialPadres, nodo);
        }
        this.idsHistorialPadres++;//aumenta el identificador de indexamiento

    }

    /**
     * Metodo que crea un nuevo nodo
     *
     *
     * @param i      fila de la nueva posición
     * @param j      columna de la nueva posición
     * @param padre     identificador del padre
     * @return
     */
    private void crearNodo(byte i, byte j, int padreId, Nodo nodoPadre) {
        Nodo nodo = new Nodo(padreId,i, j,nodoPadre.getMatriz(),nodoPadre.getMetasCumplidas());
        this.cola.add(nodo);
        this.nodoCreados++;
    }



}
