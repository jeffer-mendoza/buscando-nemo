package Model.Informada;

import java.awt.Point;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.AlgoritmoBusqueda;
import Model.Comparador.ComparadorCostosAvara;
import Model.Nodo;
import Model.Personaje;

public class Avara extends AlgoritmoBusqueda {

    private Comparator<Nodo> comparador;
    private PriorityQueue<Nodo> cola;

    private double[] distanciasMetas = new double[2];//almacena las distancia de meta1-meta2 + meta2-meta3 y meta2-meta3
    private byte[] X = new byte[3];//posiciones de metas en filas
    private byte[] Y = new byte[3];//posiciones de metas en columnas

    public Avara(byte[][] matriz, byte n, int tipoHeuristica) {
        super(matriz, n);
        comparador = new ComparadorCostosAvara();
        cola = new PriorityQueue<Nodo>(10, comparador);
    }

    public void run() {
        Nodo nodoActual = null;
        buscarNodoInicialyMetas();

        while (true) {
            nodoActual = this.cola.remove();
            System.out.println(nodoActual);
            if (nodoActual.isMeta()) {
                if (nodoActual.isMetaGlobal()) {//verifica si ya se alcanzo la meta global y actualiza las variables de estado
                    this.costoTotal = nodoActual.getCostoAcumulado();
                    this.historialPadres.add(nodoActual);
                    this.idsHistorialPadres++;

                    break;
                }
            }

            this.expandirNodo(nodoActual);//expandir el nodo
        }

        System.out.println(this.toString());
    }

    public void expandirNodo(Nodo nodo) {
        this.nodoExpandidos++;//se ha expandido un nuevo nodo
        this.historialPadres.add(nodo);//se agrega nodo al historial de padres
        byte i = nodo.getFila();//obtiene la fila del nodo actual
        byte j = nodo.getColumna();//obtiene la columna del noto actual
        Nodo nodoAbuelo = this.historialPadres.get(nodo.getPadre());

        //System.out.println(nodoActual+" - "+nodoActual.getValorHeuristica());
        if (i + 1 < this.n) {
            this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodo, nodoAbuelo);
        }

        if (j + 1 < this.n) {
            this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodo, nodoAbuelo);
        }

        if (i - 1 >= 0) {
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodo, nodoAbuelo);
        }

        if (j - 1 >= 0) {
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodo, nodoAbuelo);
        }


        this.idsHistorialPadres++;//aumenta el identificador de indexamiento
    }

    /**
     * Metodo que crea un nuevo nodo, también se comprueba
     * que el nodo no se este devolviendo
     *
     * @param i          fila de la nueva posicion
     * @param j          columna de la nueva posicion
     * @param padreId    identificador del padre
     * @param nodoActual nodo padre del nodo que será creado
     * @return
     */
    private void crearNodo(byte i, byte j, int padreId, Nodo nodoActual, Nodo nodoAbuelo) {
        if (nodoActual.getMatriz()[i][j] == Personaje.ROCA) {

            return;
        }
        if (nodoActual.getMatriz()[i][j] == Personaje.ACUAMAN) {
            //no crea el nodo solo se suma a los expandidos
            this.nodoExpandidos++;
            return;
        }

        if (!(i == nodoAbuelo.getFila() && j == nodoAbuelo.getColumna() && nodoAbuelo.getMetaActual() == nodoActual.getMetaActual())) {
            Nodo nodo = new Nodo(padreId, i, j, nodoActual.getMatriz(), nodoActual.getMetasCumplidas(), nodoActual.getMetaActual(), nodoActual.getFactorReduccion());
            nodo.setCostoAcumulado(nodoActual.getCostoAcumulado());
            nodo.setValorHeuristica(this.heuristica(i, j, nodo.getMetasCumplidas()));
            this.cola.add(nodo);
            this.nodoCreados++;

        }
    }

    /**
     * Función que implementa la heuristica.
     *
     * Heuristica: es la suma de las lineas rectas entre posicion actual y las distancias
     * rectas entre las metas restantes
     *
     * @param x
     * @param y
     * @param metasCumplidas
     * @return
     */
    public double heuristica(byte x, byte y, byte metasCumplidas) {
        if(metasCumplidas == 0){

            return (distanciaPuntos(x,this.X[0],y,this.Y[0]) + this.distanciasMetas[0]) * 0.5 ;
        }
        if(metasCumplidas == 1){

            return (distanciaPuntos(x,this.X[1],y,this.Y[1]) + this.distanciasMetas[1])* 0.5;
        }
        if(metasCumplidas == 2){

            return (distanciaPuntos(x,this.X[2],y,this.Y[2]))* 0.5;
        }

        return 0;
    }

    public void buscarNodoInicialyMetas() {
        byte posX = 0;
        byte posY = 0;
        byte personaje;
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz.length; j++) {
                personaje = matriz[i][j];

                if (personaje == Personaje.ROBOT) {
                    posX = i;
                    posY = j;
                } else {
                    if (personaje == Personaje.NEMO) {
                        this.X[0] = i;
                        this.Y[0] = j;
                    } else {
                        if (personaje == Personaje.MARLIN) {
                            this.X[1] = i;
                            this.Y[1] = j;
                        } else {
                            if (personaje == Personaje.DORI) {
                                this.X[2] = i;
                                this.Y[2] = j;
                            }
                        }
                    }
                }
            }
        }
        double BC = distanciaPuntos(this.X[2],this.X[1],this.Y[2],this.Y[1]);
        distanciasMetas[0] = distanciaPuntos(this.X[1],this.X[0],this.Y[1],this.Y[0]) + BC;
        distanciasMetas[1] = BC;
        Nodo primerNodo = new Nodo(0, posX, posY, this.matriz.clone(), (byte) 0, Personaje.NEMO, (byte) 0);
        primerNodo.setValorHeuristica(this.heuristica(posX, posY, primerNodo.getMetasCumplidas()));
        cola.add(primerNodo);
    }

    private double distanciaPuntos(byte x0, byte x1, byte y0, byte y1){

        return Math.sqrt(Math.pow(x0 - x1,2) + Math.pow(y0 - y1,2));
    }
}
