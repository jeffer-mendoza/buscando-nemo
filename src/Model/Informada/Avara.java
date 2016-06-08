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
    private Point[] listadoMetas;

    public Avara(byte[][] matriz, byte n, int tipoHeuristica) {
        super(matriz, n);
        comparador = new ComparadorCostosAvara();
        cola = new PriorityQueue<Nodo>(10, comparador);
        listadoMetas = new Point[3];
    }

    public void run() {
        Nodo nodoActual = null;
        buscarNodoInicialyMetas();

        while (true) {
            nodoActual = this.cola.remove();

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
        if (i - 1 >= 0) {
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodo, nodoAbuelo);
        }

        if (i + 1 < this.n) {
            this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodo, nodoAbuelo);
        }

        if (j - 1 >= 0) {
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodo, nodoAbuelo);
        }

        if (j + 1 < this.n) {
            this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodo,nodoAbuelo);
        }

        this.idsHistorialPadres++;//aumenta el identificador de indexamiento
    }

    /**
     * Metodo que crea un nuevo nodo, también se comprueba
     * que el nodo no se este devolviendo
     *
     * @param i         fila de la nueva posicion
     * @param j         columna de la nueva posicion
     * @param padreId   identificador del padre
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
            Nodo nodo = new Nodo(padreId, i, j, nodoActual.getMatriz(), nodoActual.getMetasCumplidas(), nodoActual.getMetaActual(),nodoActual.getFactorReduccion());
            nodo.setCostoAcumulado(nodoActual.getCostoAcumulado());
            nodo.setValorHeuristica(this.primeraHeuristica(i, j, listadoMetas[nodoActual.getMetasCumplidas()]));
            //System.out.println(nodoActual.getValorHeuristica()+" - "+nodoActual.getCostoAcumulado()+" - "+nodo.getMetaActual());
            System.out.println(nodo + " - " + nodo.getValorHeuristica() + " - " + nodo.getMetasCumplidas());
            this.cola.add(nodo);
            this.nodoCreados++;

        }
    }

    /**
     * Metodo que retorna el valor de aplicar la heuristica del valor manhattan
     *
     * @param x
     * @param y
     * @param coordenada
     * @return
     */
    public double primeraHeuristica(int x, int y, Point coordenada) {
        double heuristica = Math.abs(x - coordenada.getX()) + Math.abs(y - coordenada.getY());
        //System.out.println(x+" - "+y+" - "+coordenada.getX()+" - "+coordenada.getY());
        return heuristica;
    }

    public void buscarNodoInicialyMetas() {
        byte posX = 0;
        byte posY = 0;
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz.length; j++) {

                switch (matriz[i][j]) {
                    case 0:
                        posX = i;
                        posY = j;
                        break;
                    case 7:
                        this.listadoMetas[0] = new Point(i, j);
                        break;
                    case 6:
                        this.listadoMetas[1] = new Point(i, j);
                        break;
                    case 5:
                        this.listadoMetas[2] = new Point(i, j);
                        break;
                    default:
                        break;
                }
            }
        }

        Nodo primerNodo = new Nodo(0, posX, posY, this.matriz.clone(), (byte) 0, Personaje.NEMO, (byte) 0);
        primerNodo.setValorHeuristica(this.primeraHeuristica(posX, posY, listadoMetas[0]));
        cola.add(primerNodo);
    }
}
