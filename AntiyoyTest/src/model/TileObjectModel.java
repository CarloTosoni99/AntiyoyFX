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
public abstract class TileObjectModel 
{
    public abstract int getDefense();
    
    public abstract int getMaintenanceCost();
    
    public abstract boolean isEliminable();
    
    public abstract Model.BuldingType getBuldingType();
    
    public abstract Model.UnitType getUnitType();
    
    public abstract boolean mergeUnits( Unit unitToMerge);
    
    public abstract Unit getUnit();
    
    public abstract boolean isMoved();
            
}
