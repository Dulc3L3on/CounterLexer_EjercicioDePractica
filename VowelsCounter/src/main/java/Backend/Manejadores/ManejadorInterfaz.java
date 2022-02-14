/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author ldulc
 */
public class ManejadorInterfaz {
     
    public void setEventoPosicion(JTextArea inputArea, JLabel campoFila, JLabel campoColumna){    
        inputArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) { 
                 JTextArea txtArea = (JTextArea)e.getSource();  
                int linea;  

                try {
                    int posicionCareta = txtArea.getCaretPosition();
                    linea= txtArea.getLineOfOffset(posicionCareta);
                    
                    //se inserta actualizan los valores
                    campoFila.setText(String.valueOf((linea+1)));                    
                    campoColumna.setText(String.valueOf((posicionCareta - txtArea.getLineStartOffset(linea))));
                } catch(BadLocationException ex) { 
                    System.out.println("El usuario se ha colocado en una mala posición");
                }
           }             
        });  
    }
    
}
