package noinformada;

import Main.Personaje;

import java.util.ArrayList;
import java.util.LinkedList;

import Model.AlgoritmoBusqueda;

/**
 * Created by jeffer on 13/05/16.
 */
public class Amplitud extends AlgoritmoBusqueda {

    LinkedList<byte[]> cola;//estructura que manejara los nodos que se vayan creando
    ArrayList<byte[]> historial;//guarda el historial de todos los padres

    public Amplitud(byte[][] matriz, byte n) {
        super(matriz,n);
        this.cola = new LinkedList<byte[]>();//se inicializa la cola
        byte[] nodo = {1, 0, 0, -1, -1};
        this.expandirNodo(nodo);//se expanden los nodos de la raiz
    }



    public void run() {
        System.out.println(this.n);
        while (this.numeroMetas < 3) {
            byte[] nodo = this.cola.getFirst();//obtiene el nodo de izquierda a derecha
            if (nodo[0] == this.metaActual) {//verifica si es meta
                this.numeroMetas++;
                if (this.numeroMetas == 1) {
                    this.metaActual = Personaje.MARLIN;
                } else {
                    if (this.numeroMetas == 2) {
                        this.metaActual = Personaje.DORI;
                    }else{
                        //si se da es porque se cumplieron las tres metas y no permite expandir mas nodos
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
    private void expandirNodo(byte[] nodo) {
        this.nodoExpandidos++;//se ha expandido un nuevo nodo
        byte i = nodo[1];//obtiene la fila del nodo actual
        byte j = nodo[2];//obtiene la columna del noto actual
        if (i - 1 >= 0) {
            this.crearNodo((byte) (i - 1), j, i, j);
        }
        if (i + 1 < this.n) {
            this.crearNodo((byte) (i + 1), j, i, j);
        }
        if (j - 1 >= 0) {
            this.crearNodo(i, (byte) (j - 1), i, j);
        }
        if (j + 1 < this.n) {
            this.crearNodo(i, (byte) (j + 1), i, j);
        }

    }

    /**
     * Metodo que crea el nodo con la información más relevante:
     * <p>
     * Identificación = El número que representa el nodo en la matriz
     * Posición (i,j) = la posición en la matriz
     * Posición (i,j) del padre = la posición en la matriz del padre
     *
     * @param i      fila de la nueva posición
     * @param j      columna de la nueva posición
     * @param iPadre fila del padre
     * @param jPadre columna del padre
     * @param id identificador del padre
     * @return
     */
    private void crearNodo(byte i, byte j, byte iPadre, byte jPadre) {
        byte[] nodo = {this.matriz[i][j], i, j, iPadre, jPadre};
        this.cola.add(nodo);
        this.nodoCreados++;
    }


}
