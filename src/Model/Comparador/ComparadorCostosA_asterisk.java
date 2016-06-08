package Model.Comparador;

import Model.Nodo;

import java.util.Comparator;

public class ComparadorCostosA_asterisk implements Comparator<Nodo>
{
    /*Metodo que retorna si el costo de un nodo es menor a otro*/
    @Override
    public int compare(Nodo x, Nodo y) 
    {
        if(x.getfN() < y.getfN())
        {
            return -1;
        }
        else if(x.getfN() > y.getfN())
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