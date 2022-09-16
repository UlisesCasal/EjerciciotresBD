package Ejercicio3bd;

import java.io.*;

public class Main {

    static RandomAccessFile metadatosFile = null;
    static RandomAccessFile datosFile = null;

    public static void main(String[] args) throws FileNotFoundException {

        try {
            //crea el archivo:
            metadatosFile = new RandomAccessFile("metadatos.bin", "rw");
            datosFile = new RandomAccessFile("datos.bin", "rw");
            Metadata.crearMetadata();


            System.out.println("Archivo creado!!!");

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



}
