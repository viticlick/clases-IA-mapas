/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapas.busqueda;

import java.util.List;

/**
 * Clase genérica (indepeniente de estados y algoritmos concretos) que representa 
 * un problema de búsqueda en espacio de estados.
 * Está caracterizado por un Estado inicial y un método de Busqueda
 * @author rp
 */
public class Problema {
    /** Estado inicial del problema*/
    private Estado inicial;
    /** Método de busqueda e utilizar en la resolución del problema */
    private Busqueda buscador;

    public Problema() {
        this.inicial = null;
        this.buscador = null;
    }

    public Problema(Estado inicial) {
        this.inicial = inicial;
    }

    
    
    public Problema(Estado inicial, Busqueda buscador) {
        this.inicial = inicial;
        this.buscador = buscador;
    }

    public void setBuscador(Busqueda buscador) {
        this.buscador = buscador;
    }

    public void setInicial(Estado inicial) {
        this.inicial = inicial;
    }
    
    /**
     * Aplica el método de Busqueda de este Problema concreto para resolverlo. 
     * Devuelve la lista de Operadores que permiten alcanzar un Estado final desde
     * el Estado inicial del Problema
     * @return null o Vector de Operadores
     */
    public List<Operador> obtenerSolucion() {
        if ((buscador != null) && (inicial != null)) {
            return(buscador.buscarSolucion(inicial));
        }
        else  {
            return null;
        }
    }
    
    
    
}
