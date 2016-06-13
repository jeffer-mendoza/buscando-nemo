package Model.Informada;

import java.awt.Point;
import java.util.Comparator;
import java.util.PriorityQueue;
import Model.AlgoritmoBusqueda;
import Model.Comparador.ComparadorCostosA_asterisk;
import Model.Nodo;
import Model.Personaje;

public class AEstrella extends AlgoritmoBusqueda{

	private Comparator<Nodo> comparador;
    private PriorityQueue<Nodo> cola;
    private Point[] listadoMetas;
	
	public AEstrella(byte[][] matriz, byte n, int tipoHeuristica)
	{
		super(matriz, n);
		comparador = new ComparadorCostosA_asterisk();
		cola = new PriorityQueue<Nodo>(10, comparador);	
		listadoMetas = new Point[3];
	}
	
	public String run()
	{
		Nodo nodoActual = null;
		buscarNodoInicialyMetas();

		while (this.cola.size() > 0)
        {
			nodoActual = this.cola.remove();
						
			if (nodoActual.isMeta()) 
			{
				if (nodoActual.isMetaGlobal()) {//verifica si ya se alcanzo la meta global y actualiza las variables de estado
					this.costoTotal = nodoActual.getCostoAcumulado();
					this.historialPadres.add(nodoActual);
					this.idsHistorialPadres++;

	                break;
	            }				
			}
			
			this.expandirNodo(nodoActual);//expandir el nodo
        }

		return this.toString();
	}
	
	public void expandirNodo(Nodo nodoActual)
	{
		this.nodoExpandidos++;//se ha expandido un nuevo nodo
		byte i = nodoActual.getFila();//obtiene la fila del nodo actual
        byte j = nodoActual.getColumna();//obtiene la columna del noto actual
		
		/*
         * Si el nodo a expander tiene a acuaman entonces no puede crearle hijos
         */
        if (nodoActual.getMatriz()[i][j] == Personaje.ACUAMAN) {
			return;
		}
		
        this.historialPadres.add(nodoActual);//se agrega nodo al historial de padres        
		Nodo nodoAbuelo = this.historialPadres.get(nodoActual.getPadre());

		if (i - 1 >= 0)
        {
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodoActual, nodoAbuelo);
        }
        
        if (i + 1 < this.n) 
        {
            this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodoActual, nodoAbuelo);
        }
        
        if (j - 1 >= 0) 
        {
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodoActual, nodoAbuelo);
        }
        
        if (j + 1 < this.n) 
        {
            this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodoActual, nodoAbuelo);
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

        if(!(i == nodoAbuelo.getFila() && j == nodoAbuelo.getColumna() && nodoAbuelo.getMetaActual() == nodoActual.getMetaActual()))
        {
            Nodo nodo = new Nodo(padreId, i, j, nodoActual.getMatriz(), nodoActual.getMetasCumplidas(), nodoActual.getMetaActual(),nodoActual.getFactorReduccion());
            nodo.setCostoAcumulado(nodoActual.getCostoAcumulado());
            nodo.setValorHeuristica(this.primeraHeuristica(i, j, listadoMetas[nodoActual.getMetasCumplidas()]));
            nodo.setfN();
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
    public double primeraHeuristica(int x, int y, Point coordenada)
    {
        double heuristica = (Math.abs(x-coordenada.getX())+Math.abs(y-coordenada.getY()))*0.5;
        return heuristica;
    }
	
	public void buscarNodoInicialyMetas()
	{
		byte posX = 0;
		byte posY = 0;
		for (byte i = 0; i < matriz.length; i++) {
			for (byte j = 0; j < matriz.length; j++) {
								
				switch (matriz[i][j]) 
				{
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
		
		Nodo primerNodo = new Nodo(0, posX, posY, this.matriz.clone(), (byte) 0, Personaje.NEMO, (byte)0);
		primerNodo.setCostoAcumulado(0);
		primerNodo.setValorHeuristica(this.primeraHeuristica(posX, posY, listadoMetas[0]));
		primerNodo.setfN();
		
		cola.add(primerNodo);
	}
}
