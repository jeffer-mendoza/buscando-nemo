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
        super(matriz, n);
        this.cola = new LinkedList<Nodo>();//se inicializa la cola
        Nodo nodo = new Nodo(0, (byte) 0, (byte) 0, this.matriz.clone(), (byte) -99, Personaje.NEMO, (byte) 0);
        this.cola.add(nodo);
        this.expandirNodo(nodo);//se expanden los nodos de la raiz
    }


    public void run() {
        Nodo nodo;
        while (true) {
            nodo = this.cola.getFirst();//obtiene el nodo de izquierda a derecha
            if (nodo.isMeta()) {//verifica si es meta
                //System.out.println(nodo);
                //todo borrar la meta de la matriz
                if (nodo.isMetaGlobal()) {//verifica si ya se alcanzo la meta global y actualiza las variables de estado
                    this.costoTotal = nodo.getCostoAcomulado();
                    this.historialPadres.add(nodo);
                    this.idsHistorialPadres++;

                    break;
                }
            }
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
        cola.removeFirst();//remover este nodo evaluado que no es meta
        this.nodoExpandidos++;//se ha expandido un nuevo nodo
        this.historialPadres.add(nodo);//se agrega nodo al historial de padres
        byte i = nodo.getFila();//obtiene la fila del nodo actual
        byte j = nodo.getColumna();//obtiene la columna del noto actual
        if (i - 1 >= 0) {
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodo);
        }
        if (i + 1 < this.n) {
            this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodo);
        }
        if (j - 1 >= 0) {
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodo);
        }
        if (j + 1 < this.n) {
            this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodo);
        }
        this.idsHistorialPadres++;//aumenta el identificador de indexamiento

    }

    /**
     * Metodo que crea un nuevo nodo, también se comprueba
     * que el nodo no se este devolviendo
     *
     * @param i         fila de la nueva posición
     * @param j         columna de la nueva posición
     * @param padreId   identificador del padre
     * @param nodoPadre nodo padre del nodo que será creado
     * @return
     */
    private int crearNodo(byte i, byte j, int padreId, Nodo nodoPadre) {
        if (nodoPadre.getMatriz()[i][j] == Personaje.ROCA) {

            return 0;
        }
        if (nodoPadre.getMatriz()[i][j] == Personaje.ACUAMAN) {
            //no crea el nodo solo se suma a los expandidos
            this.nodoExpandidos++;
            return 0;
        }
        Nodo nodoAbuelo = this.historialPadres.get(nodoPadre.getPadre());

//
//        if (this.cola.size() == 0) {
//            nodoAbuelo = null;
//        }
       // if (!nodoPadre.equals(nodoAbuelo)) {
            Nodo nodo = new Nodo(padreId, i, j, nodoPadre.getMatriz(), nodoPadre.getMetasCumplidas(), nodoPadre.getMetaActual(), nodoPadre.getFactorReduccion());
            nodo.setCostoAcomulado(nodoPadre.getCostoAcomulado());
            this.cola.add(nodo);
            this.nodoCreados++;
       // }


        return 1;
    }

}
