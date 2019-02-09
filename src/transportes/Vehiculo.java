/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transportes;

/**
 *
 * @author Usuario Estandar
 */
public class Vehiculo {
    public static final int MAX_PASAJEROS = 10;
    private char destino;
    private int pasajeros = 0;
        
    public Vehiculo(char destino) {
        this.destino = destino;
    }
        
    public char getDestino() {
        return destino;
    }
        
    public int getPasajeros() {
        return pasajeros;
    }
    
    public boolean addPasajeros(int pasajeros) {
        if ( MAX_PASAJEROS < this.pasajeros + pasajeros )
            return false;
        
        this.pasajeros += pasajeros;
        return true;
    }
}
