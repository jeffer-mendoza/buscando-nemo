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
        this.cola.add(this.getNodoInicial());
        this.expandirNodo(this.cola.getFirst());//se expanden los nodos de la raiz
    }


    public String run() {
        Nodo nodo;
        while (true) {
            nodo = this.cola.getFirst();//obtiene el nodo de izquierda a derecha
            if (nodo.isMeta()) {//verifica si es meta
                if (nodo.isMetaGlobal()) {//verifica si ya se alcanzo la meta global y actualiza las variables de estado
                    this.costoTotal = nodo.getCostoAcumulado();
                    this.historialPadres.add(nodo);
                    this.idsHistorialPadres++;

                    break;
                }
            }
            this.expandirNodo(nodo);//expandir el nodo
        }
        return this.toString();
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
        byte i = nodo.getFila();//obtiene la fila del nodo actual
        byte j = nodo.getColumna();//obtiene la columna del noto actual
        
        /*
         * Si el nodo a expander tiene a acuaman entonces no puede crearle hijos
         */
        if (nodo.getMatriz()[i][j] == Personaje.ACUAMAN) {
			return;
		}
        
        this.historialPadres.add(nodo);//se agrega nodo al historial de padres
        Nodo nodoAbuelo = this.historialPadres.get(nodo.getPadre());
        
        if (i + 1 < this.n) {//ir a abajo
            this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodo, nodoAbuelo);
        }
        if (j + 1 < this.n) {//ir a la derecha
            this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodo, nodoAbuelo);
        }
        if (i - 1 >= 0) {//ir arriba
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodo, nodoAbuelo);
        }
        if (j - 1 >= 0) {//i a la izquierda
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodo, nodoAbuelo);
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
     * @param nodoActual nodo padre del nodo que será creado
     */
    private void crearNodo(byte i, byte j, int padreId, Nodo nodoActual, Nodo nodoAbuelo) {
        if (nodoActual.getMatriz()[i][j] == Personaje.ROCA) {

            return;
        }

        if (!(nodoAbuelo.getFila() == i && nodoAbuelo.getColumna() == j && nodoAbuelo.getMetaActual() == nodoActual.getMetaActual())) {
            Nodo nodo = new Nodo(padreId, i, j, nodoActual.getMatriz(), nodoActual.getMetasCumplidas(), nodoActual.getMetaActual(), nodoActual.getFactorReduccion());
            nodo.setCostoAcumulado(nodoActual.getCostoAcumulado());
            this.cola.add(nodo);
            this.nodoCreados++;
        }
    }

}
