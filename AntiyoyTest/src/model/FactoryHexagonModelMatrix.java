/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.Dijkstra;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import util.BFS;
import util.Vdata;

/**
 *
 * @author toson
 */
public class FactoryHexagonModelMatrix 
{
    private enum Color
    {
        WHITE,
        GRAY,
        BLACK
    };
    
    private static HexagonModel[][] hexagonModelMatrix;
    private static Color[][] colorMatrix;
    
    private final static int START_MONEY = 10;
    
    private final static int SMALL_X_DIMENSION = 15;
    private final static int SMALL_Y_DIMENSION = 13;
    
    private final static int MEDIUM_X_DIMENSION = 23;
    private final static int MEDIUM_Y_DIMENSION = 19;
    
    private final static int BIG_X_DIMENSION = 31;
    private final static int BIG_Y_DIMENSION = 25;
    
    public static HexagonModel[][] createHexagonModelMatrix ( int dimension, boolean[] colorsArray )
    {
        List<Integer> repr = new LinkedList<>();
        int maxWaterTiles = 0;
        Model model = Model.getModel();
        
        if( dimension == 1 )
        {
            hexagonModelMatrix = new HexagonModel[SMALL_Y_DIMENSION][SMALL_X_DIMENSION];
            
            for ( int i = 0; i < hexagonModelMatrix.length; ++i )
                for ( int j = 0; j < hexagonModelMatrix[i].length; ++j )
                    hexagonModelMatrix[i][j] = new HexagonModel( -1, i, j );
            
        }
        
        else if ( dimension == 2 )
        {
            hexagonModelMatrix = new HexagonModel[MEDIUM_Y_DIMENSION][MEDIUM_X_DIMENSION];
            
            for ( int i = 0; i < hexagonModelMatrix.length; ++i )
                for ( int j = 0; j < hexagonModelMatrix[i].length; ++j )
                    hexagonModelMatrix[i][j] = new HexagonModel( -1, i, j );
            
        }
        
        else if ( dimension == 3 )
        {
            hexagonModelMatrix = new HexagonModel[BIG_Y_DIMENSION][BIG_X_DIMENSION];
            
            for ( int i = 0; i < hexagonModelMatrix.length; ++i )
                for ( int j = 0; j < hexagonModelMatrix[i].length; ++j )
                    hexagonModelMatrix[i][j] = new HexagonModel( -1, i, j );
            
        }
            
            
        Random rand = new Random();
        int randomNumber;
            
        for ( int i = 0; i < hexagonModelMatrix.length; ++i )
            for ( int j = 0; j < hexagonModelMatrix[i].length; ++j )
            {
                randomNumber = rand.nextInt(11);
                if ( randomNumber < 3 )
                {
                    hexagonModelMatrix[i][j].setLand(false);
                }
                
                if ( randomNumber == 10 )
                {
                    hexagonModelMatrix[i][j].setTileObject( new Building(Model.BuldingType.TREE, -1, null, i, j) );
                }
            }
        
        int[][] startPositionCoordinates = new int[6][2];
        
        startPositionCoordinates[0][0] = 1;
        startPositionCoordinates[0][1] = 1;
        startPositionCoordinates[1][0] = hexagonModelMatrix.length - 2;
        startPositionCoordinates[1][1] = hexagonModelMatrix[0].length - 2;
        startPositionCoordinates[2][0] = hexagonModelMatrix.length - 2;
        startPositionCoordinates[2][1] = 1;
        startPositionCoordinates[3][0] = 1;
        startPositionCoordinates[3][1] = hexagonModelMatrix[0].length - 2;
        startPositionCoordinates[4][0] = hexagonModelMatrix.length / 2;
        startPositionCoordinates[4][1] = 1;
        startPositionCoordinates[5][0] = hexagonModelMatrix.length / 2;
        startPositionCoordinates[5][1] = hexagonModelMatrix[0].length - 2;
        
        int index = 0;
        
        for( int i = 0; i < colorsArray.length; ++i )
        {
            if ( colorsArray[i] )
            {
                List<HexagonModel> listOfStartingTiles = new LinkedList<>();
                int coordX = startPositionCoordinates[index][0];
                int coordY = startPositionCoordinates[index][1];
                listOfStartingTiles.add( hexagonModelMatrix[coordX][coordY] );
                hexagonModelMatrix[coordX][coordY].setOwner(i);
                hexagonModelMatrix[coordX][coordY].setLand(true);
                VillageModel startVillage = new VillageModel( START_MONEY, startPositionCoordinates[index], listOfStartingTiles, i );
                changeOwnerAdjacentBoxes( hexagonModelMatrix[coordX][coordY] ,i, listOfStartingTiles, startVillage );
                hexagonModelMatrix[coordX][coordY].setTileObject(new Building( Model.BuldingType.CENTER_OF_VILLAGE, i, startVillage, coordX, coordY  ));
                hexagonModelMatrix[coordX][coordY].setVillageModel(startVillage);
                model.villageArray[i].add( startVillage );
                index += 1;
                
            }
        }

            
        boolean connected = findConnection( repr );
            
        if ( !connected )
        {
        
            int[][] weight = new int[hexagonModelMatrix.length][hexagonModelMatrix[0].length];
        
            for( int i = 0; i < weight.length; ++i )
                for ( int j = 0; j < weight[i].length; ++j )
                {
                    if ( hexagonModelMatrix[i][j].isLand() )
                        weight[i][j] = 0;
                    else
                        weight[i][j] = 1;
                }
                
            Dijkstra dijkstra = new Dijkstra(hexagonModelMatrix.length, hexagonModelMatrix[0].length, weight);
            connectNodes( repr, dijkstra);
                    
        }
        
        BFS.setHexagonModelMatrix(hexagonModelMatrix);
        
        for( int i = 0; i < hexagonModelMatrix.length; ++i )
            for (  int j = 0; j < hexagonModelMatrix[i].length; ++j )
            {
                int owner = hexagonModelMatrix[i][j].getOwner();
                if ( owner >= 0 )
                    findWeight( hexagonModelMatrix[i][j], owner);
            }
               
        return hexagonModelMatrix;
    }
    
    private static boolean findConnection( List<Integer> repr )
    {
        Queue<HexagonModel> queue = new LinkedList<>();
        
        colorMatrix = new Color[hexagonModelMatrix.length][hexagonModelMatrix[0].length];
        
        for ( int i = 0; i < colorMatrix.length; ++i )
            for ( int j = 0; j < colorMatrix[i].length; ++j )
                colorMatrix[i][j] = Color.WHITE;
        
        
        for ( int i = 0; queue.isEmpty() && i < hexagonModelMatrix.length ; ++i )
            for ( int j = 0; queue.isEmpty() && j < hexagonModelMatrix[i].length; ++j )
            {
                if ( hexagonModelMatrix[i][j].isLand()  )
                {
                    try
                    {
                        queue.add(hexagonModelMatrix[i][j]);
                        colorMatrix[i][j] = Color.GRAY;
                        repr.add( coord_to_id(i,j, hexagonModelMatrix[i].length));
                    }
                    catch ( IllegalStateException e )
                    {
                        System.out.println( "error during the bfs algorithm");
                        return false;
                    }
                }
            }
       
        bfs( repr, queue );
        return findRepresentatives( repr, queue );
       
    }
    
    
    private static void bfs( List<Integer> repr, Queue<HexagonModel> queue )
    {
        while ( queue.peek() != null )
        {
            HexagonModel hm = queue.remove();
            int row = hm.getRow();
            int col = hm.getCol();
            
            if ( (row - 1 >= 0) && colorMatrix[row - 1][col] == Color.WHITE && hexagonModelMatrix[row - 1][col].isLand() )
            {
                colorMatrix[row - 1][col] = Color.GRAY;
                queue.add(hexagonModelMatrix[row - 1][col]);
            }
            
            if ( ( col + 1 < colorMatrix[row].length) && colorMatrix[row][col + 1] == Color.WHITE && hexagonModelMatrix[row][col + 1].isLand())
            {
                colorMatrix[row][col + 1] = Color.GRAY;
                queue.add(hexagonModelMatrix[row][col + 1]); 
            }
            
            if ( ( row + 1 < colorMatrix.length) && colorMatrix[row + 1][col] == Color.WHITE && hexagonModelMatrix[row + 1][col].isLand())
            {
                colorMatrix[row + 1][col] = Color.GRAY;
                queue.add(hexagonModelMatrix[row + 1][col]); 
            }
            
            if ( ( col - 1 >= 0 ) && colorMatrix[row][col - 1] == Color.WHITE && hexagonModelMatrix[row][col - 1].isLand())
            {
                colorMatrix[row][col - 1] = Color.GRAY;
                queue.add(hexagonModelMatrix[row][col - 1]);
            }
            
            if ( col % 2 == 0 )
            {
                if ( ( row - 1 >= 0 && col - 1 >= 0 ) && colorMatrix[row - 1][col - 1] == Color.WHITE && hexagonModelMatrix[row - 1][col - 1].isLand())
                {
                    colorMatrix[row - 1][col - 1] = Color.GRAY;
                    queue.add(hexagonModelMatrix[row - 1][col - 1]);
                }
                
                if ( ( row - 1 >= 0 && col + 1 < colorMatrix[row].length ) && colorMatrix[row - 1][col + 1] == Color.WHITE && hexagonModelMatrix[row - 1][col + 1].isLand())
                {
                    colorMatrix[row - 1][col + 1] = Color.GRAY;
                    queue.add(hexagonModelMatrix[row - 1][col + 1]); 
                }
            }
            
            else
            {
                if ( ( row + 1 < colorMatrix.length && col + 1 < colorMatrix[row].length ) && colorMatrix[row + 1][col + 1] == Color.WHITE && hexagonModelMatrix[row + 1][col + 1].isLand())
                {
                    colorMatrix[row + 1][col + 1] = Color.GRAY;
                    queue.add(hexagonModelMatrix[row + 1][col + 1]); 
                }
                
                if ( ( row + 1 < colorMatrix.length && col - 1 >= 0 ) && colorMatrix[row + 1][col - 1] == Color.WHITE && hexagonModelMatrix[row + 1][col - 1].isLand())
                {
                    colorMatrix[row + 1][col - 1] = Color.GRAY;
                    queue.add(hexagonModelMatrix[row + 1][col - 1]); 
                } 
            }
            
            colorMatrix[row][col] = Color.BLACK;
        }
        
    }
    
    private static boolean findRepresentatives( List<Integer> repr, Queue<HexagonModel> queue )
    {
        boolean connected = true;
        
        for ( int i = 0; i < hexagonModelMatrix.length; ++i )
            for ( int j = 0; j < hexagonModelMatrix[i].length; ++j )
                if ( colorMatrix[i][j] == Color.WHITE && hexagonModelMatrix[i][j].isLand())
                {
                    System.out.println( i +" " + j);
                    queue.add( hexagonModelMatrix[i][j] );
                    repr.add( coord_to_id(i,j, hexagonModelMatrix[i].length));
                    bfs( repr, queue);
                    connected =  false;
                }
        return connected;
    }
    
    private static void connectNodes( List<Integer> repr, Dijkstra dijkstra )
    {
        int firstNode = repr.remove(0);
        int[] arrayFinalCoord = id_to_coord( firstNode, hexagonModelMatrix[0].length );
        
        Vdata[][] sp = dijkstra.makeDijkstra( arrayFinalCoord[0], arrayFinalCoord[1] );
        
        while ( !repr.isEmpty() )
        {
            int node = repr.remove(0);
            int[] arrayCurrentCoord = id_to_coord( node, hexagonModelMatrix[0].length );
            recorsiveFoundPath( arrayFinalCoord[0], arrayFinalCoord[1], arrayCurrentCoord[0], arrayCurrentCoord[1], sp );
        }
    }
    
    private static void recorsiveFoundPath( int finalCoordX, int finalCoordY, int currentCoordX, int currentCoordY, Vdata[][] sp )
    {
        if ( finalCoordX == currentCoordX && finalCoordY == currentCoordY );
        else 
        {
            hexagonModelMatrix[currentCoordX][currentCoordY].setLand(true);
            int newCoordX = sp[currentCoordX][currentCoordY].parent[0];
            int newCoordY = sp[currentCoordX][currentCoordY].parent[1];
            recorsiveFoundPath( finalCoordX, finalCoordY, newCoordX, newCoordY, sp );
        }
       
    }
    
    private static int coord_to_id(int i, int j, int cols){
        return i*cols+j;
    }

    private static int[] id_to_coord(int id, int cols){
        return new int[]{id/cols,id%cols};
    }
    
    private static void changeOwnerAdjacentBoxes( HexagonModel capitalBox, int owner, List<HexagonModel> listOfStartingTiles, VillageModel startVillage )
    {
        int row = capitalBox.getRow();
        int col = capitalBox.getCol();
        
 
        hexagonModelMatrix[row - 1][col].setOwner(owner);
        hexagonModelMatrix[row - 1][col].setLand(true);
        hexagonModelMatrix[row - 1][col].setVillageModel(startVillage);
        if ( hexagonModelMatrix[row - 1][col].getTileObject() != null )
            startVillage.changeMaintenanceCost( hexagonModelMatrix[row - 1][col].getTileObject().getMaintenanceCost() );
        listOfStartingTiles.add(hexagonModelMatrix[row - 1][col]);
        
        hexagonModelMatrix[row][col + 1].setOwner(owner);
        hexagonModelMatrix[row][col + 1].setLand(true);  
        hexagonModelMatrix[row][col + 1].setVillageModel(startVillage);
        if ( hexagonModelMatrix[row][col + 1].getTileObject() != null )
            startVillage.changeMaintenanceCost( hexagonModelMatrix[row][col + 1].getTileObject().getMaintenanceCost() );
        listOfStartingTiles.add(hexagonModelMatrix[row][col + 1]);
        
        hexagonModelMatrix[row + 1][col].setOwner(owner);
        hexagonModelMatrix[row + 1][col].setLand(true);  
        hexagonModelMatrix[row + 1][col].setVillageModel(startVillage);
        if ( hexagonModelMatrix[row + 1][col].getTileObject() != null )
            startVillage.changeMaintenanceCost( hexagonModelMatrix[row + 1][col].getTileObject().getMaintenanceCost() );
        listOfStartingTiles.add(hexagonModelMatrix[row + 1][col]);
        
        hexagonModelMatrix[row][col - 1].setOwner(owner);
        hexagonModelMatrix[row][col - 1].setLand(true); 
        hexagonModelMatrix[row][col - 1].setVillageModel(startVillage);
        if ( hexagonModelMatrix[row][col - 1].getTileObject() != null )
            startVillage.changeMaintenanceCost( hexagonModelMatrix[row][col - 1].getTileObject().getMaintenanceCost() );
        listOfStartingTiles.add(hexagonModelMatrix[row][col - 1]);
        
        if ( col % 2 == 0 )
        {
            hexagonModelMatrix[row - 1][col - 1].setOwner(owner);
            hexagonModelMatrix[row - 1][col - 1].setLand(true);  
            hexagonModelMatrix[row - 1][col - 1].setVillageModel(startVillage);
            if ( hexagonModelMatrix[row - 1][col - 1].getTileObject() != null )
                startVillage.changeMaintenanceCost( hexagonModelMatrix[row - 1][col - 1].getTileObject().getMaintenanceCost() );
            listOfStartingTiles.add(hexagonModelMatrix[row - 1][col - 1]);
            
            hexagonModelMatrix[row - 1][col + 1].setOwner(owner);
            hexagonModelMatrix[row - 1][col + 1].setLand(true); 
            hexagonModelMatrix[row - 1][col + 1].setVillageModel(startVillage);
            if ( hexagonModelMatrix[row - 1][col + 1].getTileObject() != null )
                startVillage.changeMaintenanceCost( hexagonModelMatrix[row - 1][col + 1].getTileObject().getMaintenanceCost() );
            listOfStartingTiles.add(hexagonModelMatrix[row - 1][col + 1]);
        }
            
        else
        {
            hexagonModelMatrix[row + 1][col + 1].setOwner(owner);
            hexagonModelMatrix[row + 1][col + 1].setLand(true); 
            hexagonModelMatrix[row + 1][col + 1].setVillageModel(startVillage);
            if ( hexagonModelMatrix[row + 1][col + 1].getTileObject() != null )
                startVillage.changeMaintenanceCost( hexagonModelMatrix[row + 1][col + 1].getTileObject().getMaintenanceCost() );
            listOfStartingTiles.add(hexagonModelMatrix[row + 1][col + 1]);
            
            hexagonModelMatrix[row + 1][col - 1].setOwner(owner); 
            hexagonModelMatrix[row + 1][col - 1].setLand(true); 
            hexagonModelMatrix[row + 1][col - 1].setVillageModel(startVillage);
            if ( hexagonModelMatrix[row + 1][col - 1].getTileObject() != null )
                startVillage.changeMaintenanceCost( hexagonModelMatrix[row + 1][col - 1].getTileObject().getMaintenanceCost() );
            listOfStartingTiles.add(hexagonModelMatrix[row + 1][col - 1]);
        }
        
    }
    
    private static void findWeight( HexagonModel hexagonModel, int owner )
    {
        int row = hexagonModel.getRow();
        int col = hexagonModel.getCol();
        
        int weight = 63;
        
        if ( col % 2 == 0 )
        {
            if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getOwner() == owner )
                weight -= 1;
            if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getOwner() == owner )
                weight -= 8;
            if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getOwner() == owner )
                weight -= 16;
            if ( col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row][col + 1].getOwner() == owner )
                weight -= 4;
            if ( row - 1 >= 0 && col - 1 >= 0 && hexagonModelMatrix[row - 1][col - 1].getOwner() == owner )
                weight -= 32;
            if ( row - 1 >= 0 && col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row - 1][col + 1].getOwner() == owner )
                weight -= 2;
        }
        
        else
        {
            if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getOwner() == owner )
                weight -= 1;
            if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getOwner() == owner )
                weight -= 8;
            if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getOwner() == owner )
                weight -= 32;
            if ( col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row][col + 1].getOwner() == owner )
                weight -= 2;
            if ( row + 1 < hexagonModelMatrix.length && col - 1 >= 0 && hexagonModelMatrix[row + 1][col - 1].getOwner() == owner )
                weight -= 16;
            if ( row + 1 < hexagonModelMatrix.length && col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row + 1][col + 1].getOwner() == owner )
                weight -= 4;
        }
        
        hexagonModel.setWeight(weight);         
    }
    
    
}