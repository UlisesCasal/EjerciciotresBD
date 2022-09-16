package Ejercicio3bd;

public class Funciones {

    public static String cambiarTamanioString(String cadena,int tamanio) {
        cadena = cadena.trim();
        int agregar = tamanio - cadena.length();
        for (int i = 0; i < agregar; i++) {
            cadena += " ";
        }
        return cadena;
    }

    public static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}
