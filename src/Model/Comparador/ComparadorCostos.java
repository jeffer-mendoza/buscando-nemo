package Model.Comparador;
import Model.Nodo;

import java.util.*;

/*Clase de tipo comparador de nodos que se usa como criterio de comparacion para
sacar los elementos de la cola de prioridad*/
public class ComparadorCostos implements Comparator<Nodo>
{
    /*Metodo que retorna si el costo de un nodo es menor a otro*/
    @Override
    public int compare(Nodo x, Nodo y) 
    {
        if(x.getCostoAcumulado() < y.getCostoAcumulado())
        {
            return -1;
        }
        else if(x.getCostoAcumulado() > y.getCostoAcumulado())
        {
            return 1;
        }
        else//Tienen el mismo costo, el orden sera definido por su valor de personaje
        {
            if(x.getPersonaje() < y.getPersonaje())
            {
            	return -1;
            }
            else 
            {
            	return 1;
            }
        }
    }
}
