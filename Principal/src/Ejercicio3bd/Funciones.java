package Ejercicio3bd;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Funciones {


    public static String hacerStringde10(String cadena) {
        cadena = cadena.trim();
        int agregar = 10 - cadena.length();
        for (int i = 0; i < agregar; i++) {
            cadena += " ";
        }
        return cadena;
    }
    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private void escribirMetadato(String nombreCampo, int longCampo, RandomAccessFile archivo) throws IOException {
        archivo.writeUTF(nombreCampo);
        archivo.writeInt(longCampo);

    }


    public void crearMetadata() {


        //Pido la cantidad de campos:
        int CANTCAMPOS;
        int cantiCampos = 0;
        Scanner sc = new Scanner("Ingrese la cantidad de campos que desea: ");
        String cantC = sc.nextLine();
        cantC = cantC.trim();

        while ((cantiCampos < 1) || (cantiCampos < 10)) {

            if (isNumeric(cantC)) {
                cantiCampos = Integer.parseInt(cantC);

            }

            sc = new Scanner("Ingrese la cantidad de campos que desea: ");
            cantC = sc.nextLine();
            cantC = cantC.trim();
        }
        //Asigno datos a la cantidad de campos:
        CANTCAMPOS = cantiCampos;
        int tamanioDeCampo = 0;

        //ciclo que pide el nombre del campo y su longitud
        for (int i = 0; i < CANTCAMPOS; i++) {
            sc = new Scanner("Ingrese el nombre del campo" + " i:");
            String nombreCampo = sc.nextLine();
            nombreCampo = cantC.trim();
            nombreCampo = hacerStringde10(nombreCampo);

            //verifica si la cantidad de datos del campo es mayor a 1:
            while ((tamanioDeCampo < 1) && (tamanioDeCampo <= 10)) {
                sc = new Scanner("Ingrese el nombre del campo" + " i:");
                String num = sc.nextLine();
                num = num.trim();

                if (isNumeric(num)) {
                    tamanioDeCampo = Integer.parseInt(num);

                }
            }
            //Una vez los datos verificados los escribo en el archivo de metadatos:
            archivo =
            escribirMetadato(nombreCampo, tamanioDeCampo, archivo);

        }
    }

}
