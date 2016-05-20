package Test;

import Model.NoInformada.Amplitud;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jeffer on 19/05/16.
 */
public class AmplitudTest {

    @Test
    public void testRun() throws Exception {
        byte matriz [][] = {{0,2,2,1,1},{ 2,3,4,2,7},{2,1,2,8,1},{1,1,3,2,1},{2,6,2,4,5}};
        byte n = 5;
        Amplitud amplitud = new Amplitud(matriz, n);
        long tInicio = System.currentTimeMillis();
        amplitud.run();
        long tFin = System.currentTimeMillis();
        long tiempo = tFin - tInicio;
        System.out.println("Tiempo de ejecución: " + tiempo + "ms");
    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testMostrarRuta() throws Exception {

    }
}