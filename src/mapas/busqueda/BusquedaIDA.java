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
    
    public List<Operador> buscarSolucion(Estado inicial) {
        
        double cota;
        double nuevaCota = heuristica.getValor(inicial);
        boolean resuelto = false;
        NodoIDA nodoActual = null;
        
        while( ! resuelto ){
            cota = nuevaCota;
            nuevaCota = Double.MAX_VALUE;
            listaAbiertos.clear();
            listaAbiertos.add( new NodoIDA( inicial , null , null ) );
            while( ! listaAbiertos.isEmpty() && ! resuelto ){
                nodoActual = listaAbiertos.pollFirst();
                if( nodoActual.getEstado().esFinal() ){
                    resuelto = true;
                }else{
                    for( Operador operador : nodoActual.getEstado().operadoresAplicables() ){
                        Estado nuevoEstado = nodoActual.getEstado().aplicarOperador(operador);
                      
                        double f_nuevoEstado =  heuristica.getValor(nuevoEstado) 
                                + operador.getCoste()
                                + nodoActual.getCosteCamino();
                        
                        if( f_nuevoEstado <= cota ){
                            listaAbiertos.addFirst( new NodoIDA( nuevoEstado , nodoActual , operador ) );
                        }else{
                            nuevaCota = Math.min(nuevaCota, f_nuevoEstado );
                        }
                    }
                }
            }
            
        }
         
        LinkedList<Operador> lista = new LinkedList<Operador>();
        if( resuelto ) {
            NodoIDA nodo = nodoActual;
            while ( nodo.getPadre() != null) { 
                lista.addFirst( nodo.getOperador() );
                nodo = (NodoIDA) nodo.getPadre();
            }       
        } 
        return lista;
    }
    
}
