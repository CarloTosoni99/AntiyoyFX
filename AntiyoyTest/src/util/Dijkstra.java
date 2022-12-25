/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.*; 
/**
 *
 * @author toson
 */
public class Dijkstra 
{
 
 
    public Vdata[][] nodeArray;
    public int[][] weight;
  
    public Dijkstra(int rows, int cols, int[][] weight) 
    {  
        this.nodeArray = new Vdata[rows][cols];
        for( int i = 0; i < nodeArray.length; ++i )
            for ( int j = 0;j < nodeArray[i].length; ++j )
                nodeArray[i][j] = new Vdata( i, j);
        
        this.weight = weight;
        
            
    } 
    
    public Vdata[][] makeDijkstra( int srcX, int srcY )
    {
        nodeArray[srcX][srcY].d = 0;  
        PriorityQueue<Vdata> pQueue = new PriorityQueue<Vdata>();
        
        List<Integer> ssp = new ArrayList<>();
        
        for( int i = 0; i < nodeArray.length; ++i )
            for( int j = 0; j < nodeArray[i].length; ++j )
                pQueue.add( nodeArray[i][j] );
        
      
        while ( !pQueue.isEmpty() )
        {
            Vdata node = pQueue.peek();
            node.hexColor = Vdata.Color.BLACK;
            
            int row = node.coord[0];
            int col = node.coord[1];
            
            if ( row - 1 >= 0 )
            {
                int edgeWeight;
                if ( weight[row][col] == 1 || weight[row - 1][col] == 1 )
                    edgeWeight = 1;
                else
                    edgeWeight = 0;
                
                if ( nodeArray[row - 1][col].d > nodeArray[row][col].d + edgeWeight  && nodeArray[row - 1][col].hexColor == Vdata.Color.WHITE )
                {
                    pQueue.remove( nodeArray[row - 1][col] );
                    nodeArray[row - 1][col].d = nodeArray[row][col].d + edgeWeight;
                    nodeArray[row - 1][col].parent[0] = row;
                    nodeArray[row - 1][col].parent[1] = col;
                    pQueue.add( nodeArray[row - 1][col] );   
                }
            }
            
            if ( col + 1 < nodeArray[0].length )
            {
                int edgeWeight;
                if ( weight[row][col] == 1 || weight[row][col + 1] == 1 )
                    edgeWeight = 1;
                else
                    edgeWeight = 0;
                
                if ( nodeArray[row][col + 1].d > nodeArray[row][col].d + edgeWeight && nodeArray[row][col + 1].hexColor == Vdata.Color.WHITE)
                {
                    pQueue.remove( nodeArray[row][col + 1] );
                    nodeArray[row][col + 1].d = nodeArray[row][col].d + edgeWeight;
                    nodeArray[row][col + 1].parent[0] = row;
                    nodeArray[row][col + 1].parent[1] = col;
                    pQueue.add(nodeArray[row][col + 1]);
                }
            }
            
            if ( row + 1 < nodeArray.length )
            {
                int edgeWeight;
                if ( weight[row][col] == 1 || weight[row + 1][col] == 1 )
                    edgeWeight = 1;
                else
                    edgeWeight = 0;
                
                if ( nodeArray[row + 1][col].d > nodeArray[row][col].d + edgeWeight && nodeArray[row + 1][col].hexColor == Vdata.Color.WHITE )
                {
                    pQueue.remove( nodeArray[row + 1][col] );
                    nodeArray[row + 1][col].d = nodeArray[row][col].d + edgeWeight;
                    nodeArray[row + 1][col].parent[0] = row;
                    nodeArray[row + 1][col].parent[1] = col;
                    pQueue.add( nodeArray[row + 1][col]);
                }
                    
            }
            
            if ( col - 1 >= 0 )
            {
                int edgeWeight;
                if ( weight[row][col] == 1 || weight[row][col - 1] == 1 )
                    edgeWeight = 1;
                else
                    edgeWeight = 0;
                
                if ( nodeArray[row][col - 1].d > nodeArray[row][col].d + edgeWeight && nodeArray[row][col - 1].hexColor == Vdata.Color.WHITE)
                {
                    pQueue.remove( nodeArray[row][col - 1] );
                    nodeArray[row][col - 1].d = nodeArray[row][col].d + edgeWeight;
                    nodeArray[row][col - 1].parent[0] = row;
                    nodeArray[row][col - 1].parent[1] = col;
                    pQueue.add( nodeArray[row][col - 1]);
                }
            }
            
            if ( col % 2 == 0 )
            {
                if ( row - 1 >= 0 && col - 1 >= 0)
                {
                    int edgeWeight;
                    if ( weight[row][col] == 1 || weight[row - 1][col - 1] == 1 )
                        edgeWeight = 1;
                    else
                        edgeWeight = 0;
                
                    if ( nodeArray[row - 1][col - 1].d > nodeArray[row][col].d + edgeWeight && nodeArray[row - 1][col - 1].hexColor == Vdata.Color.WHITE)
                    {
                        pQueue.remove( nodeArray[row - 1][col - 1]);
                        nodeArray[row - 1][col - 1].d = nodeArray[row][col].d + edgeWeight;
                        nodeArray[row - 1][col - 1].parent[0] = row;
                        nodeArray[row - 1][col - 1].parent[1] = col;
                        pQueue.add( nodeArray[row - 1][col - 1]);
                    }
                }
                
                if ( row - 1 >= 0 && col + 1 < nodeArray[0].length )
                {
                    int edgeWeight;
                    if ( weight[row][col] == 1 || weight[row - 1][col + 1] == 1 )
                        edgeWeight = 1;
                    else
                        edgeWeight = 0;
                
                    if ( nodeArray[row - 1][col + 1].d > nodeArray[row][col].d + edgeWeight && nodeArray[row - 1][col + 1].hexColor == Vdata.Color.WHITE)
                    {
                        pQueue.remove( nodeArray[row - 1][col + 1]);
                        nodeArray[row - 1][col + 1].d = nodeArray[row][col].d + edgeWeight;
                        nodeArray[row - 1][col + 1].parent[0] = row;
                        nodeArray[row - 1][col + 1].parent[1] = col;
                        pQueue.add( nodeArray[row - 1][col + 1]);
                    }
                }
            }
            
            else
            {
                if ( row + 1 < nodeArray.length && col + 1 < nodeArray[0].length)
                {
                    int edgeWeight;
                    if ( weight[row][col] == 1  || weight[row + 1][col + 1] == 1)
                        edgeWeight = 1;
                    else
                        edgeWeight = 0;
                
                    if ( nodeArray[row + 1][col + 1].d > nodeArray[row][col].d + edgeWeight && nodeArray[row + 1][col + 1].hexColor == Vdata.Color.WHITE)
                    {
                        pQueue.remove( nodeArray[row + 1][col + 1]);
                        nodeArray[row + 1][col + 1].d = nodeArray[row][col].d + edgeWeight;
                        nodeArray[row + 1][col + 1].parent[0] = row;
                        nodeArray[row + 1][col + 1].parent[1] = col;
                        pQueue.add( nodeArray[row + 1][col + 1]);
                    }
                }
                
                if ( row + 1 < nodeArray.length && col - 1 >= 0)
                {
                    int edgeWeight;
                    if ( weight[row][col] == 1 || weight[row + 1][col - 1] == 1 )
                        edgeWeight = 1;
                    else
                        edgeWeight = 0;
                
                    if ( nodeArray[row + 1][col - 1].d > nodeArray[row][col].d + edgeWeight && nodeArray[row + 1][col - 1].hexColor == Vdata.Color.WHITE)
                    {
                        pQueue.remove( nodeArray[row + 1][col - 1]);
                        nodeArray[row + 1][col - 1].d = nodeArray[row][col].d + edgeWeight;
                        nodeArray[row + 1][col - 1].parent[0] = row;
                        nodeArray[row + 1][col - 1].parent[1] = col;
                        pQueue.add( nodeArray[row + 1][col - 1]);
                    }
                }
            }
            pQueue.poll();
        }
        
        return nodeArray;
    }
}
