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
    private double costoAcumulado;//conocer el costo acumulado en el momento
    private double valorHeuristica;
    private double fN;

    //factor reducción permite implementar la ayuda de la tortuga en 4 pasos y será decrementando a medida que es usada
    //cuando llega a 0 es porque ya se ha agotado y no se tiene este beneficio de reducir costos (ver método setCostoAcumulado)
    private byte factorReduccion;


    public Nodo(int padre, byte fila, byte columna, byte[][] matriz, byte metasCumplidas, byte metaActual, byte factorReduccion) {
        this.padre = padre;
        this.fila = fila;
        this.columna = columna;
        this.cloneMatriz(matriz);
        this.metasCumplidas = metasCumplidas;
        this.metaActual = metaActual;
        this.personaje = this.matriz[fila][columna];
        this.setFactorReduccion(factorReduccion);
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
            this.matriz[this.fila][this.columna] = Personaje.DISPONIBLE;
            this.metaActual = Personaje.MARLIN;

            return false;
        }
        if (this.personaje == Personaje.MARLIN) {
            this.metasCumplidas = 2;
            this.metaActual = Personaje.DORI;
            this.matriz[this.fila][this.columna] = Personaje.DISPONIBLE;

            return false;
        } else {
            return true;
        }
    }

    /**
     * Método que permite mostrar la matriz
     */
    public void mostrarMatriz() {
        int n = this.matriz.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matriz[i][j] + " ");
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

    public double getCostoAcumulado() {
        return costoAcumulado;
    }

    public double getValorHeuristica() {
        return valorHeuristica;
    }

    public void setValorHeuristica(double valorHeuristica) {
        this.valorHeuristica = valorHeuristica;
    }

    public double getfN() {
        return fN;
    }

    public void setfN() {
        this.fN = this.costoAcumulado+this.valorHeuristica;
    }

    /**
     * En este método se implementa la sumatorio de costos y se aplica la estrategia del factor de reducción
     * el cual es accionado cuando se halla una tortuga, y los siguientes 4 pasos se tiene una reducción del 50% en los
     * costos de las casillas por donde pase el robot.
     *
     * @param costoAcumulado
     */
    public void setCostoAcumulado(double costoAcumulado) {
        if (this.factorReduccion > 0) {
            this.costoAcumulado = costoAcumulado + Personaje.getCosto(this.personaje) * 0.5d;
            this.factorReduccion--;
        } else {
            this.costoAcumulado = costoAcumulado + Personaje.getCosto(this.personaje);
        }

    }

    public byte getFactorReduccion() {
        return factorReduccion;
    }

    /**
     * Al setear el factor de reducción(ver descripción en la definición del atributo) se tiene en cuenta que este se
     * activa cuando se encuentra una tortuga, y está sera unica en los 4 pasos siguientes, es decir si encuentra otra
     * tortuga no la tendra en cuenta. También se elimina la tortuga de la matriz
     *
     * @param factorReduccion
     */
    private void setFactorReduccion(byte factorReduccion) {
        if (factorReduccion == 0) {
            if (this.personaje == Personaje.TORTUGA) {
                this.factorReduccion = 4;
                this.matriz[this.fila][this.columna] = 2;//se elimina la tortuga de la matriz
            }
        } else {
            this.factorReduccion = factorReduccion;
        }
    }

    /**
     * Permite eliminar la referencia que esta ligada a la matriz del padre
     * de esta forma aqui se pueden modificar los valores y esto no afecta
     * las matrices de los padres y vecinos.
     */
    private void cloneMatriz(byte[][] matriz) {
        byte n = (byte) matriz.length;
        this.matriz = new byte[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz[i], 0, this.matriz[i], 0, n);
        }

    }

    public boolean equals(Nodo nodoAbuelo) {
        if (nodoAbuelo == null) return false;
        if (fila != nodoAbuelo.fila) return false;
        if (columna != nodoAbuelo.columna) return false;
        if (metasCumplidas != nodoAbuelo.metasCumplidas) return false;

        return true;

    }


    @Override
    public String toString() {
        return "(" + fila + ", " + columna + ')';
    }
}
