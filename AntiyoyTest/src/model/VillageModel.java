/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import util.BFS;

/**
 *
 * @author toson
 */
public class VillageModel 
{
    private int money;
    private int moneyForTurn;
    private int[] coordCenterOfVillage = new int[2];
    private int owner;
    
    private int numberOfTilesControlled = 0;
    private int totalMaintenanceCost = 0;
    
    private int addCostVillageHut = 0;
    
    private boolean graved = false;
    
    List<HexagonModel> tiles = new LinkedList<>();
    
    
    public VillageModel ( int startMoney, int[] coordCenterOfVillage, List<HexagonModel> tiles, int owner )
    {
        this.money = startMoney;
        this.coordCenterOfVillage = coordCenterOfVillage;
        this.tiles = tiles;
        this.owner = owner;
    }
    
    public int getOwner()
    {
        return this.owner;
    }
    
    public void addTile ( HexagonModel tile )
    {
        tiles.add( tile );
        numberOfTilesControlled++;
        
        List<VillageModel>  listOfVillages = BFS.findVillage( tile.getRow(), tile.getCol(), tile.getOwner(), this );
        
        if ( !listOfVillages.isEmpty() )
        {
            int mantenanceCost = 0;
            
            int[] numberOfTiles = new int[listOfVillages.size()];
            
            int i = 0;
            for ( VillageModel vm : listOfVillages )
            {
                numberOfTiles[i] = vm.countTiles();
                i++;
            }
            
            int index = -1;
            int max = this.countTiles();
            
            for ( int j = 0; j < numberOfTiles.length ; ++j )
                if ( max < numberOfTiles[j] )
                {
                    index = j;
                    max = numberOfTiles[j];
                }
            
            VillageModel mergedVillage;
            List<HexagonModel> mergedVillageTotalTiles;
            
            Model model = Model.getModel();
            
            if ( index == -1 )
            {
                mergedVillage = this;
                mergedVillageTotalTiles = this.tiles;
            }
            
            else
            {   
                mergedVillage = listOfVillages.get(index);
                mergedVillage.sumAddCostVillageHut( this.getAddCostVillageHut() );
                mergedVillageTotalTiles = listOfVillages.get(index).getTiles();
                
                model.villageArray[this.getOwner()].remove(this);
                
                mergedVillageTotalTiles.addAll( this.tiles );
                
                for( HexagonModel hm : tiles )
                    hm.setVillageModel(mergedVillage);
                
                this.tiles.clear();
                this.countTiles();
                
                model.hexagonModelMatrix[coordCenterOfVillage[0]][coordCenterOfVillage[1]].setTileObject( null );
                model.playGameWindow.removeObjectInGame( coordCenterOfVillage[0], coordCenterOfVillage[1] );
            }
            
            for ( VillageModel vm : listOfVillages )
            {
                if ( vm != mergedVillage )
                {
                    model.villageArray[vm.getOwner()].remove(vm);
                    mergedVillage.sumAddCostVillageHut( vm.getAddCostVillageHut() );
                }
            }
            
            for ( VillageModel vm : listOfVillages )
            {
                if ( mergedVillage != vm )
                {

                    mergedVillageTotalTiles.addAll( vm.getTiles() );

                    for ( HexagonModel hm : vm.getTiles() )
                        if ( hm.getTileObject() != null )
                            vm.changeMaintenanceCost( -1 * hm.getTileObject().getMaintenanceCost() );
                    
                    for ( HexagonModel hm : vm.getTiles() )
                        hm.setVillageModel(mergedVillage);
                        
                    vm.getTiles().clear();
                    
                    model.hexagonModelMatrix[vm.getCoordCenterOfVillage()[0]][vm.getCoordCenterOfVillage()[1]].setTileObject( null );
                    model.playGameWindow.removeObjectInGame(vm.getCoordCenterOfVillage()[0],vm.getCoordCenterOfVillage()[1]);

                }
            }
            
            if ( index == -1 )
            {
                for ( HexagonModel hm : mergedVillageTotalTiles )
                    if ( hm.getTileObject() != null )
                        mantenanceCost += hm.getTileObject().getMaintenanceCost();
               
                this.resetTotalMantenanceCost();
                this.changeMaintenanceCost( mantenanceCost );
            }
            
            else 
            {
                for ( int j = 0; j < mergedVillageTotalTiles.size(); ++j )
                    mantenanceCost += mergedVillageTotalTiles.get(j).getTileObject().getMaintenanceCost();
                mergedVillage.resetTotalMantenanceCost();
                mergedVillage.changeMaintenanceCost( mantenanceCost );
            }
        }
                
    }
    
    public void removeTile( HexagonModel tile )
    {
        Model model = Model.getModel();

        tiles.remove( tile );

        numberOfTilesControlled -= 1;
        
        if ( tiles.size() == 1 )
        {
            model.villageArray[this.getOwner()].remove(this);
            tiles.get(0).setVillageModel(null);
            if ( tiles.get(0).getTileObject() != null )
            {
                tiles.get(0).setTileObject( new Building ( Model.BuldingType.TREE, owner, null, tiles.get(0).getRow(), tiles.get(0).getCol()));
                model.playGameWindow.changeObjectViewTree(tiles.get(0).getRow(), tiles.get(0).getCol() );
            }
        }
        
        else 
        {
            if ( tile.getRow() == this.coordCenterOfVillage[0] && tile.getCol() == this.coordCenterOfVillage[1])
            {
                Random rand = new Random();
                int tileIndex = rand.nextInt(tiles.size());
                boolean rightTileFound = false;
                boolean loopCompleted = false;
                int startPosition = tileIndex;
                int tilesLength = tiles.size();
            
                while ( !rightTileFound )
                {
                    if ( tiles.get(tileIndex).getTileObject() == null || loopCompleted )
                    {
                        if ( loopCompleted )
                        {
                            this.changeMaintenanceCost( -1 * tiles.get(tileIndex).getTileObject().getMaintenanceCost() );
                            if ( tiles.get(tileIndex).getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT )
                                this.decreaseAddCostVillageHut();
                        }
                        rightTileFound = true;
                        this.coordCenterOfVillage[0] = tiles.get(tileIndex).getRow();
                        this.coordCenterOfVillage[1] = tiles.get(tileIndex).getCol();
                        
                        tiles.get(tileIndex).setTileObject( new Building( Model.BuldingType.CENTER_OF_VILLAGE, owner, this , this.coordCenterOfVillage[0], this.coordCenterOfVillage[1] ));
                        model.playGameWindow.changeObjectViewCenterOfVillage( this.coordCenterOfVillage[0], this.coordCenterOfVillage[1] );
                        
                    }
                
                    tileIndex = ( tileIndex + 1) % tilesLength;
                    loopCompleted = tileIndex == startPosition;
                    
                }
                
            }
        
            boolean connection = BFS.controllConnectionOfVillage( tiles, coordCenterOfVillage );
        
            if ( !connection )
                countTiles();
        }
    }
    
    public void resetTotalMantenanceCost()
    {
        this.totalMaintenanceCost = 0;
    }
    
    public void changeMaintenanceCost( int addCost )
    {
        this.totalMaintenanceCost = this.totalMaintenanceCost + addCost;
    }
    
    public int getMaintenanceCost ()
    {
        return this.totalMaintenanceCost;
    }
    
    public List<HexagonModel> getTiles()
    {
        return this.tiles;
    }
    
    public int[] getCoordCenterOfVillage()
    {
        return coordCenterOfVillage;
    }
    
    public void setCoordCenterOfVillage( int[] coordCenterOfVillage )
    {
        this.coordCenterOfVillage = coordCenterOfVillage;
    }
     
    public int countTiles()
    {
        numberOfTilesControlled = tiles.size();
        return numberOfTilesControlled;
    }
    
    public int previsionOfProfit()
    {
        controlNumberOfTilesControlled();
        return numberOfTilesControlled - totalMaintenanceCost;
    }
    
    public void effectiveProfit()
    {
        if ( this.money + numberOfTilesControlled - totalMaintenanceCost < 0 )
        {
            killAllUnits();
            this.money = 0;
        }
        
        else
        {
            this.money = this.money + numberOfTilesControlled - totalMaintenanceCost;
        }        
    }
    
    private void killAllUnits()
    {
        Model model = Model.getModel();
        graved = true;
        for ( HexagonModel hm : tiles )
            if ( hm.getTileObject() != null && hm.getTileObject().getUnitType() != null )
            {
                this.changeMaintenanceCost( -1 * hm.getTileObject().getMaintenanceCost() );
                hm.setTileObject( new Building( Model.BuldingType.GRAVE, hm.getOwner(), this, hm.getRow(), hm.getCol() ));
                model.playGameWindow.changeObjectViewGrave( hm.getRow(), hm.getCol() );
            }
    }  
    
    public void leaveAUnit( Unit unit )
    {
        
    }
    
    public int getMoney()
    {
        return this.money;
    }
    
    public void changeMoney( int cost )
    {
        this.money = this.money - cost;
    }
    
    private void controlNumberOfTilesControlled()
    {
        numberOfTilesControlled = tiles.size();
    }
    
    public void verifyGravedVillage()
    {
        Model model = Model.getModel();
        if ( graved )
        {
            graved = false;
            for( HexagonModel hm : tiles )
                if ( hm.getTileObject() != null && hm.getTileObject().getBuldingType() != null & hm.getTileObject().getBuldingType() == Model.BuldingType.GRAVE )
                {
                    hm.setTileObject( new Building ( Model.BuldingType.TREE, hm.getOwner(), this, hm.getRow(), hm.getCol() ));
                    changeMaintenanceCost( hm.getTileObject().getMaintenanceCost() );
                    model.playGameWindow.changeObjectViewTree( hm.getRow(), hm.getCol() );
                }
        }
    }
    
    public void increaseAddCostVillageHut()
    {
        this.addCostVillageHut += 2;
    }
    
    public void decreaseAddCostVillageHut()
    {
        this.addCostVillageHut -= 2;
    }
    
    public void resetAddCostVillageHut()
    {
        this.addCostVillageHut = 0;
    }
    
    public void sumAddCostVillageHut( int costToAdd )
    {
        this.addCostVillageHut += costToAdd;
    }
     
    public int getAddCostVillageHut()
    {
        return this.addCostVillageHut;
    }
            
}
