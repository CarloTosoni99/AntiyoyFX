/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author toson
 */
public class HexagonModel 
{   
    
    private int owner;
    private int weight = -1;
    private TileObjectModel tileObject;
    private boolean land = true;
    private VillageModel villageOfTheTile;
    
    private final int row;
    private final int col;
    
    
    public HexagonModel( int owner, int row, int col )
    {
        this.owner = owner;
        
        this.row = row;
        this.col = col;
    }
    
    public int getOwner()
    {
        return this.owner;
    }
    
    public void setOwner( int owner )
    {
        this.owner = owner;
    }
    
    public VillageModel getVillageModel()
    {
        return this.villageOfTheTile;
    }
    
    public void setVillageModel( VillageModel villageOfTheTile )
    {
        this.villageOfTheTile = villageOfTheTile;
    }
    
    public TileObjectModel getTileObject()
    {
        return this.tileObject;
    }
    
    public void setTileObject( TileObjectModel tom)
    {
        this.tileObject = tom;
    }
    
    public void setLand( boolean isLand )
    {
        this.land = isLand;
    }
    
    public boolean isLand()
    {
        return land;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public void setWeight( int weight )
    {   
        this.weight = weight;
    }
}

        
