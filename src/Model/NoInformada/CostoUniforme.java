package Model.NoInformada;

import java.util.Comparator;
import java.util.PriorityQueue;
import Model.AlgoritmoBusqueda;
import Model.Comparador.ComparadorCostos;
import Model.Nodo;
import Model.Personaje;

public class CostoUniforme extends AlgoritmoBusqueda
{
	private Comparator<Nodo> comparador;
    private PriorityQueue<Nodo> cola;

	public CostoUniforme(byte[][] matriz, byte n) 
	{
		super(matriz, n);
		comparador = new ComparadorCostos();
		cola = new PriorityQueue<Nodo>(10, comparador);
		cola.add(this.getNodoInicial());
	}
	
	public void run()
	{
		Nodo nodoActual = null;

		while (true)
        {
			nodoActual = this.cola.remove();
			if (nodoActual.isMeta())
			{
				System.out.println(nodoActual);
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
	
	public void expandirNodo(Nodo nodoActual)
	{
		this.nodoExpandidos++;//se ha expandido un nuevo nodo
        this.historialPadres.add(nodoActual);//se agrega nodo al historial de padres
        byte i = nodoActual.getFila();//obtiene la fila del nodo actual
        byte j = nodoActual.getColumna();//obtiene la columna del noto actual
		Nodo nodoAbuelo = this.historialPadres.get(nodoActual.getPadre());
		if (i + 1 < this.n)
		{
			this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodoActual,nodoAbuelo);
		}
		if (j + 1 < this.n)
		{
			this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodoActual,nodoAbuelo);
		}
		if (i - 1 >= 0)
        {
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodoActual,nodoAbuelo);
        }
        if (j - 1 >= 0)
        {
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodoActual,nodoAbuelo);
        }

        this.idsHistorialPadres++;//aumenta el identificador de indexamiento
	}
	
	/**
     * Metodo que crea un nuevo nodo, también se comprueba
     * que el nodo no se este devolviendo
     *
     * @param i     fila de la nueva posicion
     * @param j     columna de la nueva posicion
     * @param padreId identificador del padre
     * @param nodoActual nodo padre del nodo que será creado
     * @return
     */
    private void crearNodo(byte i, byte j, int padreId, Nodo nodoActual, Nodo nodoAbuelo)
    {
		if (nodoActual.getMatriz()[i][j] == Personaje.ROCA) {

			return;
		}
		if (nodoActual.getMatriz()[i][j] == Personaje.ACUAMAN) {
			//no crea el nodo solo se suma a los expandidos
			this.nodoExpandidos++;
			return;
		}

        if(!(i == nodoAbuelo.getFila() && j == nodoAbuelo.getColumna() && nodoAbuelo.getMetaActual() == nodoActual.getMetaActual()))
        {
            Nodo nodo = new Nodo(padreId, i, j, nodoActual.getMatriz(), nodoActual.getMetasCumplidas(), nodoActual.getMetaActual(), nodoActual.getFactorReduccion());
            nodo.setCostoAcumulado(nodoActual.getCostoAcumulado());
            this.cola.add(nodo);
            this.nodoCreados++;
        }
    }
	
}
