package Ejercicio3bd;

import java.io.*;

public class Main {

    static RandomAccessFile archivo = null;

    public static void main(String[] args) throws FileNotFoundException {

        FileOutputStream fos = null;
        try {
            //crea el archivo:
            archivo = new RandomAccessFile("metadatos.bin", "rw");
            if (archivo.length() == 0) {
                Funciones.crearVacio(archivo);
                System.out.println("Archivo creado!!!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
        try {
            if (fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }