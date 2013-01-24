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
public class BusquedaAEstrella implements Busqueda{

    private LinkedList<NodoAEstrella> listaAbiertos;
    private LinkedList<NodoAEstrella> listaCerrados;
    private Heuristica heuristica;
    
    public BusquedaAEstrella( Heuristica heuristica ){
        this.heuristica = heuristica;
    }
    
    
    public List<Operador> buscarSolucion(Estado inicial) {
    
        NodoAEstrella nodoInicial = new NodoAEstrella( inicial , null , null , heuristica.getValor(inicial));
        NodoAEstrella nodoActual = null;
        listaAbiertos.clear();
        listaCerrados.clear();
        boolean resuelto = false;
        
        while( ! listaAbiertos.isEmpty() && ! resuelto ){
            nodoActual = listaAbiertos.pollFirst();
            if( nodoActual.getEstado().esFinal() ){
                resuelto = true;
            }else{
                listaCerrados.add( nodoActual );
                for( Operador operador : nodoActual.getEstado().operadoresAplicables() ){
                    Estado estadoSucesor = nodoActual.getEstado().aplicarOperador(operador);
                    double f_nodoSucesor = heuristica.getValor(estadoSucesor);
                    NodoAEstrella nodoSucesor = new NodoAEstrella( estadoSucesor , nodoActual , operador , f_nodoSucesor );
                   
                    if( listaAbiertos.contains( nodoSucesor ) ){
                        int indiceAbierto = listaAbiertos.indexOf( nodoSucesor );
                        if( nodoSucesor.getCosteCamino() < listaAbiertos.get(indiceAbierto).getCosteCamino() ){
                            
                           NodoAEstrella peorNodo = listaAbiertos.get(indiceAbierto);
                           ( (NodoAEstrella) listaAbiertos.get(indiceAbierto).getPadre() ).descolgarHijo( nodoSucesor );
                           listaAbiertos.remove(peorNodo);
                           this.insertaOrdenadoAbiertos(nodoSucesor);
                        }
                    }
                    
                    if( listaCerrados.contains( nodoSucesor ) ){
                        int indiceCerrado = listaCerrados.indexOf( nodoSucesor );
                        if( nodoSucesor.getCosteCamino() < listaCerrados.get(indiceCerrado).getCosteCamino() ){
                            
                            NodoAEstrella peorNodo = listaCerrados.get(indiceCerrado);
                            
                            nodoSucesor.setDescendientes( peorNodo.getDescendientes() );
                            
                            ( (NodoAEstrella) listaCerrados.get(indiceCerrado).getPadre() ).descolgarHijo(nodoSucesor);
                            
                            listaCerrados.remove( peorNodo );
                            nodoSucesor.propagarCosteCamino();
                            this.insertaOrdenadoAbiertos(nodoSucesor);
                        }
                    }
                    
                    if( ! listaAbiertos.contains( nodoSucesor ) && ! listaCerrados.contains( nodoSucesor )){
                        listaAbiertos.add( nodoSucesor );
                    }
                }//end for
            }
        }
        
        LinkedList<Operador> lista = new LinkedList<Operador>();
        if( resuelto ) {
            NodoAEstrella nodo = nodoActual;
            while ( nodo.getPadre() != null) { 
                lista.addFirst( nodo.getOperador() );
                nodo = (NodoAEstrella) nodo.getPadre();
            }       
        } 
        return lista;
    }
    
    
    
    private void insertaOrdenadoAbiertos( NodoAEstrella nodo ){
  
        if( listaAbiertos.isEmpty() ){
            this.listaAbiertos.add( nodo );
        }else{
            
            boolean insertado = false;
            int indice = 0 ;
            
            while( ! insertado && indice < listaAbiertos.size() ){
                if( nodo.getCosteEstimado() >  listaAbiertos.get(indice).getCosteEstimado() ){
                    indice++;
                }else{
                    listaAbiertos.addLast( nodo );
                    insertado = true;
                }
            }
        }
        
        
        
    }
    
}
