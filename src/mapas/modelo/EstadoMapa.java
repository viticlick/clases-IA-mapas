/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas.modelo;

import java.util.ArrayList;
import java.util.List;
import mapas.busqueda.Estado;
import mapas.busqueda.Operador;

/**
 *
 * @author ribadas
 */
public class EstadoMapa implements Estado {

    /** Incremento en el peso de las posiciones cuando se trata de un movivmiento en diagonal */
    private static double FACTOR_DIAGONAL = 1.4142;  // Raiz de 2
    /** Factor para convertir peso de una posicion en consumo de litros */
    private static double FACTOR_CONSUMO = 5;

    private Posicion posicion;
    private double gasolina;
    private double capacidad;
    private Posicion posicionFinal;

    public EstadoMapa(Posicion posicion, double gasolina, double capacidad, Posicion posicionFinal) {
        this.posicion = posicion;
        this.gasolina = gasolina;
        this.capacidad = capacidad;
        this.posicionFinal = posicionFinal;
    }

    public double getGasolina() {
        return gasolina;
    }

    public void setGasolina(double gasolina) {
        this.gasolina = gasolina;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public double getCapacidad() {
        return capacidad;
    }


    public Posicion getPosicionFinal() {
        return posicionFinal;
    }




    /**
     * Determina los operadores aplicables sobre la posición actual:
     *    movimiento en las direcciones válidas + repostaje si hay gasolinera
     *    CORREGIDO 2/12/2010
     * @return
     */
    public List<Operador> operadoresAplicables() {
        List<Operador> operadores = new ArrayList<Operador>();

        if (posicion.esGasolinera() && gasolina < capacidad) {
              // CORRECCION 2/12/2010:  sólo se reposta si hay sitio en el deposito
            operadores.add(new OperadorMapaRepostar());
        }

        for (Direccion d : posicion.direccionesValidas()) {
            // Calcular peso del movimiento sobre la posicion actual
            double peso;
            if (d.esDiagonal()) {
                peso = EstadoMapa.FACTOR_DIAGONAL * posicion.getPeso();
            } else {
                peso = posicion.getPeso();
            }
            
            // Comprobar si hay suficiente gasolina
            double consumo = calculoConsumo(peso);
            if (gasolina >= consumo) {
                operadores.add(new OperadorMapaMovimiento(d, peso));
            }
        }

        return operadores;
    }


    public boolean esFinal() {
        return ((posicion.getColumna() == posicionFinal.getColumna()
                && (posicion.getFila() == posicionFinal.getFila())));


    }

    /** Aplicar el operador indicado para crear un nuevo estado */
    public Estado aplicarOperador(Operador o) {
        if (o instanceof OperadorMapaMovimiento) {
            OperadorMapaMovimiento m = (OperadorMapaMovimiento) o;

            // Convertir coste en el consumo a descontar
            double consumo = calculoConsumo(m.getCoste());            
            return new EstadoMapa(posicion.moverPosicion(m.getDireccion()), (gasolina - consumo), capacidad, posicionFinal);
        } else if (o instanceof OperadorMapaRepostar) {
            // Repostar a tope de capacidad
            return new EstadoMapa(posicion, capacidad, capacidad, posicionFinal);
        } else {
            return null;
        }
    }


    /**
     * Calcula el consumo de gasolina vinculado a un movimiento (redondea a 1 decimal)
     * El consumo no es lineal, sino que es más alta a medida que el coste del movimiento se hace mayor
     * @param costeMovimiento
     * @return 
     */
    private double calculoConsumo(double costeMovimiento){
        
        double consumo = EstadoMapa.FACTOR_CONSUMO * Math.pow(costeMovimiento, 1.5);
        
        // Redondear a 1 decimal
        return (Math.round(consumo*10)/10.0);
    }
    
    /**
     * Extrae un objeto EstadoMapa del parámetro recibido y delega la comparación
     * en el método equals() de la Posicion interna y en la comparación de la gasolina restante
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        EstadoMapa e = (EstadoMapa) o;

        return (posicion.equals(e.getPosicion()) && gasolina == e.getGasolina());
    }

    @Override
    public int hashCode() {
        return (10000*this.posicion.hashCode() + ((int) this.gasolina));
    }

    @Override
    public String toString() {
        return "EstadoMapa{" + "posicion=" + posicion + ", gasolina=" + gasolina + '}';
    }       
}
