package Main;

import Model.NoInformada.Amplitud;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class Main
{
    private static int[][] matriz;

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //todo realizar el menú para seleccinar que tipo de algoritmo se desea ejecutar
        //todo la ruta de la matriz puede venir como args[] del main
        //lecturaArchivo("./pruebas/prueba1.txt");
        //mostrarMatriz();

        byte matriz [][] = {{0,2,2,1,1},{ 2,3,4,2,7},{2,1,2,8,1},{1,1,3,2,1},{2,6,2,4,5}};
        byte n = 5;
        Amplitud amplitud = new Amplitud(matriz, n);
        long tInicio = System.currentTimeMillis();
        amplitud.run();
        long tFin = System.currentTimeMillis();
        long tiempo = tFin - tInicio;
        System.out.println("Tiempo de ejecución: " + tiempo + "ms");
    }

    /**
     * Función que permite leer un archivo
     *
     * @param archivo se usa una ruta relativa para cargar el archivo
     * @throws IOException
     */
    static void lecturaArchivo(String archivo)
    {
        String cadena;
        try {
            FileReader file = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(file);

            int iterador = 0;
            int tamanoMatriz = 0;
            while ((cadena = buffer.readLine()) != null) {
                if (iterador == 0) {
                    tamanoMatriz = Integer.parseInt(cadena);
                    matriz = new int[tamanoMatriz][tamanoMatriz];
                } else {
                    StringTokenizer st = new StringTokenizer(cadena);

                    int j = 0;
                    while (st.hasMoreElements()) {
                        String token = st.nextElement().toString();
                        matriz[iterador - 1][j] = Integer.parseInt(token);
                        j++;
                    }
                }
                iterador++;
            }
            buffer.close();

        }catch (FileNotFoundException ex){
            System.out.println("Error. La ruta del archivo no es la correcta, vuelva a intenterlo.");
            System.exit(-1);
        }catch (IOException ex){
            System.out.println("Error.Ha ocurrido un error interno, vuelva a intentarlo.");
            System.exit(-1);

        }

    }

    /**
     * Método que permite mostrar la matriz
     */
    static void mostrarMatriz()
    {
        for (int i = 0; i < matriz.length; i++)
        {
            for (int j = 0; j < matriz.length; j++)
            {
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println();
        }
    }
}