package Model.NoInformada;

import Model.Personaje;

import java.util.ArrayList;
import java.util.LinkedList;

import Model.AlgoritmoBusqueda;

/**
 * Created by jeffer on 13/05/16.
 */
public class Amplitud extends AlgoritmoBusqueda {

    private LinkedList<int[]> cola;//estructura que manejara los nodos que se vayan creando


    public Amplitud(byte[][] matriz, byte n) {
        super(matriz,n);
        this.cola = new LinkedList<int[]>();//se inicializa la cola
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
     * <p>
     * Teoricamente es la expasión del nodo que no son meta.
     */
    private void expandirNodo(int[] nodo) {
        this.nodoExpandidos++;//se ha expandido un nuevo nodo
        this.historialPadres.add(nodo);//se agrega nodo al historial de padres
        int i = nodo[1];//obtiene la fila del nodo actual
        int j = nodo[2];//obtiene la columna del noto actual
        if (i - 1 >= 0) {
            this.crearNodo(i - 1, j, this.idsHistorialPadres);
        }
        if (i + 1 < this.n) {
            this.crearNodo(i + 1, j, this.idsHistorialPadres);
        }
        if (j - 1 >= 0) {
            this.crearNodo(i, j - 1, this.idsHistorialPadres);
        }
        if (j + 1 < this.n) {
            this.crearNodo(i, j + 1, this.idsHistorialPadres);
        }
        this.idsHistorialPadres++;//aumenta el identificador de indexamiento

    }

    /**
     * Metodo que crea el nodo con la información más relevante:
     *
     *[0] Identificación = El número que representa el nodo en la matriz
     *[1,2] Posición (i,j) = la posición en la matriz
     *[3] id = referencia al padre el el array historial
     *
     * @param i      fila de la nueva posición
     * @param j      columna de la nueva posición
     * @param padre     identificador del padre
     * @return
     */
    private void crearNodo(int i, int j, int padre) {
        int[] nodo = {this.matriz[i][j],i, j, padre};
        this.cola.add(nodo);
        this.nodoCreados++;
    }



}
