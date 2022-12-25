/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.image.Image;
import view.PlayGameWindow;

/**
 *
 * @author toson
 */
public final class Model {
    
    public enum UnitType
    {
        PEASANT,
        SPEARMAN,
        BARON,
        KNIGHT
    };
    
    public enum BuldingType
    {
        CENTER_OF_VILLAGE,
        TREE,
        TOWER,
        MAGIC_TOWER,
        VILLAGE_HUT,
        GRAVE,
        NOTHING
    };            
   
    public int currentTurn = 0;
    public int currentPlayer;
    public int currentUnitShop = 0;
    public int currentBuildingShop = 0;
    
    public boolean canBuyUnit = false;
    public boolean canBuyBuilding = false;
    public boolean isFirstTurn = true;
    
    public VillageModel villageModelSelected;
    public Unit unitSelected;
    public Image[] imageArray;
    
    private static Model instance = null;
    
    public HexagonModel[][] hexagonModelMatrix;
    
    public int dimension = 0;
    
    public boolean[] colorsArray = new boolean[6];
    public List<VillageModel>[] villageArray = new List[6];
    public PlayGameWindow playGameWindow;
    
    private Model()
    {
        hexagonModelMatrix = null;
        
        for ( int i = 0; i < colorsArray.length; ++i )
            colorsArray[i] = false;
        
        for ( int i = 0; i < villageArray.length; ++i )
            villageArray[i] = new LinkedList<>();
    }
    
    public static Model getModel(){
        if(instance == null) instance = new Model();
        return instance;
    }
    
    public void createMatrix( )
    {
        this.hexagonModelMatrix = FactoryHexagonModelMatrix.createHexagonModelMatrix(dimension, colorsArray );        
    }
    
    public boolean findBorderOwner( int row, int col )
    {
        if ( (row - 1 >= 0) && hexagonModelMatrix[row - 1][col].getVillageModel() == villageModelSelected )
            return true;
        
        if ( ( col + 1 < hexagonModelMatrix[row].length ) && hexagonModelMatrix[row][col + 1].getVillageModel() == villageModelSelected )
            return true;
            
        if ( ( row + 1 < hexagonModelMatrix.length) && hexagonModelMatrix[row + 1][col].getVillageModel() == villageModelSelected )
            return true;
        
         if ( ( col - 1 >= 0 ) && hexagonModelMatrix[row][col - 1].getVillageModel() == villageModelSelected )
            return true;

        if ( col % 2 == 0 )
        {
            if ( ( row - 1 >= 0 && col - 1 >= 0 ) && hexagonModelMatrix[row - 1][col - 1].getVillageModel() == villageModelSelected )
                return true;
                
            if ( ( row - 1 >= 0 && col + 1 < hexagonModelMatrix[row].length ) && hexagonModelMatrix[row - 1][col + 1].getVillageModel() == villageModelSelected )
                return true;
        }
            
        else
        {
            if ( ( row + 1 < hexagonModelMatrix.length && col + 1 < hexagonModelMatrix[row].length ) && hexagonModelMatrix[row + 1][col + 1].getVillageModel() == villageModelSelected )
                return true;
                
            if ( ( row + 1 < hexagonModelMatrix.length && col - 1 >= 0 ) && hexagonModelMatrix[row + 1][col - 1].getVillageModel() == villageModelSelected )
                return true;
        }
        return false;
        
    }
    
    public void calculateNewWeights( int row, int col )
    {
        
        findWeight( row, col, hexagonModelMatrix[row][col].getOwner() );
        if ( col % 2 == 0 )
        {
            if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getOwner() >= 0 )
                findWeight( row - 1, col, hexagonModelMatrix[row - 1][col].getOwner() );
            if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getOwner() >= 0 )
                findWeight( row + 1, col, hexagonModelMatrix[row + 1][col].getOwner() );         
            if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getOwner() >= 0 )
                findWeight( row, col - 1, hexagonModelMatrix[row][col - 1].getOwner() );
            if ( col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row][col + 1].getOwner() >= 0 )
                findWeight( row, col + 1, hexagonModelMatrix[row][col + 1].getOwner() );
            if ( row - 1 >= 0 && col - 1 >= 0 && hexagonModelMatrix[row - 1][col - 1].getOwner() >= 0 )
                findWeight( row - 1, col - 1, hexagonModelMatrix[row - 1][col - 1].getOwner() );
            if ( row - 1 >= 0 && col + 1 < hexagonModelMatrix[0].length&& hexagonModelMatrix[row - 1][col + 1].getOwner() >= 0  )
                findWeight( row - 1, col + 1, hexagonModelMatrix[row - 1][col + 1].getOwner() );
        }
        
        else
        {
            if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getOwner() >= 0 )
                findWeight( row - 1, col, hexagonModelMatrix[row - 1][col].getOwner() );
            if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getOwner() >= 0 )
                findWeight( row + 1, col, hexagonModelMatrix[row + 1][col].getOwner() );
            if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getOwner() >= 0 )
                findWeight( row, col - 1, hexagonModelMatrix[row][col - 1].getOwner() );
            if ( col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row][col + 1].getOwner() >= 0 )
                findWeight ( row, col + 1, hexagonModelMatrix[row][col + 1].getOwner() );
            if ( row + 1 < hexagonModelMatrix.length && col - 1 >= 0 && hexagonModelMatrix[row + 1][col - 1].getOwner() >= 0 )
                findWeight( row + 1, col - 1, hexagonModelMatrix[row + 1][col - 1].getOwner() );
            if ( row + 1 < hexagonModelMatrix.length && col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row + 1][col + 1].getOwner() >= 0 )
                findWeight( row + 1, col + 1, hexagonModelMatrix[row + 1][col + 1].getOwner());
        } 
    }
    
    private void findWeight( int row, int col, int owner )
    {
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
        
        hexagonModelMatrix[row][col].setWeight(weight);
    }
    
    public int findMaxDefense( int row, int col )
    {
        int maxDefense = 0;
        int owner = hexagonModelMatrix[row][col].getOwner();
        
        if ( hexagonModelMatrix[row][col].getTileObject() != null && hexagonModelMatrix[row][col].getTileObject().getDefense() > maxDefense )
            maxDefense = hexagonModelMatrix[row][col].getTileObject().getDefense();
        
        if ( col % 2 == 0 )
        {
            if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getOwner() == owner && hexagonModelMatrix[row - 1][col].getTileObject() != null && hexagonModelMatrix[row - 1][col].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row - 1][col].getTileObject().getDefense();
            if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getOwner() == owner && hexagonModelMatrix[row + 1][col].getTileObject() != null && hexagonModelMatrix[row + 1][col].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row + 1][col].getTileObject().getDefense();        
            if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getOwner() == owner && hexagonModelMatrix[row][col - 1].getTileObject() != null && hexagonModelMatrix[row][col - 1].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row][col - 1].getTileObject().getDefense();
            if ( col + 1 < hexagonModelMatrix[row].length && hexagonModelMatrix[row][col + 1].getOwner() == owner && hexagonModelMatrix[row][col + 1].getTileObject() != null && hexagonModelMatrix[row][col + 1].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row][col + 1].getTileObject().getDefense();
            if ( row - 1 >= 0 && col - 1 >= 0 && hexagonModelMatrix[row - 1][col - 1].getOwner() == owner && hexagonModelMatrix[row - 1][col - 1].getTileObject() != null && hexagonModelMatrix[row - 1][col - 1].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row - 1][col - 1].getTileObject().getDefense();
            if ( row - 1 >= 0 && col + 1 < hexagonModelMatrix[row].length && hexagonModelMatrix[row - 1][col + 1].getOwner() == owner && hexagonModelMatrix[row - 1][col + 1].getTileObject() != null && hexagonModelMatrix[row - 1][col + 1].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row - 1][col + 1].getTileObject().getDefense();
        }
        
        else
        {
            if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getOwner() == owner && hexagonModelMatrix[row - 1][col].getTileObject() != null && hexagonModelMatrix[row - 1][col].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row - 1][col].getTileObject().getDefense();
            if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getOwner() == owner && hexagonModelMatrix[row + 1][col].getTileObject() != null && hexagonModelMatrix[row + 1][col].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row + 1][col].getTileObject().getDefense();        
            if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getOwner() == owner && hexagonModelMatrix[row][col - 1].getTileObject() != null && hexagonModelMatrix[row][col - 1].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row][col - 1].getTileObject().getDefense();
            if ( col + 1 < hexagonModelMatrix[row].length && hexagonModelMatrix[row][col + 1].getOwner() == owner && hexagonModelMatrix[row][col + 1].getTileObject() != null && hexagonModelMatrix[row][col + 1].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row][col + 1].getTileObject().getDefense();
            if ( row + 1 < hexagonModelMatrix.length && col - 1 >= 0 && hexagonModelMatrix[row + 1][col - 1].getOwner() == owner && hexagonModelMatrix[row + 1][col - 1].getTileObject() != null && hexagonModelMatrix[row + 1][col - 1].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row + 1][col - 1].getTileObject().getDefense();
            if ( row + 1 < hexagonModelMatrix.length && col + 1 < hexagonModelMatrix[row].length && hexagonModelMatrix[row + 1][col + 1].getOwner() == owner && hexagonModelMatrix[row + 1][col + 1].getTileObject() != null && hexagonModelMatrix[row + 1][col + 1].getTileObject().getDefense() > maxDefense )
                maxDefense = hexagonModelMatrix[row + 1][col + 1].getTileObject().getDefense();
        } 
        
        return maxDefense;
    }
    
    public boolean findOtherHuts( int row, int col )
    {
        if ( col % 2 == 0 )
        {
            if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getTileObject() != null && ( hexagonModelMatrix[row - 1][col].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row - 1][col].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE ) )
                return true;
            if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getTileObject() != null && ( hexagonModelMatrix[row + 1][col].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row + 1][col].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
            if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getTileObject() != null && ( hexagonModelMatrix[row][col - 1].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row][col - 1].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
            if ( col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row][col + 1].getTileObject() != null && ( hexagonModelMatrix[row][col + 1].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row][col + 1].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
            if ( row - 1 >= 0 && col - 1 >= 0 && hexagonModelMatrix[row - 1][col - 1].getTileObject() != null && ( hexagonModelMatrix[row - 1][col - 1].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row - 1][col - 1].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
            if ( row - 1 >= 0 && col + 1 < hexagonModelMatrix[0].length&& hexagonModelMatrix[row - 1][col + 1].getTileObject() != null && ( hexagonModelMatrix[row - 1][col + 1].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row - 1][col + 1].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
        }
        
        else
        {
            if ( row - 1 >= 0 && hexagonModelMatrix[row - 1][col].getTileObject() != null && ( hexagonModelMatrix[row - 1][col].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row - 1][col].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE ) )
                return true;
            if ( row + 1 < hexagonModelMatrix.length && hexagonModelMatrix[row + 1][col].getTileObject() != null && ( hexagonModelMatrix[row + 1][col].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row + 1][col].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
            if ( col - 1 >= 0 && hexagonModelMatrix[row][col - 1].getTileObject() != null && ( hexagonModelMatrix[row][col - 1].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row][col - 1].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
            if ( col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row][col + 1].getTileObject() != null && ( hexagonModelMatrix[row][col + 1].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row][col + 1].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
            if ( row + 1 < hexagonModelMatrix.length && col - 1 >= 0 && hexagonModelMatrix[row + 1][col - 1].getTileObject() != null && ( hexagonModelMatrix[row + 1][col - 1].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row + 1][col - 1].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
            if ( row + 1 < hexagonModelMatrix.length && col + 1 < hexagonModelMatrix[0].length && hexagonModelMatrix[row + 1][col + 1].getTileObject() != null && ( hexagonModelMatrix[row + 1][col + 1].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT || hexagonModelMatrix[row + 1][col + 1].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )  )
                return true;
        } 
        
        return false;
    }
}
