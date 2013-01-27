/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas.busqueda;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author zarapalleto
 */
public class BusquedaIDA implements Busqueda{

    private Heuristica heuristica;
    private LinkedList<NodoIDA> listaAbiertos;
    
    public BusquedaIDA( Heuristica heuristica ){
        this.heuristica = heuristica;
        this.listaAbiertos = new LinkedList<NodoIDA>();
    }
  
    /**
     * Realiza la búsqueda de la solución según el algoritmo
     * @param inicial Estado de partida
     * @return Lista de operadores aplicables al estado inicial para llegar a la solución
     */    
    public List<Operador> buscarSolucion(Estado inicial) {
        
        double cota;
        double nuevaCota = heuristica.getValor(inicial);
        boolean resuelto = false;
        NodoIDA nodoActual = null;
        
        while( ! resuelto ){
            cota = nuevaCota;
            nuevaCota = Double.MAX_VALUE;   //Se le asigna el mayor valor a la cota
            listaAbiertos.clear();          //Se borra la lista de ABIERTOS
            listaAbiertos.add( new NodoIDA( inicial , null , null ) );
            while( ! listaAbiertos.isEmpty() && ! resuelto ){
                nodoActual = listaAbiertos.pollFirst();     //Se reupera el primer elemento de la lista de ABIERTOS
                if( nodoActual.getEstado().esFinal() ){
                    resuelto = true;
                }else{
                    
                    //Se expande el nodo actual, y se generan los hijos posibles.
                    for( Operador operador : nodoActual.getEstado().operadoresAplicables() ){
                        Estado nuevoEstado = nodoActual.getEstado().aplicarOperador(operador);
                      
                        double f_nuevoEstado =  heuristica.getValor(nuevoEstado) 
                                + operador.getCoste()
                                + nodoActual.getCosteCamino();
                        //Si el valor de f(e) del nuevo nodo es menor que la cota, se añade el nodo al comienzo de ABIERTOS
                        if( f_nuevoEstado <= cota ){
                            listaAbiertos.addFirst( new NodoIDA( nuevoEstado , nodoActual , operador ) );
                        }else{
                            //Se le da el valor a la nueva cota
                            nuevaCota = Math.min(nuevaCota, f_nuevoEstado );
                        }
                    }
                }
            }
            
        }
         
        LinkedList<Operador> lista = new LinkedList<Operador>();
        if( resuelto ) {
            NodoIDA nodo = nodoActual;
            while ( nodo.getPadre() != null) {          //Mientras haya padre
                lista.addFirst( nodo.getOperador() );   //extrae el operador de movimiento
                nodo = (NodoIDA) nodo.getPadre();       //extrae el nuevo padre
            }           
        } 
        return lista;
    }
    
}
