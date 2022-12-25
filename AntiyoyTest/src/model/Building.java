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
public class Building extends TileObjectModel
{
    private int xPosition; 
    private int yPosition;
    
    private int defense;
    private int maintenance = 0;
    
    private int owner;
    private VillageModel belongingVillage;
    
    private boolean constructible = false;
    private boolean eliminable = false;
    
    private Model.BuldingType buldingType;
    
    private static final int GRAVE_DEFENSE = 0;
    private static final int TREE_DEFENSE = 0;
    private static final int VILLAGE_HUT_DEFENSE = 0;
    private static final int CENTER_OF_VILLAGE_DEFENSE = 1;
    private static final int TOWER_DEFENSE = 2;
    private static final int MAGIC_TOWER_DEFENSE = 3;
    
    private static final int GRAVE_MAINTENANCE = 0;
    private static final int TREE_MAINTENANCE = 1;
    private static final int VILLAGE_HUT_MAINTENANCE = -3;
    private static final int CENTER_OF_VILLAGE_MAINTENANCE = 0;
    private static final int TOWER_MAINTENANCE = 1;
    private static final int MAGIC_TOWER_MAINTENANCE = 6;
    
    private static final int VILLAGE_HUT_COST = 12;
    private static final int TOWER_COST = 15;
    private static final int MAGIC_TOWER_COST = 35;
    
    public Building ( Model.BuldingType buldingType, int owner, VillageModel belogingVillage, int xPosition, int yPosition )
    {
        Model model = Model.getModel();
        
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        
        this.owner = owner;
        this.belongingVillage = belogingVillage;
        this.buldingType = buldingType;
        
        if( this.buldingType == Model.BuldingType.GRAVE || this.buldingType == Model.BuldingType.TREE )
            eliminable = true;
        
        if ( this.buldingType == Model.BuldingType.MAGIC_TOWER || this.buldingType == Model.BuldingType.TOWER || this.buldingType == Model.BuldingType.VILLAGE_HUT )
            constructible = true;
        
        if ( this.buldingType == Model.BuldingType.CENTER_OF_VILLAGE )
        {
            defense = CENTER_OF_VILLAGE_DEFENSE;
            maintenance = CENTER_OF_VILLAGE_MAINTENANCE;
        }
        else if ( this.buldingType == Model.BuldingType.GRAVE )
        {
            defense = GRAVE_DEFENSE;
            maintenance = GRAVE_MAINTENANCE;         
        }
        else if ( this.buldingType == Model.BuldingType.TOWER )
        {
            defense = TOWER_DEFENSE;
            maintenance = TOWER_MAINTENANCE;
        }
        else if ( this.buldingType == Model.BuldingType.MAGIC_TOWER )
        {
            defense = MAGIC_TOWER_DEFENSE;
            maintenance = MAGIC_TOWER_MAINTENANCE;
        }
        else if ( this.buldingType == Model.BuldingType.VILLAGE_HUT )
        {
            defense = VILLAGE_HUT_DEFENSE;
            maintenance = VILLAGE_HUT_MAINTENANCE;
        }
        else if ( this.buldingType == Model.BuldingType.TREE )
        {
            defense = TREE_DEFENSE;
            maintenance = TREE_MAINTENANCE;
        }
        
        
    }
    
    public int getDefense()
    {
        return this.defense;
    }
    
    public Model.BuldingType getBuldingType()
    {
        return this.buldingType;
    }
    
    public int getMaintenanceCost()
    {
        return this.maintenance;
    }
    
    public boolean getConstructible()
    {
        return this.constructible;
    }
    
    public boolean isEliminable()
    {
        return this.eliminable;
    }
    
    public Model.UnitType getUnitType()
    {
        return null;
    }
    
    public boolean mergeUnits( Unit unitToMerge )
    {
        return false;
    }
    
    public Unit getUnit()
    {
        return null;
    }
    
    public static int calculateBuildingCost( int buildingValue )
    {
        if ( buildingValue == 0 )
            return VILLAGE_HUT_COST;
        else if ( buildingValue == 1 )
            return TOWER_COST;
        else if ( buildingValue == 2 )
            return MAGIC_TOWER_COST;
        else 
            return -1;
    }
    
    public boolean isMoved()
    {
        return true;
    }
}

