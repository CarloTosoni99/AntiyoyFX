/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import model.Building;
import model.Model;
import model.TileObjectModel;
import model.Unit;
import model.VillageModel;

/**
 *
 * @author toson
 */
public class HexagonView extends Polygon
{
    
    private static final double L_SMALL = 30d;
    private static final double L_MEDIUM = 22d;
    private static final double L_BIG = 18d;
    
    private static final int BASE_VILLAGE_HUT_COST = 12;
    
    
    private int color = 0;
    
    private int row;
    private int col;
    private int dimension;
    
    private Line[] hexagonBorder;
    
    public HexagonView( int row, int col, boolean isLand, int dimension, Line[] hexagonBorder )
    {
        super();
        
        assert  hexagonBorder.length == 6 : "Mannaggia ho sbagliato il codice perchè...";
        this.hexagonBorder = hexagonBorder;
        this.row = row;
        this.col = col;
        this.dimension = dimension;
        
        Model model = Model.getModel();
        double L = 0d;
        
        switch (dimension) {
            case 1:
                L = L_SMALL;
                break;
            case 2:
                L = L_MEDIUM;
                break;
            case 3:
                L = L_BIG;
                break;
            default:
                break;
        }
        
        final double sqrt3 = Math.sqrt(3d);
        double offsetX = 0;
        double offsetY = 0;
        
        if ( col % 2 == 1 )
            offsetY = sqrt3*L/2;
        
        offsetX = col*3*L/2;
        offsetY = row*sqrt3*L + offsetY;
        
        
       
        this.getPoints().addAll(new Double[]{
            L/2 + offsetX,      0d + offsetY,
            3*L/2 + offsetX,    0d + offsetY,
            2*L + offsetX,      sqrt3*L/2 + offsetY,
            3*L/2 + offsetX,    sqrt3*L + offsetY,
            L/2 + offsetX,      sqrt3*L + offsetY,
            0d + offsetX,       sqrt3*L/2 + offsetY});
   
        if ( isLand )
        {
            this.setFill(Color.GREEN);
        }
        else
        {
            this.setFill(Color.BLUE);
        }
        
        this.setOnMouseClicked((me) -> hexagonViewBehavior());
    }
    
    public void changeColor( int color )
    {
        this.color = color;
        switch (color) {
            case 0:
                this.setFill(Color.RED);
                break;
            case 1:
                this.setFill(Color.CYAN);
                break;
            case 2:
                this.setFill(Color.LIGHTGREEN);
                break;
            case 3:
                this.setFill(Color.YELLOW);
                break;
            case 4:
                this.setFill(Color.WHITE);
                break;
            case 5:
                this.setFill(Color.PINK);
                break;
            default:
                break;
        }
    }
    
    public void assignWeight( int weight )
    {
        if ( weight % 2 == 1 )
            hexagonBorder[0].setVisible(true);
        else
            hexagonBorder[0].setVisible(false);
        
        weight = weight / 2;
        
        if ( weight % 2 == 1 )
            hexagonBorder[1].setVisible(true);
        else
            hexagonBorder[1].setVisible(false);
        
        weight = weight / 2;
        
        if ( weight % 2 == 1 )
            hexagonBorder[2].setVisible(true);
        else
            hexagonBorder[2].setVisible(false);
        
        weight = weight / 2;
        
        if ( weight % 2 == 1 )
            hexagonBorder[3].setVisible(true);
        else
            hexagonBorder[3].setVisible(false);
        
        weight = weight / 2;
        
        if ( weight % 2 == 1 )
            hexagonBorder[4].setVisible(true);
        else
            hexagonBorder[4].setVisible(false);
        
        weight = weight / 2;
        
        if ( weight % 2 == 1 )
            hexagonBorder[5].setVisible(true);
        else
            hexagonBorder[5].setVisible(false);
    }
    
    public void hexagonViewBehavior()
    {
        Model model = Model.getModel();
        
        int owner = model.hexagonModelMatrix[row][col].getOwner();
        
        if ( model.villageModelSelected != null && model.hexagonModelMatrix[row][col].getVillageModel() == model.villageModelSelected  && !model.canBuyUnit && !model.canBuyBuilding && model.unitSelected == null )
        {
            if ( model.hexagonModelMatrix[row][col].getTileObject() != null && model.hexagonModelMatrix[row][col].getTileObject().getUnitType() != null && !model.hexagonModelMatrix[row][col].getTileObject().isMoved() )
            {
                model.unitSelected = model.hexagonModelMatrix[row][col].getTileObject().getUnit();
                model.playGameWindow.setCoordUnitSelected( model.unitSelected.getXPosition(), model.unitSelected.getYPosition() );
                model.playGameWindow.setTypeUnitSelected( model.unitSelected.getUnitType() );
            }
        }
        else if ( model.hexagonModelMatrix[row][col].getOwner() == model.currentPlayer && model.hexagonModelMatrix[row][col].getVillageModel() != null && ( model.villageModelSelected != model.hexagonModelMatrix[row][col].getVillageModel() ) )
        {
            model.villageModelSelected = model.hexagonModelMatrix[row][col].getVillageModel();
            model.playGameWindow.changeVillageHutPrice( BASE_VILLAGE_HUT_COST + model.villageModelSelected.getAddCostVillageHut() );
            model.playGameWindow.setMoneyVillage( model.villageModelSelected.getMoney(), model.villageModelSelected.previsionOfProfit() );
            if ( model.hexagonModelMatrix[row][col].getTileObject() != null && model.hexagonModelMatrix[row][col].getTileObject().getUnitType() != null && !model.hexagonModelMatrix[row][col].getTileObject().isMoved() )
            {
                model.unitSelected = model.hexagonModelMatrix[row][col].getTileObject().getUnit();
                model.playGameWindow.setCoordUnitSelected( model.unitSelected.getXPosition(), model.unitSelected.getYPosition() );
                model.playGameWindow.setTypeUnitSelected( model.unitSelected.getUnitType() );
            }
        }
        
        else if ( (model.hexagonModelMatrix[row][col].getVillageModel() == model.villageModelSelected) || 
                ( model.findBorderOwner(row, col) && model.canBuyUnit && model.hexagonModelMatrix[row][col].isLand()) ||
                ( model.findBorderOwner(row, col) && model.unitSelected != null ) )
        {
            if( model.canBuyUnit )
            {
                if ( model.villageModelSelected != null )
                {
                    VillageModel vm = model.villageModelSelected;
                    int villageMoney = vm.getMoney();
                    if ( villageMoney >= ( model.currentUnitShop + 1) * 10 && (( vm.getOwner() == model.hexagonModelMatrix[row][col].getOwner() && ( model.hexagonModelMatrix[row][col].getTileObject() == null || model.hexagonModelMatrix[row][col].getTileObject().isEliminable() )) || (  model.findMaxDefense(row, col) < ( model.currentUnitShop + 1) ) && model.hexagonModelMatrix[row][col].getOwner() != vm.getOwner() ) )
                    {
                        if ( model.hexagonModelMatrix[row][col].getTileObject() != null && model.hexagonModelMatrix[row][col].getVillageModel() != null )
                        {
                            model.hexagonModelMatrix[row][col].getVillageModel().changeMaintenanceCost( -1 * model.hexagonModelMatrix[row][col].getTileObject().getMaintenanceCost() );
                            if ( model.hexagonModelMatrix[row][col].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT)
                                model.hexagonModelMatrix[row][col].getVillageModel().decreaseAddCostVillageHut();
                        }
                            
                        Model.UnitType newUnitType = findUnitType( model.currentUnitShop );
                        ObjectsView newUnitView = new ObjectsView( row, col, dimension, model.imageArray[model.currentUnitShop], false);
                        Unit newUnitModel = new Unit( newUnitType, model.currentPlayer, vm, row, col);
                        if ( model.hexagonModelMatrix[row][col].getOwner() != vm.getOwner())
                            newUnitModel.unitMoved();
                        else
                            newUnitView.startJumpAnimation();
                        if ( model.hexagonModelMatrix[row][col].getVillageModel() != null && model.hexagonModelMatrix[row][col].getTileObject() != null && model.hexagonModelMatrix[row][col].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT )
                            model.hexagonModelMatrix[row][col].getVillageModel().decreaseAddCostVillageHut();
                        model.hexagonModelMatrix[row][col].setTileObject( newUnitModel );
                        vm.changeMaintenanceCost( newUnitModel.getMaintenanceCost() );
                        vm.changeMoney( newUnitModel.getRecruitmentCost());
                        model.playGameWindow.addObjectInGame( row, col, newUnitView );
                        if ( model.hexagonModelMatrix[row][col].getVillageModel() != model.villageModelSelected )
                        {
                            if ( model.hexagonModelMatrix[row][col].getVillageModel() != null )
                                model.hexagonModelMatrix[row][col].getVillageModel().removeTile( model.hexagonModelMatrix[row][col] );
                            model.hexagonModelMatrix[row][col].setVillageModel(vm);
                            model.hexagonModelMatrix[row][col].setOwner( vm.getOwner() );
                            vm.addTile( model.hexagonModelMatrix[row][col] );
                            vm.countTiles();
                            changeColor(vm.getOwner());
                            model.calculateNewWeights( row, col );
                            changeAssignmentWeight();
                        }
                        model.playGameWindow.setMoneyVillage( vm.getMoney(), vm.previsionOfProfit() );
                    }
                    
                    else if ( villageMoney >= ( model.currentUnitShop + 1) * 10 && model.hexagonModelMatrix[row][col].getTileObject() != null && vm.equals(model.hexagonModelMatrix[row][col].getVillageModel()))
                    {
                        int oldMantenanceCost = model.hexagonModelMatrix[row][col].getTileObject().getMaintenanceCost();
                        Model.UnitType newUnitType = findUnitType( model.currentUnitShop );
                        Unit newUnitModel = new Unit( newUnitType, model.currentPlayer, vm, row, col );
                        boolean unitMerged = model.hexagonModelMatrix[row][col].getTileObject().mergeUnits( newUnitModel );
                        if ( unitMerged )
                        {
                            ObjectsView newUnitView = new ObjectsView( row, col, dimension, model.imageArray[findNewMergedImage( model.hexagonModelMatrix[row][col].getTileObject())], false );
                            if ( !model.hexagonModelMatrix[row][col].getTileObject().isMoved() )
                                newUnitView.startJumpAnimation();
                            vm.changeMaintenanceCost( model.hexagonModelMatrix[row][col].getTileObject().getMaintenanceCost() - oldMantenanceCost  );
                            vm.changeMoney( newUnitModel.getRecruitmentCost() );
                            model.playGameWindow.addObjectInGame( row, col, newUnitView );
                            model.playGameWindow.setMoneyVillage( vm.getMoney(), vm.previsionOfProfit() );
                        }
                    }
                }
            }
            else if ( model.canBuyBuilding )
            {
                if ( model.hexagonModelMatrix[row][col].getVillageModel() != null && ( model.currentBuildingShop != 0 || model.findOtherHuts(row, col)) )
                {
                    VillageModel vm = model.villageModelSelected;
                    int villageMoney = vm.getMoney();
                    int costMoney = Building.calculateBuildingCost( model.currentBuildingShop );
                    if ( villageMoney >= costMoney && model.hexagonModelMatrix[row][col].getTileObject() == null )
                    {
                        Model.BuldingType newBuildingType = findBuildingType( model.currentBuildingShop );
                        Building newBuildingModel = new Building ( newBuildingType, model.currentPlayer, vm, row, col );

                        if ( newBuildingType == Model.BuldingType.VILLAGE_HUT )
                        {
                            vm.changeMoney( costMoney + vm.getAddCostVillageHut() );
                            vm.increaseAddCostVillageHut();
                            model.playGameWindow.changeVillageHutPrice( BASE_VILLAGE_HUT_COST + vm.getAddCostVillageHut() );
                        }
                        else 
                            vm.changeMoney(costMoney);

                        vm.changeMaintenanceCost( newBuildingModel.getMaintenanceCost()  );
                        model.hexagonModelMatrix[row][col].setTileObject( newBuildingModel );
                        ObjectsView newBuildingView = new ObjectsView( row, col, dimension, model.imageArray[model.currentBuildingShop + 4], newBuildingType == Model.BuldingType.VILLAGE_HUT);
                        model.playGameWindow.addObjectInGame( row, col, newBuildingView );
                        model.playGameWindow.setMoneyVillage( vm.getMoney(), vm.previsionOfProfit() );
                    }
                }
            }
            else if ( model.unitSelected != null && model.villageModelSelected != null )
            {
                VillageModel vm = model.villageModelSelected;
                int oldMaintenanceCost = 0;
                boolean unitMerged = false;
                if ( model.hexagonModelMatrix[row][col].getTileObject() != null && ( model.hexagonModelMatrix[row][col].getOwner() == model.villageModelSelected.getOwner() || model.hexagonModelMatrix[row][col].getOwner() >= 0 ))
                    oldMaintenanceCost = model.hexagonModelMatrix[row][col].getTileObject().getMaintenanceCost();
                if ( model.villageModelSelected == model.hexagonModelMatrix[row][col].getVillageModel() && ( model.hexagonModelMatrix[row][col].getTileObject() != null && model.hexagonModelMatrix[row][col].getTileObject().getUnitType() != null && verifyPossibleMerge( model.unitSelected.getUnitType(), model.hexagonModelMatrix[row][col].getTileObject().getUnitType() )))
                    unitMerged = true;
                boolean isPossibleToMove = model.unitSelected.movePosition( row, col );
                if ( isPossibleToMove && ( model.unitSelected.getOwner() == model.hexagonModelMatrix[row][col].getOwner() ||  model.unitSelected.getStrength() > model.findMaxDefense(row, col)) )
                {
                    model.hexagonModelMatrix[row][col].setOwner( model.villageModelSelected.getOwner() );
                    changeColor( model.hexagonModelMatrix[row][col].getOwner() );
                    if ( model.hexagonModelMatrix[row][col].getVillageModel() != null && model.hexagonModelMatrix[row][col].getTileObject() != null && model.hexagonModelMatrix[row][col].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT)
                        model.hexagonModelMatrix[row][col].getVillageModel().decreaseAddCostVillageHut();
                    
                    if( unitMerged )
                    {
                        vm.changeMaintenanceCost( model.hexagonModelMatrix[row][col].getTileObject().getMaintenanceCost() - oldMaintenanceCost - model.unitSelected.getMaintenanceCost() );
                        ObjectsView newUnitView = new ObjectsView( row, col, dimension, model.imageArray[findNewMergedImage( model.hexagonModelMatrix[row][col].getTileObject())], false );
                        model.playGameWindow.removeObjectInGame(model.unitSelected.getXPosition(), model.unitSelected.getYPosition());
                        model.playGameWindow.addObjectInGame( row, col, newUnitView );
                        model.hexagonModelMatrix[model.unitSelected.getXPosition()][model.unitSelected.getYPosition()].setTileObject(null); 
                        if ( !model.hexagonModelMatrix[row][col].getTileObject().isMoved() )
                        {
                            model.unitSelected = model.hexagonModelMatrix[row][col].getTileObject().getUnit();
                            model.playGameWindow.setCoordUnitSelected( model.unitSelected.getXPosition(), model.unitSelected.getYPosition() );
                            model.playGameWindow.setTypeUnitSelected( model.unitSelected.getUnitType() );
                            model.playGameWindow.startAnimation(row, col);
                        }
                        else
                        {
                            model.unitSelected = null;
                            model.playGameWindow.deactiveUnitDataContainer();
                        }

                    }
                    else 
                    {
                        if ( model.hexagonModelMatrix[row][col].getVillageModel() != null && model.hexagonModelMatrix[row][col].getTileObject() != null && model.hexagonModelMatrix[row][col].getTileObject().getBuldingType() == Model.BuldingType.VILLAGE_HUT )
                            model.hexagonModelMatrix[row][col].getVillageModel().decreaseAddCostVillageHut();
                        ObjectsView objectToMove = model.playGameWindow.getObjectsView( model.unitSelected.getXPosition(), model.unitSelected.getYPosition() );
                        objectToMove.stopJumpAnimation();
                        objectToMove.createObjectsViewTranslatAnimation( row, col);
                        model.playGameWindow.changeObjectPosition(model.unitSelected.getXPosition(), model.unitSelected.getYPosition(), row, col);
                        model.hexagonModelMatrix[model.unitSelected.getXPosition()][model.unitSelected.getYPosition()].setTileObject(null);
                        model.unitSelected.changePosition(row, col);
                        if ( model.hexagonModelMatrix[row][col].getVillageModel() != null && vm != model.hexagonModelMatrix[row][col].getVillageModel() )
                        {
                            model.hexagonModelMatrix[row][col].getVillageModel().changeMaintenanceCost(-1* oldMaintenanceCost );
                            model.hexagonModelMatrix[row][col].getVillageModel().removeTile(model.hexagonModelMatrix[row][col]);
                        }
                        model.hexagonModelMatrix[row][col].setTileObject(model.unitSelected);
                        model.hexagonModelMatrix[row][col].setVillageModel( vm );
                        if ( !model.villageModelSelected.getTiles().contains(model.hexagonModelMatrix[row][col]))
                            model.villageModelSelected.addTile( model.hexagonModelMatrix[row][col] );
                        vm.countTiles();
                        model.unitSelected.unitMoved();
                        model.unitSelected = null;
                        model.playGameWindow.deactiveUnitDataContainer();

                    }
                    
                    model.playGameWindow.setMoneyVillage( vm.getMoney(), vm.previsionOfProfit() );
                    
                    model.calculateNewWeights( row, col );
                    changeAssignmentWeight();
                    
                }
                else
                {
                    model.unitSelected = null;
                    model.playGameWindow.deactiveUnitDataContainer();
                }
            }
        }
        
        else
        {
            model.villageModelSelected = null;
            model.unitSelected = null;
            model.playGameWindow.deleteMoneyVillageText();
            model.playGameWindow.deactiveUnitDataContainer();
        }
    }
    
    private Model.UnitType findUnitType( int currentUnitShop )
    {
        Model.UnitType unitType;
        if ( currentUnitShop == 0 )
        {
            return unitType = Model.UnitType.PEASANT;
        }
        else if ( currentUnitShop == 1 )
        {
            return unitType = Model.UnitType.SPEARMAN;
        }
        else if ( currentUnitShop == 2 )
        {
            return unitType = Model.UnitType.BARON;
        }
        else
        {
            return unitType = Model.UnitType.KNIGHT;
        }
    }
    
    private int findNewMergedImage( TileObjectModel newUnitMerged )
    {
        if ( newUnitMerged.getUnitType() == Model.UnitType.PEASANT )
            return 0;
        else if ( newUnitMerged.getUnitType() == Model.UnitType.SPEARMAN )
            return 1;
        else if ( newUnitMerged.getUnitType() == Model.UnitType.BARON )
            return 2;
        else
            return 3;
    }
    
    private Model.BuldingType findBuildingType( int currentBuildingShop )
    {
        if ( currentBuildingShop == 0 )
            return Model.BuldingType.VILLAGE_HUT;
        else if ( currentBuildingShop == 1 )
            return Model.BuldingType.TOWER;
        else
            return Model.BuldingType.MAGIC_TOWER;
    }
    
    private void changeAssignmentWeight()
    {
        Model model = Model.getModel();
        PlayGameWindow pgw = model.playGameWindow;
        
        pgw.determinateVisibleBorders( row, col );
        if ( col % 2 == 0 )
        {
            if ( row - 1 >= 0 )
                pgw.determinateVisibleBorders( row - 1, col );
            if ( row + 1 < model.hexagonModelMatrix.length  )
                pgw.determinateVisibleBorders( row + 1, col );         
            if ( col - 1 >= 0  )
                pgw.determinateVisibleBorders( row, col - 1 );
            if ( col + 1 < model.hexagonModelMatrix[0].length )
                pgw.determinateVisibleBorders( row, col + 1 );
            if ( row - 1 >= 0 && col - 1 >= 0 )
                pgw.determinateVisibleBorders( row - 1, col - 1 );
            if ( row - 1 >= 0 && col + 1 < model.hexagonModelMatrix[0].length  )
                pgw.determinateVisibleBorders( row - 1, col + 1 );
        }
        
        else
        {
            if ( row - 1 >= 0 )
                pgw.determinateVisibleBorders( row - 1, col );
            if ( row + 1 < model.hexagonModelMatrix.length )
                pgw.determinateVisibleBorders( row + 1, col );
            if ( col - 1 >= 0  )
                pgw.determinateVisibleBorders( row, col - 1 );
            if ( col + 1 < model.hexagonModelMatrix[0].length )
                pgw.determinateVisibleBorders( row, col + 1 );
            if ( row + 1 < model.hexagonModelMatrix.length && col - 1 >= 0 )
                pgw.determinateVisibleBorders( row + 1, col - 1 );
            if ( row + 1 < model.hexagonModelMatrix.length && col + 1 < model.hexagonModelMatrix[0].length )
                pgw.determinateVisibleBorders( row + 1, col + 1 );
        } 
    }
    
    public boolean verifyPossibleMerge( Model.UnitType firstUnitType, Model.UnitType secondUnitType )
    {
        if (    (firstUnitType == Model.UnitType.PEASANT && secondUnitType == Model.UnitType.PEASANT) || 
                (firstUnitType == Model.UnitType.PEASANT && secondUnitType == Model.UnitType.SPEARMAN) ||
                (firstUnitType == Model.UnitType.SPEARMAN && secondUnitType == Model.UnitType.PEASANT) ||
                (firstUnitType == Model.UnitType.SPEARMAN && secondUnitType == Model.UnitType.SPEARMAN) ||
                (firstUnitType == Model.UnitType.BARON && secondUnitType == Model.UnitType.PEASANT) ||
                (firstUnitType == Model.UnitType.PEASANT && secondUnitType == Model.UnitType.BARON) )
            return true;
        return false;
    }
 
}
