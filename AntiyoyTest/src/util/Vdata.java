/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author toson
 */
public class Vdata implements Comparable<Vdata>
{
    
    public enum Color
    {
        WHITE,
        BLACK
    };
    
    
    public Color hexColor = Color.WHITE;
    public int[] parent = new int[2];
    public int d = Integer.MAX_VALUE;
    public int[] coord = new int[2];
        
    @Override
    public int compareTo(Vdata other)
    {
        return this.d - other.d;
    }
        
    public Vdata( int row, int col )
    {
        coord[0] = row;
        coord[1] = col;
        parent[0] = -1;
        parent[1] = -1;
    }
       
}
