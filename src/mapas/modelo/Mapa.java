package mapas.modelo;

import java.io.*;
import java.util.*;

/** Clase para gestionar los mapas: carga desde fichero, consulta de posiciones y pesos, direcciones posibles, ...
 */
public class Mapa {
    private int numFilas;
    private int numColumnas;
    private int[][] celdas;   // Matriz de celdas, con pesos e indicación de obstáculos
    private List<Posicion> gasolineras;  // Posiciones de las gasolineras en el mapa
    public final static int MAX_PESO = Integer.MAX_VALUE;

    public Mapa(String file) {
        this.cargarFichero(file);
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public void setNumColumnas(int numColumnas) {
        this.numColumnas = numColumnas;
    }

    public int getNumFilas() {
        return numFilas;
    }

    public void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }

    /** Cargar mapa desde fichero de textp
     * Formato de entrada:
     *        linea #1: filass=XXX
     *        linea #2: columnas=XXX
     *        linea #3: gasolinera=XX,YY  (opcional, 0 ó más)
     *        lineas sucesivas: matriz de casillas  (1 fila por linea)
     *                          digitos(1..9) para pesos y #  para obstaculos
     *                          casilla superior izquierda = Posicion (0,0)
     */
    public void cargarFichero(String nombreFichero) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(nombreFichero));

            this.numFilas = 0;
            this.numColumnas = 0;
            this.gasolineras = new ArrayList<Posicion>();

            List<String> temp = new ArrayList<String>();

            String linea = in.readLine();
            while (linea != null) {
                if (!linea.startsWith("%")) { // omitir comentarios
                    if (linea.startsWith("filas")) {
                        this.numFilas = Integer.parseInt(linea.substring(linea.lastIndexOf("=") + 1));
                    } else if (linea.startsWith("columnas")) {
                        this.numColumnas = Integer.parseInt(linea.substring(linea.lastIndexOf("=") + 1));
                    } else if (linea.startsWith("gasolinera") || linea.startsWith("gas")) {
                        this.gasolineras.add(new Posicion(linea.substring(linea.lastIndexOf("=") + 1), this));
                    } else { // add a map line
                        temp.add(linea);
                    }
                }
                linea = in.readLine(); // siguiente linea
            }

            // extraer las casillas del mapa fila a fila
            int i = 0;
            if (this.numFilas > 0 && this.numColumnas > 0) {
                this.celdas = new int[this.numFilas][this.numColumnas];

                for (String fila : temp){                    
                    int j = 0;
                    for (char c : fila.toCharArray()) {
                        if (Character.isDigit(c)) {
                            // Es una celda con peso
                            this.celdas[i][j] = Character.getNumericValue(c);
                        } else { // Es un obstaculo
                            this.celdas[i][j] = -1;
                        }
                        j++;  // contador columnas
                    }
                     i++; // contador filas
                }               
            }
            in.close(); 
        } catch (Exception e) {
            System.err.println("ERROR :: cargarFichero :: " + e);
        }
    }

    /** Comprobar si una posición es válida: no es un obstaculo y está dentro de los límites del mapa
     *
     * @param p
     * @return
     */
    public boolean esPosicionValida(Posicion p) {
        return (esPosicionValida(p.getFila(), p.getColumna()));
    }

    /** Comprobar si un par fila y columna es una posición válida: no es un obstaculo y está dentro de los límites del mapa
     *
     */
    public boolean esPosicionValida(int fila, int columna) {
        if ((fila >= 0) && (fila < this.numFilas)
                && (columna >= 0) && (columna < this.numColumnas)) {
            return (celdas[fila][columna] != -1);
        }
        return (false);
    }

    /** Comprobar si un par fila y columna contiene una gasolinera
     *
     * @param fila
     * @param columna
     * @return
     */
    public boolean esGasolinera(int fila, int columna) {
        return (esGasolinera(new Posicion(fila, columna, this)));
    }

    /** Comprobar si la posición contiene una gasolinera
     *
     * @param p
     * @return
     */
    public boolean esGasolinera(Posicion p) {
        return (gasolineras.contains(p));
    }

    /** Obtener el peso de una posición del mapa
     *
     * @param p
     * @return
     */
    public int obtenerPeso(Posicion p) {
        return (obtenerPeso(p.getFila(), p.getColumna()));
    }

    /** Obtener el peso de una posicion del mapa dada su fila y columna*
     */
    public int obtenerPeso(int fila, int columna) {
        if (esPosicionValida(fila, columna)) {
            return (celdas[fila][columna]);
        } else {
            return (MAX_PESO);
        }
    }

    /** Lista de direcciones válidas (no obstáculos y dentro del mapa) para una posición dada
     *
     */
    public List<Direccion> direccionesValidas(Posicion p) {
        if (esPosicionValida(p)) {
            List<Direccion> direccionesValidas = new ArrayList<Direccion>();
            for (Direccion d: Direccion.DIRECCIONES) {
                if (esPosicionValida(p.moverPosicion(d))) {
                    direccionesValidas.add(d);
                }
            }
            return (direccionesValidas);
        } else {
            return (null);
        }
    }

    /** Devuelve la posición de la gasolinera más cerca en base a la distancia euclídea (no tiene en cuenta los pesos)
     *
     * @param p
     * @return
     */
    public Posicion gasolineraMasCercana(Posicion p) {
        if (this.gasolineras != null) {
            double distancia;

            double distanciaMinima = this.getNumFilas() + this.getNumColumnas();
            Posicion gasolineraMasCercana = null;

            for (Posicion aux : this.gasolineras) {
                distancia = calcularDistancia(p, aux);
                if (distancia < distanciaMinima) {
                    gasolineraMasCercana = aux;
                    distanciaMinima = distancia;
                }
            }
            return (gasolineraMasCercana);
        } else {
            return (null); // No hay gasolineras
        }

    }


    /** Calcula la distancia euclídea entre dos posiciones (no considera pesos)
     * 
     * @param a
     * @param b
     * @return
     */
    double calcularDistancia(Posicion a, Posicion b) {
        return (Math.sqrt(Math.pow((a.getFila() - b.getFila()), 2)
                + Math.pow((a.getColumna() - b.getColumna()), 2)));
    }

    /** Guardar en fichero de texto */
    public void guardarEnFichero(String file) {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(file));
            volcar(out);
            out.close();
        } catch (Exception e) {
            System.err.println("ERROR :: guardarEnFichero :: " + e);
        }
    }

    /** Volcar en fichero de texto */
    public void volcar(PrintStream out) {
        this.volcarRuta(null, out);
    }

    /** Volcar en salida estandar */
    public void volcar() {
        this.volcar(System.out);
    }

    /** Volcar mapa e indicación de la ruta en fichero de texto */
    public void volcarRuta(List<Posicion> ruta, PrintStream out) {
        try {
            out.println("filas=" + this.numFilas);
            out.println("columnas=" + this.numColumnas);
            out.println("%");
            if (this.gasolineras != null) {
                for (Posicion p: this.gasolineras){
                    out.println("gasolinera="+p.getFila()+","+p.getColumna());
                }
                out.println("%");
            }
            for (int i = 0; i < this.numFilas; i++) {
                for (int j = 0; j < this.numColumnas; j++) {
                    if (enRuta(i, j, ruta)) {
                        out.print("*");
                    } else {
                        if (celdas[i][j] == -1) {
                            out.print("#");
                        } else {
                            out.print(celdas[i][j]);
                        }
                    }
                }
                out.println();
            }
        } catch (Exception e) {
            System.err.println("ERROR :: volcarRuta :: " + e);
        }

    }

    /** Volcar mapa e indicación de la ruta en salida estandar */
    public void volcarRuta(List<Posicion> ruta) {
        this.volcarRuta(ruta, System.out);
    }

    /** Comprobar su un par fila,columna pertenece a una ruta dada */
    private boolean enRuta(int i, int j, List<Posicion> ruta) {
        boolean resultado = false;
        if (ruta != null) {
            resultado = ruta.contains(new Posicion(i, j, this));
        }
        return (resultado);
    }

    /** Main apra pruebas */
    public static void main(String[] args) {
        System.out.println("Prueba de la clase Mapa");
        System.out.println("Carga el mapa del fichero \"mapa1.txt\" y lo muestra en pantalla.");

        Mapa m = new Mapa("/home/ribadas/NetBeansProjects/mapas_resuelto/src/mapas/ejemplos/mapa1.txt");

        Posicion p = new Posicion(2, 3, m);
        System.out.println("Gasolinera mas cercana a " + p + " en :" + m.gasolineraMasCercana(p));

        m.volcar();
    }
} // Fin class Mapa

