package Ejercicio3bd;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Metadata {

    public static final int TAMANIOREGISTRO = 16;
    private static int cantidadCampos = 0;


    private static void escribirMetadato(String nombreCampo, int longCampo, RandomAccessFile archivo) throws IOException {
        archivo.writeUTF(nombreCampo);
        archivo.writeInt(longCampo);
    }

    public static int getCantidadRegistros(RandomAccessFile archivo) throws IOException {
        return Math.toIntExact(((archivo.length()) / TAMANIOREGISTRO));
    }

    public static int getTamanioMetadataEnBytes(RandomAccessFile archivo) throws IOException {
        archivo.seek(0);
        int max = getCantidadRegistros(archivo);
        int resultado = 0;

        for (int i = 0; i < max; i++) {
            Registro rg = new Registro(archivo.readUTF(),archivo.readInt());
            // MULTIPLICO CADA TAMANIO DE CAMPO POR LA CANTIDAD DE BYTES QUE OCUPA UN CARACTER
            resultado += rg.tamanio() + 2;
        }

        return resultado;
    }



    public static Registro leerMetadato(RandomAccessFile archivo) throws IOException {
        String campo = archivo.readUTF();
        int size = archivo.readInt();
        return new Registro(campo,size);
    }



    public static void crearMetadata(RandomAccessFile metadata) throws IOException {

        String nombreCampo = "holas";

        //Ciclo que pide el nombre del campo y su longitud hasta que el usuario ingrese -1
        while (!nombreCampo.trim().equals("-1")) {


            System.out.println("[Para salir escriba -1]");

            System.out.println("Ingrese el nombre del campo (Solo puede ser de 10 caracteres): ");
            Scanner sc = new Scanner(System.in);
            nombreCampo = sc.nextLine();
            nombreCampo = Funciones.cambiarTamanioString(nombreCampo,10);


            //Verifica que el nombre tenga entre 1 y 10 caracteres
            while ((nombreCampo.length() > 10) || (nombreCampo.trim().length() < 1)) {
                Funciones.clear();
                System.out.println("Nombre invalido, debe tener entre 1 y 10 caracteres");
                System.out.println("Ingrese el nombre del campo (Solo puede ser de 10 caracteres): ");
                nombreCampo = sc.nextLine();
                nombreCampo = Funciones.cambiarTamanioString(nombreCampo,10);
            }


            if (!nombreCampo.trim().equals("-1")) {
                //Verifica si la cantidad de datos del campo es mayor a 1:
                int tamanioDeCampo = 0;
                System.out.println("Ingrese el tamanio del campo:");
                String num = sc.nextLine();
                if (Funciones.isNumeric(num)) {
                    tamanioDeCampo = Integer.parseInt(num);
                }

                while ((tamanioDeCampo < 1) || (tamanioDeCampo > 10)) {
                    Funciones.clear();
                    System.out.println("Dato invalido, el tamanio debe ser un numero entre 1 y 10");
                    System.out.println("Ingrese el tamanio del campo:");
                    num = sc.nextLine();
                    num = num.trim();

                    if (Funciones.isNumeric(num)) {
                        tamanioDeCampo = Integer.parseInt(num);
                    }
                }

                //Una vez los datos verificados los escribo en el archivo de metadatos:
                escribirMetadato(nombreCampo, tamanioDeCampo, metadata);
                cantidadCampos++;
            }

            Funciones.clear();
        }
    }

}
