package Model;

public class Nodo {

    private byte personaje;//el identificador del personaje
    private int padre;//se guardar la referencia del padre
    private byte fila;//la posición en la fila
    private byte columna;//la posición de la columna
    private int costo;
    private byte metasCumplidas;
    private byte metaActual;
    private byte[][] matriz;
    private long costoAcomulado;//conocer el costo acumulado en el momento

    public Nodo(int padre, byte fila, byte columna, byte[][] matriz, byte metasCumplidas, byte metaActual) {
        this.padre = padre;
        this.fila = fila;
        this.columna = columna;
        this.matriz = matriz;
        this.metasCumplidas = metasCumplidas;
        this.metaActual = metaActual;
        this.personaje = this.matriz[fila][columna];
    }


    private void expandirNodo() {

    }

    /**
     * Determina si este nodo es una meta
     *
     * @return
     */
    public boolean isMeta() {
        if (this.personaje == metaActual) {
            return true;
        }
        return false;
    }

    /**
     * Actualiza los estados meta, y determina si ya
     * se alcanzo la meta global, la cual es haber encontrado
     * a los 3 personajes.
     *
     * @return
     */
    public boolean isMetaGlobal() {
        if (this.personaje == Personaje.NEMO) {
            this.metasCumplidas = 1;
            this.matriz[this.fila][this.columna]= Personaje.DISPONIBLE;
            this.metaActual = Personaje.MARLIN;

            return false;
        }
        if (this.personaje == Personaje.MARLIN) {
            this.metasCumplidas = 2;
            this.metaActual = Personaje.DORI;
            this.matriz[this.fila][this.columna]= Personaje.DISPONIBLE;

            return false;
        } else {
            return true;
        }
    }

    /**
     * Método que permite mostrar la matriz
     */
    public void mostrarMatriz()
    {
        int n = this.matriz.length;

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println();
        }
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

    public byte getMetasCumplidas() {
        return metasCumplidas;
    }

    public void setMetasCumplidas(byte metasCumplidas) {
        this.metasCumplidas = metasCumplidas;
    }

    public byte[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(byte[][] matriz) {
        this.matriz = matriz;
    }

    public byte getMetaActual() {
        return metaActual;
    }

    public void setMetaActual(byte metaActual) {
        this.metaActual = metaActual;
    }

    @Override
    public String toString() {
        return "(" + fila + ", " + columna + ')';
    }
}
