/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import mapas.busqueda.BusquedaAnchura;
import mapas.modelo.OperadorMapaMovimiento;
import mapas.busqueda.Operador;
import mapas.busqueda.Problema;
import mapas.modelo.EstadoMapa;
import mapas.modelo.Mapa;
import mapas.modelo.Posicion;

/**
 *
 * @author rp
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Comprobar parámetros
        if (args.length != 7) {
            mensajeAyuda();

            args = new String[7];
            args[0] = leerLinea("Fichero mapa: ");
            args[1] = leerLinea("Capacidad deposito: ");
            args[2] = leerLinea("Gasolina inicial: ");
            args[3] = leerLinea("Fila inicial: ");
            args[4] = leerLinea("Columna inicial: ");
            args[5] = leerLinea("Fila final: ");
            args[6] = leerLinea("Columna final: ");
        }

        // Cargar parametros
        Mapa mapa = new Mapa(args[0]);
        int capacidadDeposito = Integer.parseInt(args[1]);
        int gasolinaInicial = Integer.parseInt(args[2]);
        Posicion posicionInicial = new Posicion(Integer.parseInt(args[3]), Integer.parseInt(args[4]), mapa);
        Posicion posicionFinal = new Posicion(Integer.parseInt(args[5]), Integer.parseInt(args[6]), mapa);



        Problema problema = new Problema();
        problema.setInicial(new EstadoMapa(posicionInicial, gasolinaInicial, capacidadDeposito, posicionFinal));
        problema.setBuscador(new BusquedaAnchura()); // Busqueda en anchura
        // problema.setBuscador(new BusquedaProfundidad()); // Busqueda en profundidad


        System.out.println("SOLUCION:");
        List<Operador> operadoresSolucion = problema.obtenerSolucion();
        if (operadoresSolucion != null) {
            List<Posicion> ruta = new LinkedList<Posicion>();
            Posicion posicionActual = posicionInicial;
            ruta.add(posicionActual);
            System.out.println("Movimientos:");
            for (Operador o : operadoresSolucion) {
                System.out.print("\t" + o.getEtiqueta());
                if (o instanceof OperadorMapaMovimiento) {
                    // Anotar la posición por donde pasa en la lista "ruta"
                    OperadorMapaMovimiento om = (OperadorMapaMovimiento) o;
                    posicionActual = posicionActual.moverPosicion(om.getDireccion());
                    ruta.add(posicionActual);
                    System.out.print(" -> "+posicionActual.toString());
                }
                System.out.println();
            }
            mapa.volcarRuta(ruta);
            System.out.println();
        } else {
            System.out.println("no se ha encontrado solución");
        }

    }

    public static void mensajeAyuda() {
        System.out.println("Sintaxis:  java mapas.Main fichero_mapa capacidad_deposito gasolina_inicial "
                + "fila_inicial columna_inicial fila_final columna_final");
    }

    private static String leerLinea(String mensaje) throws IOException {
        byte[] buffer = new byte[200];

        System.out.print(mensaje);

        int i = 0;
        int c = System.in.read();
        while (c != '\n') {
            buffer[i] = (byte) c;
            i++;
            c = System.in.read();
        }

        return (new String(buffer, 0, i));


    }
}
