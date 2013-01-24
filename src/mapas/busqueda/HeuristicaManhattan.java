/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas.busqueda;

import mapas.modelo.Posicion;

/**
 *
 * @author zarapalleto
 */
public class HeuristicaManhattan {

    private int posicionObjetivoY;
    private int posicionObjetivoX;
    
    public HeuristicaManhattan( Posicion posicionObjetivo ){
        this.posicionObjetivoY = posicionObjetivo.getColumna();
        this.posicionObjetivoX = posicionObjetivo.getFila();
    }

    public HeuristicaManhattan() {
        this.posicionObjetivoY=0;
        this.posicionObjetivoX=0;
    }
    
    public void setObjetivo( Posicion posicionObjetivo ){
        this.posicionObjetivoY = posicionObjetivo.getColumna();
        this.posicionObjetivoX = posicionObjetivo.getFila();
    }
    
    public void setObjetivo( int posicionObjetivoX , int posicionObjetivoY ){
        this.posicionObjetivoX = posicionObjetivoX;
        this.posicionObjetivoY = posicionObjetivoY;
    }
    
    /**
     * Calula la heurística entre dos posiciones dadas
     * @param posicionInicialFila       posicion inicial en las filas
     * @param posicionInicialColumna    posicion inicial en las columnas
     * @param posicionObjetivoFila      posicion objetivo en las filas
     * @param posicionObjetivoColumna   posicion objetivo en las columnas
     * @return la heurístia de la posición correspondiente con el objetivo
     */
    public double getValor( int posicionInicialFila , int posicionInicialColumna , int posicionObjetivoFila , int posicionObjetivoColumna ){
        
        double distanciaX;
        double distanciaY;
        
        distanciaY = Math.abs( posicionInicialColumna  - posicionObjetivoColumna );
        distanciaX = Math.abs( posicionInicialFila     - posicionObjetivoFila );
        
        if( distanciaX > distanciaY ){
            return 10 * (1.4142 * distanciaY + distanciaX - distanciaY );
        }else{
            return 10 * (1.4142 * distanciaX + distanciaY - distanciaX );
        }
        
    }
    
    public double getValor(Estado e) {
        
        int posicionColumna = e.getPosicion().getColumna();
        int posicionFila =    e.getPosicion().getFila();

        return this.getValor( posicionFila , posicionColumna , this.posicionObjetivoX , this.posicionObjetivoY );
        
    }
    
    public double getValor( Posicion actual , Posicion objetivo  ){
        
        int posicionActualX = actual.getFila();
        int posicionActualY = actual.getColumna();
        int posicionXObjetivo = objetivo.getFila();
        int posicionYObjetivo = objetivo.getColumna();
        
        return this.getValor( posicionActualX , posicionActualY , posicionXObjetivo, posicionYObjetivo );
        
        
    }
    
    
    public double getValor( Posicion posicion ){
        int posicionColumna = posicion.getColumna();
        int posicionFila =    posicion.getFila();

        return this.getValor( posicionFila , posicionColumna , this.posicionObjetivoX , this.posicionObjetivoY );
    }
    

    public double getValor( int posicionFila , int posicionColumna ){
        
        return getValor( posicionFila , posicionColumna , this.posicionObjetivoX , this.posicionObjetivoY );
    }

    public int getPosicionObjetivoX() {
        return posicionObjetivoX;
    }

    public int getPosicionObjetivoY() {
        return posicionObjetivoY;
    }   
}
