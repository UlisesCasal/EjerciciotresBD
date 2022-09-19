package Ejercicio3bd;

import java.io.*;
import java.util.Arrays;

public class Main {

    static RandomAccessFile metadatosFile = null;
    static RandomAccessFile datosFile = null;

    public static void main(String[] args) throws FileNotFoundException {

        try {
            //crea el archivo:
            System.out.println("Archivo creado!!!");

            metadatosFile = new RandomAccessFile("metadatos.bin", "rw");
            datosFile = new RandomAccessFile("datos.bin", "rw");
            Metadata.crearMetadata();

            ManejarDatos.escribir(datosFile,metadatosFile);
            ManejarDatos.escribir(datosFile,metadatosFile);
            System.out.println(Arrays.toString(ManejarDatos.leer(datosFile, metadatosFile, 0)));
            System.out.println(Arrays.toString(ManejarDatos.leer(datosFile, metadatosFile, 1)));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



}
