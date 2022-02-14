/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Resultado;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ldulc
 */
public class ManejadorRespuesta {
    ArrayList<Resultado> resultadoVocales;
    ArrayList<Resultado> resultadoEnteros;
    ArrayList<Resultado> errores;
    
    public ManejadorRespuesta(ArrayList<Resultado> resultadoVocales, ArrayList<Resultado> resultadoEnteros, 
            ArrayList<Resultado> errores){
        this.resultadoVocales = resultadoVocales;
        this.resultadoEnteros = resultadoEnteros;
        this.errores = errores;    
    }
    
    public void setResultados(JTable tablaResumen,int[] totalResultados, 
            JTable tablaDetalleVocales, JTable tablaDetalleEnteros, JTable tablaErrores){
        this.setResumen(tablaResumen, totalResultados);
        this.setResultadosVocales(tablaDetalleVocales);
        this.setResultadosEnteros(tablaDetalleEnteros);
        this.setErrores(tablaErrores);
        
        tablaResumen.updateUI();
        tablaDetalleVocales.updateUI();
        tablaDetalleEnteros.updateUI();
        tablaErrores.updateUI();
    }
    
    private void setResumen(JTable tabla, int[] totales){
        DefaultTableModel modeloResumen = (DefaultTableModel) tabla.getModel();
        
        eliminarTodosLosRegistros(modeloResumen, tabla);
        //no hay un métood para editar los datos de las filas entonces para no tener que estar seteando cada vez el nombre de las columnas y el tamaño y crear cada vez un nuevo objeto modelo, mejor elimino los registros
        //puesto que no serán muchos entonces hay menor complejidad en esto xD [EDD xD],
        //otra cosa que pudiste haber hecho es addle un nuevo modelo xD, solo tendrías que crear las 3 columnas xD        
        
        modeloResumen.addRow(new Object[]{1, "Cantidad de palabras con 1 vocal", totales[0]});
        modeloResumen.addRow(new Object[]{2, "Cantidad de palabras con 2 vocales", totales[1]});
        modeloResumen.addRow(new Object[]{3, "Cantidad de palabras con 3 vocales", totales[2]});
        modeloResumen.addRow(new Object[]{4, "Cantidad de palabras con 4 vocales", totales[3]});
        modeloResumen.addRow(new Object[]{5, "Cantidad de palabras con 5 vocales", totales[4]});        
        modeloResumen.addRow(new Object[]{6, "Cantidad de números enteros", totales[5]});
        modeloResumen.addRow(new Object[]{7, "Cantidad de números errores", totales[6]});      
        
        //tabla.setModel(modeloResumen);//no es necesario porque es el mismo modelo... quizá un updateUI() si xD
    }
    
    private void setResultadosVocales(JTable tabla){
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        eliminarTodosLosRegistros(modelo, tabla);
        
        for (int elementoActual = 0; elementoActual < this.resultadoVocales.size(); elementoActual++) {
            modelo.addRow(new Object[]{elementoActual+1, 
                this.resultadoVocales.get(elementoActual).getContenido(),
                this.resultadoVocales.get(elementoActual).getTipo(), 
                this.resultadoVocales.get(elementoActual).getPosicionLinea(),
                this.resultadoVocales.get(elementoActual).getPosicionColumna()});
        }
    }
    
    private void setResultadosEnteros(JTable tabla){
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        eliminarTodosLosRegistros(modelo, tabla);
        
        for (int elementoActual = 0; elementoActual < this.resultadoEnteros.size(); elementoActual++) {
             modelo.addRow(new Object[]{elementoActual+1, 
                Integer.parseInt(this.resultadoEnteros.get(elementoActual).getContenido()),
                this.resultadoEnteros.get(elementoActual).getPosicionLinea(),
                this.resultadoEnteros.get(elementoActual).getPosicionColumna()});
        }
    }
    
    private void setErrores(JTable tabla){
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        eliminarTodosLosRegistros(modelo, tabla);
        
        for (int elementoActual = 0; elementoActual < this.errores.size(); elementoActual++) {
             modelo.addRow(new Object[]{elementoActual+1, 
                this.errores.get(elementoActual).getContenido(),
                "elemento(s) inválidos",
                this.errores.get(elementoActual).getPosicionLinea(),
                this.errores.get(elementoActual).getPosicionColumna()});
        }
    }
    
    private void eliminarTodosLosRegistros(DefaultTableModel modelo, JTable tabla){
        try {            
            while(tabla.getRowCount()>0){
                modelo.removeRow(0);                
            }
            
            tabla.updateUI();
        } catch (Exception e) {
            System.out.println("Error al limpiar la tabla" + e.getMessage());
        }        
    }      
}
