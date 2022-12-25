/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import model.Model;
import model.VillageModel;

/**
 *
 * @author toson
 */
public final class BorderArray
{
    final static double SQRT_3 = Math.sqrt(3d);
  
    
    public static double[] getPointCoord( int row, int col, int i, double L )
    {
        double[] coordArray = new double[2];
        if ( i == 0 )
        {
            coordArray[0] = L/2;
            coordArray[1] = 0d;
        }
        else if ( i == 1 )
        {
            coordArray[0] = 3*L/2;
            coordArray[1] = 0d;
        }
        else if ( i == 2 )
        {
            coordArray[0] = 2*L;
            coordArray[1] = SQRT_3*L/2;
        }
        else if ( i == 3 )
        {
            coordArray[0] = 3*L/2;
            coordArray[1] = SQRT_3*L;
        }
        else if ( i == 4 )
        {
            coordArray[0] = L/2;
            coordArray[1] = SQRT_3*L;
        }
        else
        {
            coordArray[0] = 0d;
            coordArray[1] = SQRT_3 * L / 2;
        }
        
        double offsetX, offsetY;
        offsetX = col*3*L/2;
        offsetY = (col % 2 == 1) ? SQRT_3*L/2 : 0d;
        offsetY = row*SQRT_3*L + offsetY;
        
        coordArray[0] += offsetX;
        coordArray[1] += offsetY;
        
        return coordArray;
    }
    
}
