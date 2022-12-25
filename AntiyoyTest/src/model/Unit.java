/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.BFS;

/**
 *
 * @author toson
 */
public class Unit extends TileObjectModel
{
    private boolean eliminable = false;
    private Model.UnitType unitType;
    
    private int xPosition;
    private int yPosition;
    
    private int strength;
    private int defense;
    
    private int recruitmentCost;
    private int maintenanceCost;
    
    private boolean moved = false;
    
    private int owner;
    private VillageModel belongingVillage;
    
    private static final int PEASANT_COST = 10;
    private static final int SPEARMAN_COST = 20;
    private static final int BARON_COST = 30;
    private static final int KNIGHT_COST = 40;
    
    private static final int PEASANT_MAINTENANCE = 2;
    private static final int SPEARMAN_MAINTENANCE = 6;
    private static final int BARON_MAINTENANCE = 18;
    private static final int KNIGHT_MAINTENANCE = 36;
    
    private static final int PEASANT_ATTACK = 1;
    private static final int SPEARMAN_ATTACK = 2;
    private static final int BARON_ATTACK = 3;
    private static final int KNIGHT_ATTACK = 4;
    
    private static final int PEASANT_DEFENSE = 1;
    private static final int SPEARMAN_DEFENSE = 2;
    private static final int BARON_DEFENSE = 3;
    private static final int KNIGHT_DEFENSE = 3;
    
    private static final int UNIT_SPEED = 5;
    
    public Unit ( Model.UnitType unitType, int owner, VillageModel belogingVillage, int xPosition, int yPosition )
    {
        this.unitType = unitType;
        this.owner = owner;
        this.belongingVillage = belogingVillage;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        
        if ( this.unitType == Model.UnitType.PEASANT )
        {
            this.strength = PEASANT_ATTACK;
            this.defense = PEASANT_DEFENSE;
            this.recruitmentCost = PEASANT_COST;
            this.maintenanceCost = PEASANT_MAINTENANCE; 
        }
        
        else if ( this.unitType == Model.UnitType.SPEARMAN )
        {
            this.strength = SPEARMAN_ATTACK;
            this.defense = SPEARMAN_DEFENSE;
            this.recruitmentCost = SPEARMAN_COST;
            this.maintenanceCost = SPEARMAN_MAINTENANCE;
        }
        
        else if ( this.unitType == Model.UnitType.BARON )
        {
            this.strength = BARON_ATTACK;
            this.defense = BARON_DEFENSE;
            this.recruitmentCost = BARON_COST;
            this.maintenanceCost = BARON_MAINTENANCE;
        }
        
        else if ( this.unitType == Model.UnitType.KNIGHT )
        {
            this.strength = KNIGHT_ATTACK;
            this.defense = KNIGHT_DEFENSE;
            this.recruitmentCost = KNIGHT_COST;
            this.maintenanceCost = KNIGHT_MAINTENANCE;
        }
    }
    
    public Model.UnitType getUnitType()
    {
        return unitType;
    }
    
    public int getStrength()
    {
        return strength;
    }
    
    public int getDefense()
    {
        return defense;
    }
    
    public int getRecruitmentCost()
    {
        return this.recruitmentCost;
    }
    
    public int getMaintenanceCost()
    {
        return this.maintenanceCost;
    }
    
    public boolean isEliminable()
    {
        return this.eliminable;
    }
    
    public boolean mergeUnits ( Unit unitToMerge )
    {
        if ( this.unitType == Model.UnitType.PEASANT && unitToMerge.getUnitType() == Model.UnitType.PEASANT )
        {
            this.unitType = Model.UnitType.SPEARMAN;
            this.strength = SPEARMAN_ATTACK;
            this.defense = SPEARMAN_DEFENSE;
            this.recruitmentCost = SPEARMAN_COST;
            this.maintenanceCost = SPEARMAN_MAINTENANCE;
            return true;
        }
        
        if ( (this.unitType == Model.UnitType.PEASANT && unitToMerge.getUnitType() == Model.UnitType.SPEARMAN) || (this.unitType == Model.UnitType.SPEARMAN && unitToMerge.getUnitType() == Model.UnitType.PEASANT))
        {
            this.unitType = Model.UnitType.BARON;
            this.strength = BARON_ATTACK;
            this.defense = BARON_DEFENSE;
            this.recruitmentCost = BARON_COST;
            this.maintenanceCost = BARON_MAINTENANCE; 
            return true;
        }
        
        if ( (this.unitType == Model.UnitType.SPEARMAN && unitToMerge.getUnitType() == Model.UnitType.SPEARMAN) || (this.unitType == Model.UnitType.PEASANT && unitToMerge.getUnitType() == Model.UnitType.BARON) || (this.unitType == Model.UnitType.BARON && unitToMerge.getUnitType() == Model.UnitType.PEASANT))
        {
            this.unitType = Model.UnitType.KNIGHT;
            this.strength = KNIGHT_ATTACK;
            this.defense = KNIGHT_DEFENSE;
            this.recruitmentCost = KNIGHT_COST;
            this.maintenanceCost = KNIGHT_MAINTENANCE; 
            return true;
        }
        
        return false;
    }
   
    public boolean killUnit ( Unit unitToKill )
    {
        if ( this.strength > unitToKill.getDefense() )
        {
            unitToKill.leaveVillage();
            return true;
        }
        
        return false;
    }
    
    public boolean destroyBulding( Building buldingToDestroy )
    {
        if ( this.strength > buldingToDestroy.getDefense() )
            return true;
        
        return false;
    }
    
    public  VillageModel getBelongingVillage()
    {
        return this.belongingVillage;
    }
    
    public void setBelongingVillage( VillageModel villageModel )
    {
        this.belongingVillage = villageModel;
    }
        
    public void leaveVillage()
    {
        belongingVillage.leaveAUnit( this );
    }
    
    public boolean movePosition( int newXPosition, int newYPosition )
    {
        boolean moved = BFS.tryToMoveUnit( newXPosition, newYPosition, this.xPosition, this.yPosition, owner, UNIT_SPEED, this );
        return moved;
    }
    
    public void changePosition( int newXPosition, int newYPosition )
    {
        this.xPosition = newXPosition;
        this.yPosition = newYPosition;
    }
    
    public Model.BuldingType getBuldingType()
    {
        return null;
    }
    
    public int getXPosition()
    {
        return xPosition;
    }
    
    public int getYPosition()
    {
        return yPosition;
    }
    
    public void setXposition( int newXPosition )
    {
        this.xPosition = newXPosition;
    }
    
    public void setYposition( int newYPosition )
    {
        this.yPosition = newYPosition;
    }
    
    public Unit getUnit()
    {
        return this;
    }
            
    public int getOwner()
    {
        return this.owner;
    }
    
    public boolean isMoved()
    {
        return this.moved;
    }
    
    public void unitMoved()
    {
        this.moved = true;
    }
    
    public void unitNotMoved()
    {
        this.moved = false;
    }
    
}
