/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import model.Building;
import model.HexagonModel;
import model.Model;
import model.Unit;
import model.VillageModel;

/**
 *
 * @author toson
 */
public class BFS 
{
    private enum Color
    {
        WHITE,
        GRAY,
        BLACK
    };
    
    private static HexagonModel[][] hexagonModelMatrix;
    
    public static void setHexagonModelMatrix( HexagonModel[][] hexagonModelMatrix)
    {
        BFS.hexagonModelMatrix = hexagonModelMatrix;
    }
    
    public static boolean controllConnectionOfVillage ( List<HexagonModel> tiles , int[] coordCenterOfVillage )
    {
        Color[][] colorMatrix = new Color[hexagonModelMatrix.length][hexagonModelMatrix[0].length];
        
        for( int i = 0; i < hexagonModelMatrix.length; ++ i)
            for( int j = 0; j < hexagonModelMatrix[i].length; ++j )
                colorMatrix[i][j] = Color.WHITE;
        
        Queue<HexagonModel> queue = new LinkedList<>();
        queue.add( hexagonModelMatrix[coordCenterOfVillage[0]][coordCenterOfVillage[1]]);
        
        while ( queue.peek() != null )
        {
            HexagonModel hm = queue.remove();
            int row = hm.getRow();
            int col = hm.getCol();
            
            if ( (row - 1 >= 0) && colorMatrix[row - 1][col] == Color.WHITE && hexagonModelMatrix[row - 1][col].isLand() && tiles.contains(hm) )
            {
                colorMatrix[row - 1][col] = Color.GRAY;
                queue.add(hexagonModelMatrix[row - 1][col]);
            }
            
            if ( ( col + 1 < colorMatrix[row].length) && colorMatrix[row][col + 1] == Color.WHITE && hexagonModelMatrix[row][col + 1].isLand() && tiles.contains(hm))
            {
                colorMatrix[row][col + 1] = Color.GRAY;
                queue.add(hexagonModelMatrix[row][col + 1]); 
            }
            
            if ( ( row + 1 < colorMatrix.length) && colorMatrix[row + 1][col] == Color.WHITE && hexagonModelMatrix[row + 1][col].isLand() && tiles.contains(hm))
            {
                colorMatrix[row + 1][col] = Color.GRAY;
                queue.add(hexagonModelMatrix[row + 1][col]); 
            }
            
            if ( ( col - 1 >= 0 ) && colorMatrix[row][col - 1] == Color.WHITE && hexagonModelMatrix[row][col - 1].isLand() && tiles.contains(hm))
            {
                colorMatrix[row][col - 1] = Color.GRAY;
                queue.add(hexagonModelMatrix[row][col - 1]);
            }
            
            if ( col % 2 == 0 )
            {
                if ( ( row - 1 >= 0 && col - 1 >= 0 ) && colorMatrix[row - 1][col - 1] == Color.WHITE && hexagonModelMatrix[row - 1][col - 1].isLand() && tiles.contains(hm))
                {
                    colorMatrix[row - 1][col - 1] = Color.GRAY;
                    queue.add(hexagonModelMatrix[row - 1][col - 1]);
                }
                
                if ( ( row - 1 >= 0 && col + 1 < colorMatrix[row].length ) && colorMatrix[row - 1][col + 1] == Color.WHITE && hexagonModelMatrix[row - 1][col + 1].isLand() && tiles.contains(hm))
                {
                    colorMatrix[row - 1][col + 1] = Color.GRAY;
                    queue.add(hexagonModelMatrix[row - 1][col + 1]); 
                }
            }
            
            else
            {
                if ( ( row + 1 < colorMatrix.length && col + 1 < colorMatrix[row].length ) && colorMatrix[row + 1][col + 1] == Color.WHITE && hexagonModelMatrix[row + 1][col + 1].isLand() && tiles.contains(hm))
                {
                    colorMatrix[row + 1][col + 1] = Color.GRAY;
                    queue.add(hexagonModelMatrix[row + 1][col + 1]); 
                }
                
                if ( ( row + 1 < colorMatrix.length && col - 1 >= 0 ) && colorMatrix[row + 1][col - 1] == Color.WHITE && hexagonModelMatrix[row + 1][col - 1].isLand() && tiles.contains(hm))
                {
                    colorMatrix[row + 1][col - 1] = Color.GRAY;
                    queue.add(hexagonModelMatrix[row + 1][col - 1]); 
                } 
            }
            
            colorMatrix[row][col] = Color.BLACK;
        }
        
        boolean connected = true;
        

        
        for( HexagonModel hm : tiles )
        {
            int row = hm.getRow();
            int col = hm.getCol();
            
            if ( colorMatrix[row][col] == Color.WHITE )
                connected = false;
        }
        
        if ( !connected )
        {
            createNewVillage( colorMatrix, tiles );
        }
        
        return connected;
    }
    
    private static void createNewVillage( Color[][] colorMatrix, List<HexagonModel> tiles )
    {
        List<HexagonModel> newListOfTiles = new LinkedList<>();
        Model model = Model.getModel();
        VillageModel oldVillageModel = tiles.get(0).getVillageModel();
        oldVillageModel.resetAddCostVillageHut();
        
        List<HexagonModel> toBeRemoved = new ArrayList<>();
        for(HexagonModel hm : tiles){
            int row = hm.getRow();
            int col = hm.getCol();
            if (colorMatrix[row][col] == Color.WHITE) {
                toBeRemoved.add(hm);
                newListOfTiles.add( hm );
                if ( hm.getTileObject() != null )
                {
                    oldVillageModel.changeMaintenanceCost( -1* hm.getTileObject().getMaintenanceCost() );

                }
            }
            else 
                if ( hm.getTileObject()!= null && hm.getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT )
                {
                    oldVillageModel.increaseAddCostVillageHut();
                }
        }
        
        tiles.removeAll(toBeRemoved);
        
        if ( tiles.size() == 1 )
        {
            model.villageArray[tiles.get(0).getOwner()].remove(tiles.get(0).getVillageModel());
            tiles.get(0).setVillageModel(null);
            if ( tiles.get(0).getTileObject() != null )
            {
                tiles.get(0).setTileObject( new Building ( Model.BuldingType.TREE, tiles.get(0).getOwner(), null, tiles.get(0).getRow(), tiles.get(0).getCol()));
                model.playGameWindow.changeObjectViewTree(tiles.get(0).getRow(), tiles.get(0).getCol() );
            }
        }
            
        if ( newListOfTiles.size() == 1 )
        {
            VillageModel vm = newListOfTiles.get(0).getVillageModel();
            if ( newListOfTiles.get(0).getTileObject() != null )
            {
                newListOfTiles.get(0).setVillageModel( null );
                newListOfTiles.get(0).setTileObject( new Building( Model.BuldingType.TREE, newListOfTiles.get(0).getOwner(), vm, newListOfTiles.get(0).getRow(),newListOfTiles.get(0).getCol()));
                model.playGameWindow.changeObjectViewTree( newListOfTiles.get(0).getRow(), newListOfTiles.get(0).getCol() );
            }
            vm.countTiles();
        }
        
        else
        {
            Random rand = new Random();
            
            int tileIndex = rand.nextInt( newListOfTiles.size() );
            int startPosition = tileIndex;
            boolean findedRightTile = false;
            boolean lapCompleted = false;
            int newListLength = newListOfTiles.size();
            
            int[] coordCenterOfVillage = new int[2];
            
            VillageModel newVillageModel = new VillageModel( 0, null, newListOfTiles, newListOfTiles.get(0).getOwner() );
            model.villageArray[newVillageModel.getOwner()].add( newVillageModel );
            
            while ( !findedRightTile )
            {
                if ( newListOfTiles.get(tileIndex).getTileObject() == null || lapCompleted )
                {
                    
                    findedRightTile = true;
                    coordCenterOfVillage[0] = newListOfTiles.get(tileIndex).getRow();
                    coordCenterOfVillage[1] = newListOfTiles.get(tileIndex).getCol();
                       
                    newListOfTiles.get(tileIndex).setTileObject( new Building( Model.BuldingType.CENTER_OF_VILLAGE, newListOfTiles.get(0).getOwner(), newVillageModel , coordCenterOfVillage[0], coordCenterOfVillage[1] ));
                    model.playGameWindow.changeObjectViewCenterOfVillage( coordCenterOfVillage[0], coordCenterOfVillage[1] );
                    findedRightTile = true;
                    newVillageModel.setCoordCenterOfVillage(coordCenterOfVillage);
                        
                }
                
                tileIndex = ( tileIndex + 1) % newListLength;
                lapCompleted = tileIndex == startPosition;
            }
            
            newListOfTiles.stream()
                    .forEach(e -> e.setVillageModel(newVillageModel));
            
            for ( HexagonModel hm : newListOfTiles )
            {
                if ( hm.getTileObject() != null )
                {
                    newVillageModel.changeMaintenanceCost( hm.getTileObject().getMaintenanceCost() );
                    if ( hm.getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT )
                        newVillageModel.increaseAddCostVillageHut();
                }
            }
            
            newVillageModel.countTiles();

        }

    }
    
    public static List<VillageModel> findVillage( int row, int col, int owner, VillageModel vm )
    {
        List<VillageModel> listOfNewVillages = new LinkedList<>();
        
        if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getOwner() == owner && !vm.equals( hexagonModelMatrix[row - 1][col].getVillageModel()))
        {
            if ( hexagonModelMatrix[row - 1][col].getVillageModel() == null )
            {
                hexagonModelMatrix[row  - 1][col].setVillageModel(vm);
                vm.countTiles();
                if ( hexagonModelMatrix[row - 1][col].getTileObject() != null )
                    vm.changeMaintenanceCost( hexagonModelMatrix[row - 1][col].getTileObject().getMaintenanceCost() );
            }
            else
                if ( !listOfNewVillages.contains(hexagonModelMatrix[row - 1][col].getVillageModel()) )
                    listOfNewVillages.add(hexagonModelMatrix[row - 1][col].getVillageModel() );     
        }
        
        if ( col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row][col + 1].getOwner() == owner && !vm.equals( hexagonModelMatrix[row][col + 1].getVillageModel()) )
        {
            if ( hexagonModelMatrix[row][col + 1].getVillageModel() == null )
            {
                hexagonModelMatrix[row][col + 1].setVillageModel(vm);
                vm.countTiles();
                if ( hexagonModelMatrix[row][col + 1].getTileObject() != null )
                    vm.changeMaintenanceCost( hexagonModelMatrix[row][col + 1].getTileObject().getMaintenanceCost() );
            }
            else
                if ( !listOfNewVillages.contains(hexagonModelMatrix[row][col + 1].getVillageModel()) )
                    listOfNewVillages.add(hexagonModelMatrix[row][col + 1].getVillageModel() );  
        }
        
        if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getOwner() == owner && !vm.equals( hexagonModelMatrix[row + 1][col].getVillageModel()) )
        {
            if ( hexagonModelMatrix[row + 1][col].getVillageModel() == null )
            {
                hexagonModelMatrix[row + 1][col].setVillageModel(vm);
                vm.countTiles();
                if ( hexagonModelMatrix[row + 1][col].getTileObject() != null )
                    vm.changeMaintenanceCost( hexagonModelMatrix[row + 1][col].getTileObject().getMaintenanceCost() );
            }
            else
                if ( !listOfNewVillages.contains(hexagonModelMatrix[row + 1][col].getVillageModel()) )
                    listOfNewVillages.add(hexagonModelMatrix[row + 1][col].getVillageModel() );      
        }
        
        if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getOwner() == owner && !vm.equals( hexagonModelMatrix[row][col - 1].getVillageModel()) )
        {
            if ( hexagonModelMatrix[row][col - 1].getVillageModel() == null )
            {
                hexagonModelMatrix[row][col - 1].setVillageModel(vm);
                vm.countTiles();
                if ( hexagonModelMatrix[row][col - 1].getTileObject() != null )
                    vm.changeMaintenanceCost( hexagonModelMatrix[row][col - 1].getTileObject().getMaintenanceCost() );
            }
            else
                if ( !listOfNewVillages.contains(hexagonModelMatrix[row][col - 1].getVillageModel()) )
                    listOfNewVillages.add(hexagonModelMatrix[row][col - 1].getVillageModel() );     
        }
        
        if ( col % 2 == 0 )
        {
            if ( col - 1 >= 0 && row - 1 >= 0 && hexagonModelMatrix[row - 1][col - 1].getOwner() == owner && !vm.equals( hexagonModelMatrix[row - 1][col - 1].getVillageModel()) )
            {
                if ( hexagonModelMatrix[row - 1][col - 1].getVillageModel() == null )
                {
                    hexagonModelMatrix[row - 1][col - 1].setVillageModel(vm);
                    vm.countTiles();
                    if ( hexagonModelMatrix[row - 1][col - 1].getTileObject() != null )
                        vm.changeMaintenanceCost( hexagonModelMatrix[row - 1][col - 1].getTileObject().getMaintenanceCost() );
                }
                else
                if ( !listOfNewVillages.contains(hexagonModelMatrix[row - 1][col - 1].getVillageModel()) )
                    listOfNewVillages.add(hexagonModelMatrix[row - 1][col - 1].getVillageModel() );     
            }  
            
            if ( row - 1 >= 0 && col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row - 1][col + 1].getOwner() == owner && !vm.equals( hexagonModelMatrix[row - 1][col + 1].getVillageModel()) )
            {
                if ( hexagonModelMatrix[row - 1][col + 1].getVillageModel() == null )
                {
                    hexagonModelMatrix[row - 1][col + 1].setVillageModel(vm);
                    vm.countTiles();
                    if ( hexagonModelMatrix[row - 1][col + 1].getTileObject() != null )
                        vm.changeMaintenanceCost( hexagonModelMatrix[row - 1][col + 1].getTileObject().getMaintenanceCost() );
                }
                else
                if ( !listOfNewVillages.contains(hexagonModelMatrix[row - 1][col + 1].getVillageModel()) )
                    listOfNewVillages.add(hexagonModelMatrix[row - 1][col + 1].getVillageModel() );          
            } 
        }
            
        else
        {
            if ( row + 1 < hexagonModelMatrix.length && col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row + 1][col + 1].getOwner() == owner && !vm.equals( hexagonModelMatrix[row + 1][col + 1].getVillageModel()) )
            {
                if ( hexagonModelMatrix[row + 1][col + 1].getVillageModel() == null )
                {
                    hexagonModelMatrix[row + 1][col + 1].setVillageModel(vm);
                    vm.countTiles();
                    if ( hexagonModelMatrix[row + 1][col + 1].getTileObject() != null )
                        vm.changeMaintenanceCost( hexagonModelMatrix[row + 1][col + 1].getTileObject().getMaintenanceCost() );
                }
                else
                if ( !listOfNewVillages.contains(hexagonModelMatrix[row + 1][col + 1].getVillageModel()) )
                    listOfNewVillages.add(hexagonModelMatrix[row + 1][col + 1].getVillageModel() );     
            }
            
            if ( row + 1 < hexagonModelMatrix.length && col - 1 >= 0 && hexagonModelMatrix[row + 1][col - 1].getOwner() == owner && !vm.equals( hexagonModelMatrix[row + 1][col - 1].getVillageModel()) )
            {
                if ( hexagonModelMatrix[row + 1][col - 1].getVillageModel() == null )
                {
                    hexagonModelMatrix[row + 1][col - 1].setVillageModel(vm);
                    vm.countTiles();
                    if ( hexagonModelMatrix[row + 1][col - 1].getTileObject() != null )
                        vm.changeMaintenanceCost( hexagonModelMatrix[row + 1][col - 1].getTileObject().getMaintenanceCost() );
                }
                else
                if ( !listOfNewVillages.contains(hexagonModelMatrix[row + 1][col - 1].getVillageModel()) )
                    listOfNewVillages.add(hexagonModelMatrix[row + 1][col - 1].getVillageModel() );     
            }  
        }
        
        return listOfNewVillages;
    }
    
    public static boolean tryToMoveUnit( int newXPosition, int newYPosition, int startXPosition, int startYPosition, int owner, int speed, Unit unitToMove )
    {
        int tilesMoved = 0;
        
        Queue<HexagonModel> queue = new LinkedList<>();
        Model model = Model.getModel();
        
        if( BFS.hexagonModelMatrix == null )
        {
            BFS.hexagonModelMatrix = model.hexagonModelMatrix;
        }

        queue.add( hexagonModelMatrix[startXPosition][startYPosition]);        
        Color[][] colorMatrix = new Color[hexagonModelMatrix.length][hexagonModelMatrix[0].length];
        int[][] weight = new int[hexagonModelMatrix.length][hexagonModelMatrix[0].length];
        
        for( int i = 0; i < hexagonModelMatrix.length; ++i )
            for( int j = 0; j < hexagonModelMatrix[i].length; ++j )
                weight[i][j] = Integer.MAX_VALUE;
        
        for ( int i = 0; i < hexagonModelMatrix.length; ++i )
            for ( int j = 0; j < hexagonModelMatrix[i].length; ++j )
                colorMatrix[i][j] = Color.WHITE;
        
        weight[startXPosition][startYPosition] = 0;
        colorMatrix[startXPosition][startYPosition] = Color.GRAY;
        
        boolean moved = false;
        
        while ( !queue.isEmpty() )
        {
            HexagonModel hm = queue.remove();
            int row = hm.getRow();
            int col = hm.getCol();
            
            if ( (row - 1 >= 0) && colorMatrix[row - 1][col] == Color.WHITE && hexagonModelMatrix[row - 1][col].isLand() && weight[row][col] < speed )
            {
                colorMatrix[row - 1][col] = Color.GRAY;
                weight[row - 1][col] = weight[row][col] + 1;
                if ( owner != hexagonModelMatrix[row - 1][col].getOwner() )
                    weight[row - 1][col] = speed;
                if ( row - 1 == newXPosition && col  == newYPosition && (( hexagonModelMatrix[row - 1][col].getTileObject() == null || hexagonModelMatrix[row - 1][col].getTileObject().isEliminable() || ( unitToMove.getOwner() == hexagonModelMatrix[row - 1][col].getOwner() && hexagonModelMatrix[row - 1][col].getTileObject().mergeUnits(unitToMove)  )) || ( unitToMove.getStrength() > model.findMaxDefense(row - 1, col) && hexagonModelMatrix[row - 1][col].getOwner() != unitToMove.getOwner()))    )
                {
                    if ( hexagonModelMatrix[row - 1][col].getTileObject() != null && hexagonModelMatrix[row - 1][col].getTileObject().isEliminable() )
                        hexagonModelMatrix[row - 1][col].setTileObject(null);
                    moved = true;
                }
                queue.add( hexagonModelMatrix[row - 1][col]);
            }
            
            if ( ( col + 1 < colorMatrix[row].length) && colorMatrix[row][col + 1] == Color.WHITE && hexagonModelMatrix[row][col + 1].isLand() && weight[row][col] < speed )
            {
                colorMatrix[row][col + 1] = Color.GRAY;
                weight[row][col + 1] = weight[row][col] + 1;
                if ( owner != hexagonModelMatrix[row][col + 1].getOwner() )
                    weight[row][col + 1] = speed;
                if ( row  == newXPosition && col + 1 == newYPosition && (( hexagonModelMatrix[row][col + 1].getTileObject() == null || hexagonModelMatrix[row][col + 1].getTileObject().isEliminable() || ( unitToMove.getOwner() == hexagonModelMatrix[row][col + 1].getOwner() && hexagonModelMatrix[row][col + 1].getTileObject().mergeUnits(unitToMove) )) || ( unitToMove.getStrength() > model.findMaxDefense(row, col + 1) && hexagonModelMatrix[row][col + 1].getOwner() != unitToMove.getOwner()))  )
                {
                    if ( hexagonModelMatrix[row][col + 1].getTileObject() != null && hexagonModelMatrix[row][col + 1].getTileObject().isEliminable() )
                        hexagonModelMatrix[row][col + 1].setTileObject(null);
                    moved = true;
                }
                queue.add( hexagonModelMatrix[row][col + 1]);
            }
            
            if ( ( row + 1 < colorMatrix.length) && colorMatrix[row + 1][col] == Color.WHITE && hexagonModelMatrix[row + 1][col].isLand() && weight[row][col] < speed)
            {
                colorMatrix[row + 1][col] = Color.GRAY;
                weight[row + 1][col] = weight[row][col] + 1;
                if ( owner != hexagonModelMatrix[row + 1][col].getOwner() )
                    weight[row + 1][col] = speed;
                if ( row + 1 == newXPosition && col  == newYPosition && (( hexagonModelMatrix[row + 1][col].getTileObject() == null || hexagonModelMatrix[row + 1][col].getTileObject().isEliminable() || ( unitToMove.getOwner() == hexagonModelMatrix[row + 1][col].getOwner() && hexagonModelMatrix[row + 1][col].getTileObject().mergeUnits(unitToMove) ) ) || ( unitToMove.getStrength() > model.findMaxDefense(row + 1, col) && hexagonModelMatrix[row + 1][col].getOwner() != unitToMove.getOwner())) )
                {
                    if ( hexagonModelMatrix[row + 1][col].getTileObject() != null && hexagonModelMatrix[row + 1][col].getTileObject().isEliminable() )
                        hexagonModelMatrix[row + 1][col].setTileObject(null);
                    moved = true;
                }
                queue.add( hexagonModelMatrix[row + 1][col]);
            }
            
            if ( ( col - 1 >= 0 ) && colorMatrix[row][col - 1] == Color.WHITE && hexagonModelMatrix[row][col - 1].isLand() && weight[row][col] < speed )
            {
                colorMatrix[row][col - 1] = Color.GRAY;
                weight[row][col - 1] = weight[row][col] + 1;
                if ( owner != hexagonModelMatrix[row][col - 1].getOwner() )
                    weight[row][col - 1] = speed;
                if ( row == newXPosition && col - 1 == newYPosition && (( hexagonModelMatrix[row][col - 1].getTileObject() == null || hexagonModelMatrix[row][col - 1].getTileObject().isEliminable() || ( unitToMove.getOwner() == hexagonModelMatrix[row][col - 1].getOwner() && hexagonModelMatrix[row][col - 1].getTileObject().mergeUnits(unitToMove) ) ) || ( unitToMove.getStrength() > model.findMaxDefense(row, col - 1) && hexagonModelMatrix[row][col - 1].getOwner() != unitToMove.getOwner()))  )
                {
                    if ( hexagonModelMatrix[row][col - 1].getTileObject() != null && hexagonModelMatrix[row][col - 1].getTileObject().isEliminable() )
                        hexagonModelMatrix[row][col - 1].setTileObject(null);
                    moved = true;
                }
                queue.add( hexagonModelMatrix[row][col - 1]);
            }
            
            if ( col % 2 == 0 )
            {
                if ( ( row - 1 >= 0 && col - 1 >= 0 ) && colorMatrix[row - 1][col - 1] == Color.WHITE && hexagonModelMatrix[row - 1][col - 1].isLand() && weight[row][col] < speed )
                {
                    colorMatrix[row - 1][col - 1] = Color.GRAY;
                    weight[row - 1][col - 1] = weight[row][col] + 1;
                    if ( owner != hexagonModelMatrix[row - 1][col - 1].getOwner() )
                        weight[row - 1][col - 1] = speed;
                    if ( row - 1 == newXPosition && col - 1 == newYPosition && (( hexagonModelMatrix[row - 1][col - 1].getTileObject() == null || hexagonModelMatrix[row - 1][col - 1].getTileObject().isEliminable() || ( unitToMove.getOwner() == hexagonModelMatrix[row - 1][col - 1].getOwner() && hexagonModelMatrix[row - 1][col - 1].getTileObject().mergeUnits(unitToMove) )  ) || ( unitToMove.getStrength() > model.findMaxDefense(row - 1, col - 1 ) && hexagonModelMatrix[row - 1][col - 1].getOwner() != unitToMove.getOwner())) )
                    {
                        if ( hexagonModelMatrix[row - 1][col - 1].getTileObject() != null && hexagonModelMatrix[row - 1][col - 1].getTileObject().isEliminable() )
                            hexagonModelMatrix[row - 1][col - 1].setTileObject(null);
                        moved = true;
                    }
                    queue.add( hexagonModelMatrix[row - 1][col - 1]);
                }
                
                if ( ( row - 1 >= 0 && col + 1 < colorMatrix[row].length ) && colorMatrix[row - 1][col + 1] == Color.WHITE && hexagonModelMatrix[row - 1][col + 1].isLand() && weight[row][col] < speed )
                {
                    colorMatrix[row - 1][col + 1] = Color.GRAY;
                    weight[row - 1][col + 1] = weight[row][col] + 1;
                    if ( owner != hexagonModelMatrix[row - 1][col + 1].getOwner() )
                        weight[row - 1][col + 1] = speed;
                    if ( row - 1 == newXPosition && col + 1 == newYPosition && (( hexagonModelMatrix[row - 1][col + 1].getTileObject() == null || hexagonModelMatrix[row - 1][col + 1].getTileObject().isEliminable() || ( unitToMove.getOwner() == hexagonModelMatrix[row - 1][col + 1].getOwner() && hexagonModelMatrix[row - 1][col + 1].getTileObject().mergeUnits(unitToMove) ) ) || ( unitToMove.getStrength() > model.findMaxDefense(row - 1, col + 1) && hexagonModelMatrix[row - 1][col + 1].getOwner() != unitToMove.getOwner())) )
                    {
                        if ( hexagonModelMatrix[row - 1][col + 1].getTileObject() != null && hexagonModelMatrix[row - 1][col + 1].getTileObject().isEliminable() )
                            hexagonModelMatrix[row - 1][col + 1].setTileObject(null);
                        moved = true;
                    }
                    queue.add( hexagonModelMatrix[row - 1][col + 1]);
                }
            }
            
            else
            {
                if ( ( row + 1 < colorMatrix.length && col + 1 < colorMatrix[row].length ) && colorMatrix[row + 1][col + 1] == Color.WHITE && hexagonModelMatrix[row + 1][col + 1].isLand() && weight[row][col] < speed )
                {
                    queue.add( hexagonModelMatrix[row + 1][col + 1]);
                    colorMatrix[row + 1][col + 1] = Color.GRAY;
                    weight[row + 1][col + 1] = weight[row][col] + 1;
                    if ( owner != hexagonModelMatrix[row + 1][col + 1].getOwner() )
                        weight[row + 1][col + 1] = speed;
                    if ( row + 1 == newXPosition && col + 1 == newYPosition && (( hexagonModelMatrix[row + 1][col + 1].getTileObject() == null || hexagonModelMatrix[row + 1][col + 1].getTileObject().isEliminable() || ( unitToMove.getOwner() == hexagonModelMatrix[row + 1][col + 1].getOwner() && hexagonModelMatrix[row + 1][col + 1].getTileObject().mergeUnits(unitToMove)  ) ) || ( unitToMove.getStrength() > model.findMaxDefense(row + 1, col + 1) && hexagonModelMatrix[row + 1][col + 1].getOwner() != unitToMove.getOwner())) )
                    {
                        if ( hexagonModelMatrix[row + 1][col + 1].getTileObject() != null &&  hexagonModelMatrix[row + 1][col + 1].getTileObject().isEliminable() )
                            hexagonModelMatrix[row + 1][col + 1].setTileObject(null);
                        moved = true;
                    }
                }
                
                if ( ( row + 1 < colorMatrix.length && col - 1 >= 0 ) && colorMatrix[row + 1][col - 1] == Color.WHITE && hexagonModelMatrix[row + 1][col - 1].isLand() && weight[row][col] < speed )
                {
                    colorMatrix[row + 1][col - 1] = Color.GRAY;
                    weight[row + 1][col - 1] = weight[row][col] + 1;
                    if ( owner != hexagonModelMatrix[row + 1][col - 1].getOwner() )
                        weight[row + 1][col - 1] = speed;
                    if ( row + 1 == newXPosition && col - 1 == newYPosition && (( hexagonModelMatrix[row + 1][col - 1].getTileObject() == null || hexagonModelMatrix[row + 1][col - 1].getTileObject().isEliminable() || ( unitToMove.getOwner() == hexagonModelMatrix[row + 1][col - 1].getOwner() && hexagonModelMatrix[row + 1][col - 1].getTileObject().mergeUnits(unitToMove) ) )|| ( unitToMove.getStrength() > model.findMaxDefense(row + 1, col - 1) && hexagonModelMatrix[row + 1][col - 1].getOwner() != unitToMove.getOwner()))  )
                    {
                        if ( hexagonModelMatrix[row + 1][col - 1].getTileObject() != null && hexagonModelMatrix[row + 1][col - 1].getTileObject().isEliminable() )
                            hexagonModelMatrix[row + 1][col - 1].setTileObject(null);
                        moved = true;
                    }
                    queue.add( hexagonModelMatrix[row + 1][col - 1]);
                } 
            }
            
            colorMatrix[row][col] = Color.BLACK;
        } 
        return moved;
    }
}
