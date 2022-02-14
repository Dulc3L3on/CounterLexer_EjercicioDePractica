/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos;

import java.util.ArrayList;

/**
 *
 * @author ldulc
 */
public class Resultado {
    private int tipo;//1 -> 1 vocal, 2-> 2 vocales ... 6 -> error
    private final String contenido;//lexema
    private final int posicionLinea;
    private final int posicionColumna;
    
    public Resultado(String contenido, int posicionLinea, int posicionColumna){
        //this.tipo = tipo;//puesto que no se sabrá hasta que se haga el recuento de las vocales...
        this.contenido = contenido;
        this.posicionLinea = posicionLinea;
        this.posicionColumna = posicionColumna;
    }
    
    public Resultado(int tipo, String contenido, int posicionLinea, int posicionColumna){
        this.tipo = tipo;//puesto que para los enteros se sabrá de una vez el tipo, igual que para los de error xD
        this.contenido = contenido;
        this.posicionLinea = posicionLinea;
        this.posicionColumna = posicionColumna;
    }

    public void setTipo(int tipo){//para el error no es obligatorio setearlo, puesto que se separarán en diferentes listas...
        this.tipo = tipo;
    }
    
    public int getTipo() {
        return tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public int getPosicionLinea() {
        return posicionLinea;
    }

    public int getPosicionColumna() {
        return posicionColumna;
    }
    
    //si pongo el tipo de error con -1, bien podría guardar en la lista palabras con cualquier número de vocales
    //pero como no piden eso, entonces lo dejaremos así e ignoraremos el lexema si la cdad es >5 xD
}
