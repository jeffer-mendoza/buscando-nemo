package Main;

import Model.Informada.AEstrella;
import Model.Informada.Avara;
import Model.NoInformada.Amplitud;
import Model.NoInformada.CostoUniforme;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class Main
{
    private static byte[][] matriz;
    private static byte tamanoMatriz;

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //todo realizar el menú para seleccinar que tipo de algoritmo se desea ejecutar
        //todo la ruta de la matriz puede venir como args[] del main
        lecturaArchivo("./pruebas/prueba10.txt");
        String stralgoritmo = args[0];
        long tInicio = System.currentTimeMillis();
        long tFin = System.currentTimeMillis();
        if(stralgoritmo == "amplitud"){
            Amplitud algoritmo = new Amplitud(matriz, tamanoMatriz);
            tInicio = System.currentTimeMillis();
            algoritmo.run();
            tFin = System.currentTimeMillis();
        }else{
            if(stralgoritmo == "costouniforme"){
                Avara algoritmo = new Avara(matriz,tamanoMatriz,0);
                tInicio = System.currentTimeMillis();
                algoritmo.run();
                tFin = System.currentTimeMillis();
            }else{
                if(stralgoritmo == "profundidad"){

                }else{
                    if(stralgoritmo == "avara"){
                        Avara algoritmo = new Avara(matriz,tamanoMatriz,0);
                        tInicio = System.currentTimeMillis();
                        algoritmo.run();
                        tFin = System.currentTimeMillis();
                    }else{
                        AEstrella algoritmo = new AEstrella(matriz,tamanoMatriz,0);
                        tInicio = System.currentTimeMillis();
                        algoritmo.run();
                        tFin = System.currentTimeMillis();
                    }
                }
            }
        }

        long tiempo = tFin - tInicio;
        System.out.println("Tiempo de ejecución: " + tiempo + "ms = " + tiempo/1000+ "s");
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
            while ((cadena = buffer.readLine()) != null) {
                if (iterador == 0) {
                    tamanoMatriz = Byte.parseByte(cadena);
                    matriz = new byte[tamanoMatriz][tamanoMatriz];
                } else {
                    StringTokenizer st = new StringTokenizer(cadena);

                    int j = 0;
                    while (st.hasMoreElements()) {
                        String token = st.nextElement().toString();
                        matriz[iterador - 1][j] = Byte.parseByte(token);
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