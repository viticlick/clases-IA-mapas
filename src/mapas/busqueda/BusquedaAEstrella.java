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
        this.listaAbiertos = new LinkedList<NodoAEstrella>();
        this.listaCerrados = new LinkedList<NodoAEstrella>();
    }
    
    
    public List<Operador> buscarSolucion(Estado inicial) {
    
        NodoAEstrella nodoInicial = new NodoAEstrella( inicial , null , null , heuristica.getValor(inicial));
        NodoAEstrella nodoActual = null;
        listaAbiertos.clear();
        listaCerrados.clear();
        boolean resuelto = false;
        listaAbiertos.add(nodoInicial);
        
        while( ! listaAbiertos.isEmpty() && ! resuelto ){
            nodoActual = listaAbiertos.pollFirst(); //Extrae el primer NODO de la lista de ABIERTOS
            if( nodoActual.getEstado().esFinal() ){
                resuelto = true;
            }else{
                listaCerrados.add( nodoActual );
                //Genera los ESTADOS_SUCESORES del nodo ACTUAL
                for( Operador operador : nodoActual.getEstado().operadoresAplicables() ){
                    Estado estadoSucesor = nodoActual.getEstado().aplicarOperador(operador);
                    double h_nodoSucesor = heuristica.getValor(estadoSucesor); //Obtiene la heuristica del estado h(e)
                    NodoAEstrella nodoSucesor = new NodoAEstrella( estadoSucesor , nodoActual , operador , h_nodoSucesor );
                   
                    //Si el SUCESOR está en ABIERTOS
                    if( listaAbiertos.contains( nodoSucesor ) ){
                        int indiceAbierto = listaAbiertos.indexOf( nodoSucesor );
                        
                        //Si SUCESOR en ABIERTOS con peor g(e)
                        if( nodoSucesor.getCosteCamino() < listaAbiertos.get(indiceAbierto).getCosteCamino() ){
                            
                           NodoAEstrella peorNodo = listaAbiertos.get(indiceAbierto);
                           
                           //Elimina del nodo padre el sucesor con peor g(e)
                           ( (NodoAEstrella) listaAbiertos.get(indiceAbierto).getPadre() ).descolgarHijo( nodoSucesor );
                           listaAbiertos.remove(peorNodo);              //Se elimina el nodo anterior de la lista de ABIERTOS
                           this.insertaOrdenadoAbiertos(nodoSucesor);   //Inserta el nuevo NODO en la lista de ABIERTOS
                        }
                    }
                    //Si el SUCESOR está en CERRADOS
                    if( listaCerrados.contains( nodoSucesor ) ){
                        int indiceCerrado = listaCerrados.indexOf( nodoSucesor );
                        
                        //Si SUCESOR en CERRADOS con peor g(e)
                        if( nodoSucesor.getCosteCamino() < listaCerrados.get(indiceCerrado).getCosteCamino() ){
                            
                            NodoAEstrella peorNodo = listaCerrados.get(indiceCerrado);
                            
                            //Se recuperan los descendientes del nodo de CERRADOS
                            nodoSucesor.setDescendientes( peorNodo.getDescendientes() );
                            
                            //Se elimina del padre el nuevo nodo con mejores g(e) y f(e)
                            ( (NodoAEstrella) listaCerrados.get(indiceCerrado).getPadre() ).descolgarHijo(nodoSucesor);
                           
                            listaCerrados.remove( peorNodo );  //Se elimina el nodo anterior de la lista de CERRADOS
                            nodoSucesor.propagarCosteCamino(); //Se propaga el valor de g(e) y f(e) a los nodos hijos
                            //this.insertaOrdenadoAbiertos(nodoSucesor); //Inserta el nodo en la lista de ABIERTOS
                        }
                    }
                    //Si no se encuenta el SUCESOR en ABIERTOS ni CERRADOS se inserta en ABIERTOS
                    if( ! listaAbiertos.contains( nodoSucesor ) && ! listaCerrados.contains( nodoSucesor )){
                        this.insertaOrdenadoAbiertos(nodoSucesor);
                    }
                }//end for
            }
        }
        
        LinkedList<Operador> lista = new LinkedList<Operador>();
        if( resuelto ) {
            NodoAEstrella nodo = nodoActual;
            while ( nodo.getPadre() != null) {          //Mientras haya padre
                lista.addFirst( nodo.getOperador() );   //extrae operador de movimiento
                nodo = (NodoAEstrella) nodo.getPadre(); //extrae el padre
            }       
        } 
        return lista;
    }
    
    
    /**
     * Inserta un nodo en la lista de Abiertos según su valor de f(e) o coste Estimado
     * @param nodo Nodo a insertar
     */
    private void insertaOrdenadoAbiertos( NodoAEstrella nodo ){
  
        if( listaAbiertos.isEmpty() ){
            this.listaAbiertos.add( nodo );
        }else{
            
            boolean insertado = false;
            int indice = 0 ;
            
            while( ! insertado && indice < listaAbiertos.size() ){  //Recorre la lista de abiertos
                
                //Desplaza el índice hacia el final, mientras el coste sea mayor
                if( nodo.getCosteEstimado() >  listaAbiertos.get(indice).getCosteEstimado() ){  
                    indice++;
                }else{
                    listaAbiertos.add(indice, nodo);
                    insertado = true;
                }
            }
            if( ! insertado ){
                listaAbiertos.addLast(nodo); //Inserta el elemento al final de la lista
            }
        }
        
        
        
    }
    
}
