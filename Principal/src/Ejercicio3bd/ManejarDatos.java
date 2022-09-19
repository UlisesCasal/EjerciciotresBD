package Ejercicio3bd;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class ManejarDatos {

    public static void escribir(RandomAccessFile datos,RandomAccessFile metadato) throws IOException {

        int metadataSize = Metadata.getCantidadRegistros(metadato);
        String dato;
        metadato.seek(0);

        for (int i = 0; i < metadataSize; i++) {
            Registro rg = Metadata.leerMetadato(metadato);
            int tamanioDato = rg.tamanio();

            System.out.println("Cant. maxima de caracteres: " + tamanioDato);
            System.out.println("Ingrese su " + rg.campo() + ": ");
            Scanner sc = new Scanner(System.in);

            dato = sc.nextLine();
            dato = Funciones.cambiarTamanioString(dato,tamanioDato);

            while (dato.length() > tamanioDato) {
                System.out.println("Dato invalido, el dato debe ser de " + tamanioDato + "caracteres.");
                System.out.println("Ingrese su " + rg.campo() + ": ");
                dato = sc.nextLine();
                dato = Funciones.cambiarTamanioString(dato,tamanioDato);
            }

            //ESCRIBIR EN DATOS
            datos.writeUTF(dato);
        }
    }

    public static String[] leer(RandomAccessFile datos,RandomAccessFile metadato,int posicion) throws IOException {

        int tamanioDatoEnBytes = Metadata.getTamanioMetadataEnBytes(metadato);

        datos.seek((long)posicion * tamanioDatoEnBytes);

        int cantidadCampos = Metadata.getCantidadRegistros(metadato);
        String registrodatos[] = new String[cantidadCampos];

        for (int i = 0; i < cantidadCampos; i++) {
            registrodatos[i] = datos.readUTF();
        }

        return registrodatos;
    }

    public static void modificar(RandomAccessFile datos,RandomAccessFile metadato,int posicion) throws IOException {

        int tamanioDatoEnBytes = Metadata.getTamanioMetadataEnBytes(metadato);

        datos.seek((long)posicion * tamanioDatoEnBytes);

        int metadataSize = Metadata.getCantidadRegistros(metadato);
        String dato;

        for (int i = 0; i < metadataSize; i++) {
            Registro rg = Metadata.leerMetadato(metadato);
            int tamanioDato = rg.tamanio();

            System.out.println("Presione ENTER para dejar el dato por defecto");
            System.out.println("Cant. maxima de caracteres: " + tamanioDato);
            Scanner sc = new Scanner("Ingrese su " + rg.campo() + ": ");

            dato = sc.nextLine();
            dato = Funciones.cambiarTamanioString(dato,tamanioDato);

            if (dato.equals("")) {
                datos.readUTF();
            }

            else {
                while (dato.length() > tamanioDato) {
                    System.out.println("Dato invalido, el dato debe ser de " + tamanioDato + "caracteres.");
                    sc = new Scanner("Ingrese su " + rg.campo() + ": ");
                    dato = sc.nextLine();
                    dato = Funciones.cambiarTamanioString(dato, tamanioDato);
                }
                //ESCRIBIR EN DATOS
                datos.writeUTF(dato);
            }

            Funciones.clear();
        }
    }






}
