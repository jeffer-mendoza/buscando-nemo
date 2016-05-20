package Model;

public class Nodo {

    private byte personaje;//el identificador del personaje
    private int padre;//se guardar la referencia del padre
    private byte fila;//la posición en la fila
    private byte columna;//la posición de la columna
    private int costo;
    private boolean[] metasCumplidas;
    private byte[][] matriz;

    public Nodo(int padre, byte fila, byte columna, byte[][] matriz, boolean[] metasCumplidas) {
        this.padre = padre;
        this.fila = fila;
        this.columna = columna;
        this.matriz = matriz;
        this.metasCumplidas = metasCumplidas;
    }


    private void expandirNodo() {

    }


    /************************
     * Metodos Get y Set
     ************************/

    public byte getPersonaje() {
        return personaje;
    }

    public void setPersonaje(byte personaje) {
        this.personaje = personaje;
    }

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }

    public byte getFila() {
        return fila;
    }

    public void setFila(byte fila) {
        this.fila = fila;
    }

    public byte getColumna() {
        return columna;
    }

    public void setColumna(byte columna) {
        this.columna = columna;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public boolean[] getMetasCumplidas() {
        return metasCumplidas;
    }

    public void setMetasCumplidas(boolean[] metasCumplidas) {
        this.metasCumplidas = metasCumplidas;
    }

    public byte[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(byte[][] matriz) {
        this.matriz = matriz;
    }

    @Override
    public String toString() {
        return "(" + fila + ", " + columna + ')';
    }
}
