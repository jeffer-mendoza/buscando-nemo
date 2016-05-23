package Model;

/**
 * Created by jeffer on 13/05/16.
 *
 * Esta clase relaciona el número de identificación de cada personaje del juego
 * con una constante, lo cual permite un fácil manejo en la manipulación de estos
 * dentro del código.
 */
public final class Personaje {

    public static final byte ROCA = 1;
    public static final byte DISPONIBLE = 2;
    public static final byte TIBURON = 3;
    public static final byte TORTUGA = 4;
    public static final byte DORI = 5;
    public static final byte MARLIN = 6;
    public static final byte NEMO = 7;
    public static final byte ACUAMAN= 8;


    /**
     * Obtiene el costo de un personaje en particular
     *
     * @param personaje
     * @return
     */
    public static byte getCosto(byte personaje)
    {
        if(personaje == TIBURON) {
            return 10;
        }
        if(personaje == ACUAMAN) {
            return Byte.MAX_VALUE;
        }

        return 1;
    }


}
