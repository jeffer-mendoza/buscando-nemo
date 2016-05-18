package Model;

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

    public AlgoritmoBusqueda(byte[][] matriz, byte n) {
        this.matriz = matriz;
        this.n = n;
        this.numeroMetas = 0;
        this.metaActual = Personaje.NEMO;
    }

    @Override
    public String toString() {
        return "AlgoritmoBusqueda{" +
                "Nodos Creados=" + nodoCreados +
                ", Nodos Expandidos=" + nodoExpandidos +
                '}';
    }
}
