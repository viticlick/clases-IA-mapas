package mapas.modelo;


import java.util.List;

/**
 * Clase que encapsula una posicion (fila, columna) en un determinado mapa.
 * Delega en el mapa el acceso a los pesos de las casillas,
 * la consulta de si es gasolinera y obtener la lista de direcciones válidas
 * @author ribadas
 */
public class Posicion {

    private int fila;
    private int columna;
    private Mapa mapa;

    public Posicion(int fila, int columna, Mapa mapa) {
        this.fila = fila;
        this.columna = columna;
        this.mapa = mapa;
    }

    public Posicion(String str, Mapa mapa) {
        int coma = str.lastIndexOf(",");

        this.fila = Integer.parseInt(str.substring(0, coma));
        this.columna = Integer.parseInt(str.substring(coma + 1, str.length()));
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }


    public double getPeso(){
        return mapa.obtenerPeso(this);
    }

    public boolean esGasolinera() {
        return mapa.esGasolinera(this);
    }


    public Posicion gasolineraMasCercana(){
        return mapa.gasolineraMasCercana(this);
    }

    public List<Direccion> direccionesValidas(){
        return mapa.direccionesValidas(this);
    }



    /**
     * Crea la nueva posición resultante de moverse en la dirección recibida como parámetro
     * @param dir
     * @return
     */
    public Posicion moverPosicion(Direccion dir) {
        switch (dir.getTipo()) {
            case Direccion.NORTE:
                return (new Posicion(fila - 1, columna, this.getMapa()));
            case Direccion.SUR:
                return (new Posicion(fila + 1, columna, this.getMapa()));
            case Direccion.ESTE:
                return (new Posicion(fila, columna + 1, this.getMapa()));
            case Direccion.OESTE:
                return (new Posicion(fila, columna - 1, this.getMapa()));

            case Direccion.NORESTE:
                return (new Posicion(fila - 1, columna + 1, this.getMapa()));
            case Direccion.NOROESTE:
                return (new Posicion(fila - 1, columna - 1, this.getMapa()));
            case Direccion.SUDESTE:
                return (new Posicion(fila + 1, columna + 1, this.getMapa()));
            case Direccion.SUDOESTE:
                return (new Posicion(fila + 1, columna - 1, this.getMapa()));
            default:
                return (null);
        }
    }

    
    @Override
    public String toString() {
        return ("(" + this.fila + "," + this.columna + ")");
    }

    /** Reescribe el método equals para que la igualdad de posiciones exiga coincidencia de fila y columna
     * (necesario para que funcionen correctamente los métodos contains de listas y hashes)
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        Posicion p = (Posicion) o;
        return ((this.fila == p.getFila()) && (this.columna == p.getColumna()));
    }

    /** Calcula el valor hash del objeto en base a sus coordenadas
     *  (necesario para que funcionen correctamente los métodos contains de listas y hashes)
     */
    @Override
    public int hashCode() {
        return (1000 * this.fila + this.columna);
    }
}  // Fin class Position

