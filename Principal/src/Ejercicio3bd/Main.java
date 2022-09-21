package Ejercicio3bd;

import java.io.*;
import java.util.Scanner;

public class Main {

    static RandomAccessFile metadatosFile = null;
    static RandomAccessFile datosFile = null;

    public static void main(String[] args) throws FileNotFoundException {

        try {
            //crea el archivo:
            System.out.println("Archivo creado!!!");

            metadatosFile = new RandomAccessFile("metadatos.bin", "rw");
            datosFile = new RandomAccessFile("datos.bin", "rw");

            int opcion = 0;

            while (opcion != -1) {
                while (metadatosFile.length() <= 0) {
                    Funciones.clear();
                    System.out.println("[POR FAVOR, DEFINA LA ESTRUCTURA DE LOS REGISTROS] \n");
                    metadatosFile = new RandomAccessFile("metadatos.bin", "rw");
                    datosFile = new RandomAccessFile("datos.bin", "rw");
                    Metadata.crearMetadata(metadatosFile);
                }

                System.out.println("----------------------MENU PRINCIPAL----------------------");
                System.out.println("Opcion 1: Alta");
                System.out.println("Opcion 2: Baja");
                System.out.println("Opcion 3: Modificacion");
                System.out.println("Opcion 4: Consulta");
                System.out.println("Opcion 5: Mostrar todo el archivo de datos");
                System.out.println("Opcion 6: Eliminar metadatos (tambien eleminara los datos)");
                System.out.println("Escriba el numero de su opcion, o escriba -1 para salir");
                System.out.println("----------------------------------------------------------");

                Scanner sc = new Scanner(System.in);

                System.out.println("Ingrese la opcion que desea seleccionar:");
                String num = sc.nextLine();

                while (!Funciones.isNumeric(num)) {
                    System.out.println("[ERROR] Dato invalido, ingrese un numero");
                    System.out.println("Ingrese la posicion a modificar:");
                    num = sc.nextLine();
                }

                opcion = Integer.parseInt(num);

                switch (opcion) {
                    case -1 -> {
                        Funciones.clear();
                        System.out.println("Que tenga un buen dia");
                    }
                    case 1 -> {
                        Funciones.clear();
                        ManejarDatos.escribirInterfaz(datosFile, metadatosFile);
                    }
                    case 2 -> {
                        Funciones.clear();
                        ManejarDatos.baja(datosFile, metadatosFile);
                    }
                    case 3 -> {
                        Funciones.clear();
                        ManejarDatos.modificarInterfaz(datosFile, metadatosFile);
                    }
                    case 4 -> {
                        Funciones.clear();
                        ManejarDatos.leerInterfaz(datosFile, metadatosFile);
                    }
                    case 5 -> {
                        Funciones.clear();
                        ManejarDatos.mostrarArchivoDatos(datosFile, metadatosFile);
                    }
                    case 6 -> {
                        datosFile.setLength(0);
                        metadatosFile.setLength(0);
                        Funciones.clear();
                        System.out.println("[POR FAVOR, DEFINA LA ESTRUCTURA DE LOS REGISTROS] \n");
                        Metadata.crearMetadata(metadatosFile);
                    }
                    default -> System.out.println("[ERROR] accion no reconocida");
                }

            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



}
