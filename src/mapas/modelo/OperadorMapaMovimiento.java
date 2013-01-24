/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas.modelo;

import mapas.busqueda.Operador;

/**
 * Implementa el interfaz Operador encapsulando un movimiento en el mapa (en una dirección y con un coste)
 * @author ribadas
 */
public class OperadorMapaMovimiento extends OperadorMapa {

    /** Movimiento asociado al operador */
    private Direccion direccion;
    private double coste;

    public OperadorMapaMovimiento(Direccion direccion, double coste) {
        this.direccion = direccion;
        this.coste = coste;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public String getEtiqueta() {
        return (direccion.getNombre());
    }
}
