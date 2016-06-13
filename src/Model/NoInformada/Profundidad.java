/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.NoInformada;

import Model.AlgoritmoBusqueda;
import Model.Nodo;
import Model.Personaje;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author andre
 */
public class Profundidad extends AlgoritmoBusqueda {

    private Stack<Nodo> pila;

    public Profundidad(byte[][] matriz, byte n) {
        super(matriz, n);
        this.pila = new Stack<Nodo>();
        pila.push(this.getNodoInicial());
    }

    public String run() {
        Nodo nodoActual = null;

        while (this.pila.size() > 0) {
            nodoActual = this.pila.pop();

            if (nodoActual.isMeta()) {
                if (nodoActual.isMetaGlobal()) {//verifica si ya se alcanzo la meta global y actualiza las variables de estado
                	this.costoTotal = nodoActual.getCostoAcumulado();
					this.historialPadres.add(nodoActual);
					this.idsHistorialPadres++;                	
                	break;
                }
                //System.out.println(" -- ir por "+nodoActual.getMetaActual()+ " "+nodoActual);				
                //nodoActual.mostrarMatriz();
            }
            this.expandirNodo(nodoActual);//expandir el nodo
        }
        
        return this.toString();
    }

    /**
     * Determina los posibles cambios de estado que tiene el entorno,
     * basicamente agrega a la cola los posibles movimientos en el tablero.
     * Teoricamente es la expasión del nodo que no son meta.
     *
     * @param nodoActual
     */
    public void expandirNodo(Nodo nodoActual) {
        //System.out.println("Expandir: " + nodoActual+"Metas: "+nodoActual.getMetasCumplidas());

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
        
        if (hasLoop(nodoActual)) {
        	System.out.println("ciclo detectado");
        	System.exit(0);
        }
        
        if (i - 1 >= 0) {
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodoActual, nodoAbuelo);
        }

        if (j - 1 >= 0) {
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodoActual, nodoAbuelo);
        }

        if (i + 1 < this.n) {
            this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodoActual, nodoAbuelo);
        }

        if (j + 1 < this.n) {
            this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodoActual, nodoAbuelo);
        }

        this.idsHistorialPadres++;//aumenta el identificador de indexamiento
    }

    /**
     * Metodo que crea un nuevo nodo, también se comprueba que el nodo no
     * se este devolviendo
     *
     * @param i fila de la nueva posicion
     * @param j columna de la nueva posicion
     * @param padreId identificador del padre
     * @param nodoPadre nodo padre del nodo que será creado
     * @return
     */
    private void crearNodo(byte i, byte j, int padreId, Nodo nodoActual, Nodo nodoAbuelo) {
        
    	if (nodoActual.getMatriz()[i][j] == Personaje.ROCA) {

			return;
		}

    	if(!(i == nodoAbuelo.getFila() && j == nodoAbuelo.getColumna() && nodoAbuelo.getMetaActual() == nodoActual.getMetaActual())) {
        	
            Nodo nodo = new Nodo(padreId, i, j, nodoActual.getMatriz(), nodoActual.getMetasCumplidas(), nodoActual.getMetaActual(), nodoActual.getFactorReduccion());
        	nodo.setCostoAcumulado(nodoActual.getCostoAcumulado());
            this.pila.add(nodo);
            this.nodoCreados++;
        }
    }

    /**
     * Metodo que detecta ciclos
     *
     * @param nodo nodo a comparar
     * @return boolean
     */
	public boolean hasLoop(Nodo nodo) 
	{
		int index = nodo.getPadre();
		Nodo nodoPadre;
		boolean existeCiclo = false;
		ArrayList<Nodo> camino = new ArrayList<Nodo>();
		camino.add(nodo);
		
		while(true && this.historialPadres.size() > 1)
		{			
			nodoPadre = this.historialPadres.get(index);
			camino.add(nodoPadre);
			
			if(nodo.getColumna() == nodoPadre.getColumna() && nodo.getFila() == nodoPadre.getFila() && 
					nodo.getPersonaje() == nodoPadre.getPersonaje() && nodo.getMetasCumplidas() == nodoPadre.getMetasCumplidas())
			{
				existeCiclo = true;				
				break;
			}
			
			if(index == 0){
                break;
            }
			
			index = nodoPadre.getPadre();
		}
		
		if(existeCiclo)
		{			
			for (int i = camino.size()-1; i >= 0; i--) {
				System.out.println(camino.get(i));
			}
		}
        
        return existeCiclo;
    }
}
