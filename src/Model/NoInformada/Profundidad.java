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

    }

    public void run() {
        Nodo nodoActual = null;
        buscarNodoInicial();
        while (this.pila.size() > 0) {
            nodoActual = this.pila.pop();

            if (nodoActual.isMeta()) {
                if (nodoActual.isMetaGlobal()) {//verifica si ya se alcanzo la meta global y actualiza las variables de estado
                    break;
                }
                //System.out.println(" -- ir por "+nodoActual.getMetaActual()+ " "+nodoActual);				
                //nodoActual.mostrarMatriz();
            }
            this.expandirNodo(nodoActual);//expandir el nodo
        }
        this.nodoFinal = nodoActual;
        this.costoTotal = nodoActual.getCostoAcumulado();
        System.out.println(this.toString());
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
        
        if (nodoActual.isAquaman()) {
            //System.out.println(nodoActual +" Acuaman!!!");
            return;
        }
        
        this.historialPadres.add(nodoActual);//se agrega nodo al historial de padres
        byte i = nodoActual.getFila();//obtiene la fila del nodo actual
        byte j = nodoActual.getColumna();//obtiene la columna del noto actual
        
        if (hasLoop(nodoActual)) {
        	System.out.println("ciclo detectado");
        	System.exit(0);
        }
        
        if (i - 1 >= 0) {
            this.crearNodo((byte) (i - 1), j, this.idsHistorialPadres, nodoActual);
        }

        if (j - 1 >= 0) {
            this.crearNodo(i, (byte) (j - 1), this.idsHistorialPadres, nodoActual);
        }

        if (i + 1 < this.n) {
            this.crearNodo((byte) (i + 1), j, this.idsHistorialPadres, nodoActual);
        }

        if (j + 1 < this.n) {
            this.crearNodo(i, (byte) (j + 1), this.idsHistorialPadres, nodoActual);
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
    private void crearNodo(byte i, byte j, int padreId, Nodo nodoActual) {
        Nodo nodoPadre = historialPadres.get(nodoActual.getPadre());

        if (!(i == nodoPadre.getFila() && j == nodoPadre.getColumna()) && nodoActual.getMatriz()[i][j] != 1) {
        	
            Nodo nodo = new Nodo(padreId, i, j, nodoActual.getMatriz(), nodoActual.getMetasCumplidas(), nodoActual.getMetaActual());
            //if (!hasLoop(nodo, nodoActual)) {
                nodo.setFactorReduccion(nodoActual.getFactorReduccion());
                nodo.setCostoAcumulado(nodoActual.getCostoAcumulado());
                this.pila.add(nodo);
                this.nodoCreados++;
                //System.out.println("Crear: " + nodo);
            //} else {
            //    System.out.println("Ciclo detectado! " + nodo);
            //}
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
				System.out.println(nodo +" : "+nodo.getMetasCumplidas()+ " - " + nodoPadre+" : "+nodoPadre.getMetasCumplidas());
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
    
    /*
    public boolean hasLoop(Nodo nodo, Nodo padre) {
        
        boolean ciclo = false;
        List<Nodo> camino = buscarCamino(padre , nodo);
        System.out.print("Camino: ");
        for (int i = 0; i < camino.size(); i++) {
            System.out.print(camino.get(i) + " - ");
        }

        for (int i = 0; i < camino.size() && !ciclo; i++) {
            boolean posicion = nodo.getColumna() == camino.get(i).getColumna() && nodo.getFila() == camino.get(i).getFila();
            ciclo = posicion;
        }

        return ciclo;
    }
    
    private List<Nodo> buscarCamino(Nodo nodoActual, Nodo punto) {
        List<Nodo> camino = new LinkedList<>();
        camino.add(nodoActual);
        if (nodoActual.getColumna() == 0 && nodoActual.getFila() == 0 && nodoActual.getPadre() == 0) {
            return camino;
        }
        nodoActual = historialPadres.get(nodoActual.getPadre());
        while (!(nodoActual.getColumna() == 0 && nodoActual.getFila() == 0 && nodoActual.getPadre() == 0)) {
            camino.add(nodoActual);
            nodoActual = historialPadres.get(nodoActual.getPadre());
        }
        camino.add(nodoActual);
        return camino;
    }*/

    public void buscarNodoInicial() {
        byte posX = 0;
        byte posY = 0;
        for (byte i = 0; i < matriz.length; i++) {
            for (byte j = 0; j < matriz.length; j++) {
                if (matriz[i][j] == 0) {
                    posX = i;
                    posY = j;
                }
            }
        }

        pila.add(new Nodo(0, posX, posY, this.matriz.clone(), (byte) 0, Personaje.NEMO));
    }

    

}
