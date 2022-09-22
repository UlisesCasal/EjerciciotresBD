package Ejercicio3bd;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

public class ManejarDatos {


    public static void baja(RandomAccessFile datos, RandomAccessFile metadato) throws IOException {
        Scanner sc = new Scanner(System.in);
        int posicion = -1;

        System.out.println("Ingrese la posicion a dar de baja:");
        String num = sc.nextLine();

        while (!Funciones.isNumeric(num)) {
            System.out.println("[ERROR] Dato invalido, ingrese un numero");
            System.out.println("Ingrese la posicion a dar de baja:");
            num = sc.nextLine();
        }
        posicion = Integer.parseInt(num);

        int tamanioDatoEnBytes = Metadata.getTamanioMetadataEnBytes(metadato);
        tamanioDatoEnBytes += 1; // ESTE 1 REPRESENTA AL BOOLEAN DE USUARIO ACTIVO/INACTIVO
        int cantidadDeRegistrosDeDatos = (int) (datos.length() / tamanioDatoEnBytes);


        if ((posicion >= cantidadDeRegistrosDeDatos) || (posicion < 0)){
            System.out.println("No existe registro correspondiente al indice ingresado");
        }

        else {
            datos.seek((long) posicion * tamanioDatoEnBytes);

            int cantidadCampos = Metadata.getCantidadRegistros(metadato);
            for (int i = 0; i < cantidadCampos; i++) {
                datos.readUTF();
            }
            datos.writeBoolean(false);
            System.out.println("Registro dado de baja exitosamente");
        }

        System.out.println("Presione ENTER para continuar.");
        sc.nextLine();
        Funciones.clear();
    }

    public static void escribirInterfaz(RandomAccessFile datos, RandomAccessFile metadato) throws IOException {

        int metadataSize = Metadata.getCantidadRegistros(metadato);
        String dato;
        metadato.seek(0);
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < metadataSize; i++) {
            Registro rg = Metadata.leerMetadato(metadato);
            int tamanioDato = rg.tamanio();

            System.out.println("Cant. maxima de caracteres: " + tamanioDato);
            System.out.println("Ingrese su " + rg.campo() + ": ");

            dato = sc.nextLine();
            dato = Funciones.cambiarTamanioString(dato,tamanioDato);

            while ((dato.length() > tamanioDato) || (dato.trim().length() < 1)) {
                Funciones.clear();
                System.out.println("Dato invalido, el dato debe ser de " + tamanioDato + "caracteres.");
                System.out.println("Ingrese su " + rg.campo() + ": ");
                dato = sc.nextLine();
                dato = Funciones.cambiarTamanioString(dato,tamanioDato);
            }

            //ESCRIBIR EN DATOS
            datos.writeUTF(dato);
        }
        datos.writeBoolean(true);
        System.out.println("Presione ENTER para continuar.");
        sc.nextLine();
        Funciones.clear();
    }


    public static void mostrarArchivoDatos(RandomAccessFile datos,RandomAccessFile metadato) throws IOException {
        int tamanioDatoEnBytes = Metadata.getTamanioMetadataEnBytes(metadato);
        tamanioDatoEnBytes += 1; // ESTE 1 REPRESENTA AL BOOLEAN DE USUARIO ACTIVO/INACTIVO

        int cantidadDeRegistrosDeDatos = (int) (datos.length() / tamanioDatoEnBytes);

        System.out.println(Metadata.stringMetadatos(metadato));

        for (int i = 0; i < cantidadDeRegistrosDeDatos; i++) {
            System.out.println("[" + i + "] " + Arrays.toString(leer(datos,metadato,i)));
        }
        System.out.println("Presione ENTER para continuar.");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        Funciones.clear();
    }


    public static void leerInterfaz(RandomAccessFile datos, RandomAccessFile metadato) throws IOException {
        Scanner sc = new Scanner(System.in);
        int posicion = -1;

        System.out.println("Ingrese la posicion a leer:");
        String num = sc.nextLine();

        while (!Funciones.isNumeric(num)) {
            System.out.println("[ERROR] Dato invalido, ingrese un numero");
            System.out.println("Ingrese la posicion a leer:");
            num = sc.nextLine();
        }

        posicion = Integer.parseInt(num);

        String[] mostrar = leer(datos,metadato,posicion);

        if (mostrar.length == 0) {
            System.out.println("No existe registro correspondiente al indice ingresado");
        }
        else {
            System.out.println(Arrays.toString(mostrar));
        }
        System.out.println("Presione ENTER para continuar.");
        sc.nextLine();
        Funciones.clear();
    }



    public static String[] leer(RandomAccessFile datos,RandomAccessFile metadato,int posicion) throws IOException {

        int tamanioDatoEnBytes = Metadata.getTamanioMetadataEnBytes(metadato);
        tamanioDatoEnBytes += 1; // ESTE 1 REPRESENTA AL BOOLEAN DE USUARIO ACTIVO/INACTIVO

        int cantidadDeRegistrosDeDatos = (int) (datos.length() / tamanioDatoEnBytes);

        if ((posicion >= cantidadDeRegistrosDeDatos) || (posicion < 0)){
            return new String[0];
        }

        else {
            datos.seek((long) posicion * tamanioDatoEnBytes);

            int cantidadCampos = Metadata.getCantidadRegistros(metadato);
            String registrodatos[] = new String[cantidadCampos + 1]; // MAS UNO POR LA ACTIVIDAD/INACTIVIDAD

            for (int i = 0; i < cantidadCampos; i++) {
                registrodatos[i] = datos.readUTF();
            }

            boolean actividad = datos.readBoolean();
            if (actividad) {
                registrodatos[registrodatos.length-1] = "ACTIVO";
            }
            else registrodatos[registrodatos.length-1] = "INACTIVO";

            return registrodatos;
        }
    }

    public static void modificarInterfaz(RandomAccessFile datos, RandomAccessFile metadato) throws IOException {
        Scanner sc = new Scanner(System.in);
        int posicion = -1;

        System.out.println("Ingrese la posicion a modificar:");
        String num = sc.nextLine();

        while (!Funciones.isNumeric(num)) {
            System.out.println("[ERROR] Dato invalido, ingrese un numero");
            System.out.println("Ingrese la posicion a modificar:");
            num = sc.nextLine();
        }

        posicion = Integer.parseInt(num);

        modificar(datos,metadato,posicion);

        System.out.println("Presione ENTER para continuar.");
        sc.nextLine();
        Funciones.clear();

    }


    public static void modificar(RandomAccessFile datos, RandomAccessFile metadato, int posicion) throws IOException {

        int tamanioDatoEnBytes = Metadata.getTamanioMetadataEnBytes(metadato);
        tamanioDatoEnBytes += 1; // ESTE 1 REPRESENTA AL BOOLEAN DE USUARIO ACTIVO/INACTIVO

        int cantidadDeRegistrosDeDatos = (int) (datos.length() / tamanioDatoEnBytes);

        if ((posicion >= cantidadDeRegistrosDeDatos) || (posicion < 0)){
            System.out.println("No existe registro correspondiente al indice ingresado");
        }

        else {
            datos.seek((long) posicion * tamanioDatoEnBytes);

            int metadataSize = Metadata.getCantidadRegistros(metadato);
            String dato;
            Scanner sc = new Scanner(System.in);

            metadato.seek(0);

            for (int i = 0; i < metadataSize; i++) {
                Registro rg = Metadata.leerMetadato(metadato);
                int tamanioDato = rg.tamanio();

                System.out.println("Presione ENTER para dejar el dato por defecto");
                System.out.println("Cant. maxima de caracteres: " + tamanioDato);
                System.out.println("Ingrese su " + rg.campo() + ": ");

                dato = sc.nextLine();
                dato = Funciones.cambiarTamanioString(dato, tamanioDato);

                if (dato.trim().equals("")) {
                    datos.readUTF();
                } else {
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






}
