/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.image.Image ;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HexagonModel;
import model.Model;
import model.VillageModel;

/**
 *
 * @author toson
 */

public class PlayGameWindow extends Pane
{    
    private HexagonView[][] hexagonViewMatrix;
    private Line[][][] borderLineMatrix;
    private ObjectsView[][] unitViewMatrix;
    
    private Pane mapContainer;
    private int dimension;
    
    private Text moneyCount;
    private Text moneyPrevision;
    private Text typeUnitSelected;
    private Text coordUnitSelected;
    private Text villageHutPrice;
            
    private HBox moneyDataContainer;
    private HBox unitDataContainer;
    
    private Button buyUnits;
    private Button buyBuildings;
    
    private static final double BUTTON_SMALL_DIMENSION = 285d;
    private static final double BUTTON_MEDIUM_DIMENSION = 370d;
    private static final double BUTTON_BIG_DIMENSION = 408d;
    
    private static final double L_SMALL = 30d;
    private static final double L_MEDIUM = 22d;
    private static final double L_BIG = 18d;
    
    private int numberOfPlayers;
    
    private static final String[] buttonDescription = {
        "buy ( cost 10 )",
        "buy ( cost 20 )",
        "buy ( cost 30 )",
        "buy ( cost 40 )",
        "buy ( base cost 12 )",
        "buy ( cost 15 )",
        "buy ( cost 35 )",
    };
    
    
    private static final String[] playerName = {
        "RED",
        "BLUE",
        "GREEN",
        "YELLOW",
        "WHITE",
        "PINK",
    };
    
    private final Image[] imageArray = new Image[11];
  
    public PlayGameWindow( int dimension )
    {
        super();
        
        this.dimension = dimension;
        double L = 0d;
        double buttonWidthDimension = 0;
        if ( dimension == 1 )
        {
            buttonWidthDimension = BUTTON_SMALL_DIMENSION;
            L = L_SMALL;
        }
        else if ( dimension == 2 )
        {
            buttonWidthDimension = BUTTON_MEDIUM_DIMENSION;
            L = L_MEDIUM;    
        }
        else if ( dimension == 3 )
        {
            buttonWidthDimension = BUTTON_BIG_DIMENSION; 
            L = L_BIG;
        }
        assignImages();
                
        Model model = Model.getModel();
        
        numberOfPlayers = 0;
        for ( int i = 0; i < model.colorsArray.length ; ++i )
            if ( model.colorsArray[i] )
                numberOfPlayers += 1;
        
        VBox playGameContainer = new VBox (10);
        playGameContainer.setAlignment(Pos.CENTER);
      
        this.mapContainer = new Pane();
        
        hexagonViewMatrix = new HexagonView[model.hexagonModelMatrix.length][model.hexagonModelMatrix[0].length];
        borderLineMatrix = new Line[model.hexagonModelMatrix.length][model.hexagonModelMatrix[0].length][6];
        unitViewMatrix = new ObjectsView[model.hexagonModelMatrix.length][model.hexagonModelMatrix[0].length];
       
        
        for( int i = 0; i < borderLineMatrix.length; ++i )
            for( int j = 0; j < borderLineMatrix[i].length; ++j )
            {
                borderLineMatrix[i][j][0] = new Line( BorderArray.getPointCoord(i,j,0, L)[0], BorderArray.getPointCoord(i, j, 0, L)[1], BorderArray.getPointCoord(i, j, 1, L)[0], BorderArray.getPointCoord(i, j, 1, L)[1]);
                borderLineMatrix[i][j][0].setStrokeWidth( 4d );
                borderLineMatrix[i][j][0].setVisible(false);
                
                borderLineMatrix[i][j][1] = new Line( BorderArray.getPointCoord(i,j,1, L)[0], BorderArray.getPointCoord(i, j, 1, L)[1], BorderArray.getPointCoord(i, j, 2, L)[0], BorderArray.getPointCoord(i, j, 2, L)[1]);
                borderLineMatrix[i][j][1].setStrokeWidth( 4d );
                borderLineMatrix[i][j][1].setVisible(false);
                
                borderLineMatrix[i][j][2] = new Line( BorderArray.getPointCoord(i,j,2, L)[0], BorderArray.getPointCoord(i, j, 2, L)[1], BorderArray.getPointCoord(i, j, 3, L)[0], BorderArray.getPointCoord(i, j, 3, L)[1]);
                borderLineMatrix[i][j][2].setStrokeWidth( 4d );
                borderLineMatrix[i][j][2].setVisible(false);
                
                borderLineMatrix[i][j][3] = new Line( BorderArray.getPointCoord(i,j,3, L)[0], BorderArray.getPointCoord(i, j, 3, L)[1], BorderArray.getPointCoord(i, j, 4, L)[0], BorderArray.getPointCoord(i, j, 4, L)[1]);
                borderLineMatrix[i][j][3].setStrokeWidth( 4d );
                borderLineMatrix[i][j][3].setVisible(false);
                
                borderLineMatrix[i][j][4] = new Line( BorderArray.getPointCoord(i,j, 4, L)[0], BorderArray.getPointCoord(i, j, 4, L)[1], BorderArray.getPointCoord(i, j, 5, L)[0], BorderArray.getPointCoord(i, j, 5, L)[1]);
                borderLineMatrix[i][j][4].setStrokeWidth( 4d );
                borderLineMatrix[i][j][4].setVisible(false);
                
                borderLineMatrix[i][j][5] = new Line( BorderArray.getPointCoord(i,j,5, L)[0], BorderArray.getPointCoord(i, j, 5, L)[1], BorderArray.getPointCoord(i, j, 0, L)[0], BorderArray.getPointCoord(i, j, 0, L)[1]);
                borderLineMatrix[i][j][5].setStrokeWidth( 4d );
                borderLineMatrix[i][j][5].setVisible(false);
            }
        
        for( int i = 0; i < hexagonViewMatrix.length; ++i )
            for( int j = 0; j< hexagonViewMatrix[i].length; ++j )
            {
                hexagonViewMatrix[i][j] = new HexagonView( i, j, model.hexagonModelMatrix[i][j].isLand(), dimension, borderLineMatrix[i][j] );
                
                if ( model.hexagonModelMatrix[i][j].getTileObject() != null )
                {
                    if ( model.hexagonModelMatrix[i][j].getTileObject().getBuldingType() != null && model.hexagonModelMatrix[i][j].getTileObject().getBuldingType() == Model.BuldingType.CENTER_OF_VILLAGE )
                        unitViewMatrix[i][j] = new ObjectsView( i, j, dimension, imageArray[7], false );
                    
                    if ( model.hexagonModelMatrix[i][j].getTileObject().getBuldingType() != null && model.hexagonModelMatrix[i][j].getTileObject().getBuldingType() == Model.BuldingType.TREE )
                        unitViewMatrix[i][j] = new ObjectsView( i, j , dimension, imageArray[8], false );
                        
                }
                
                if ( model.hexagonModelMatrix[i][j].getOwner() >= 0 )
                {
                    hexagonViewMatrix[i][j].assignWeight( model.hexagonModelMatrix[i][j].getWeight() );
                    hexagonViewMatrix[i][j].changeColor( model.hexagonModelMatrix[i][j].getOwner() );
                }
            }
        
        
        for( int i = 0; i < hexagonViewMatrix.length; ++i )
            mapContainer.getChildren().addAll( hexagonViewMatrix[i]);
        
        for( int i = 0; i < borderLineMatrix.length; ++i )
            for( int j = 0; j < borderLineMatrix[i].length; ++j )
                mapContainer.getChildren().addAll( borderLineMatrix[i][j]);
        
        for( int i = 0; i < hexagonViewMatrix.length; ++i )
            for ( int j = 0; j < hexagonViewMatrix[i].length; ++j )
                if ( unitViewMatrix[i][j] != null )
                    mapContainer.getChildren().add( unitViewMatrix[i][j] );
   
        VBox unitVillageDataContainer = defineDataInterface( buttonWidthDimension );
        HBox shopButtonContainer = defineShopInterface( buttonWidthDimension );
        
        playGameContainer.setPadding(new Insets (2, 6, 10, 6));
        playGameContainer.getChildren().addAll(  unitVillageDataContainer, mapContainer, shopButtonContainer );
        
        this.getChildren().add( playGameContainer );
        model.playGameWindow = this;
    }
    
    private void assignImages()
    {
        imageArray[0] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Peasant.jpg"));
        imageArray[1] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Spearman.jpg"));
        imageArray[2] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Baron.jpg"));
        imageArray[3] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Knight.jpg"));
        imageArray[4] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Village_Hut.jpg"));
        imageArray[5] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Tower.jpg"));
        imageArray[6] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Magic_Tower.jpg"));
        imageArray[7] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Center_Of_Village.jpg"));
        imageArray[8] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Tree.jpg"));
        imageArray[9] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Grave.jpg"));
        imageArray[10] = new Image(getClass().getResourceAsStream("/view/Images_Resource/Skip_Sign.jpg"));
        
        Model model = Model.getModel();
        model.imageArray = this.imageArray;

    }
    
    private VBox defineDataInterface( double buttonWidthDimension )
    {
        Model model = Model.getModel();
        
        HBox villageDataContainer = new HBox( 10 );
        
        Text turnText = new Text( "turn of player: ");
        turnText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Text turnTextPlayer = new Text( "");
        turnTextPlayer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        turnTextPlayer.setStrokeWidth( 1d );
        turnTextPlayer.setStroke(Color.BLACK);
        
        findRightTextPlayer( turnTextPlayer );
        
        HBox turnContainer = new HBox ( 5 );
        turnContainer.setPrefHeight( 35d );
        turnContainer.setPrefWidth( 300d );
        turnContainer.setAlignment(Pos.CENTER_LEFT);
        turnContainer.getChildren().addAll( turnText, turnTextPlayer);
        
        Text money = new Text( "Money: ");
        money.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        moneyCount = new Text( "" );
        moneyCount.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        moneyPrevision = new Text( "" );
        moneyPrevision.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        moneyDataContainer = new HBox( 5 );
        moneyDataContainer.setAlignment(Pos.CENTER_LEFT);
        moneyDataContainer.setVisible(false);
        moneyDataContainer.getChildren().addAll(money, moneyCount, moneyPrevision );
        moneyDataContainer.setPrefHeight( 35d );
        moneyDataContainer.setPrefWidth( 2*buttonWidthDimension - 360 );
        
        Button skipButton = new Button();
        ImageView skipImage = new ImageView( imageArray[10]);
        skipImage.setPreserveRatio(true);
        skipImage.setFitWidth( 30d );
        skipButton.setGraphic(skipImage);
        
        skipButton.setOnAction( (e) ->{
            
            if ( model.canBuyBuilding )
            {
                model.canBuyBuilding = false;
                buyBuildings.setStyle( "" );
            }
            else if ( model.canBuyUnit )
            {
                model.canBuyUnit = false;
                buyUnits.setStyle( "" );
            }
            
            for ( VillageModel vm : model.villageArray[model.currentPlayer])
                for ( HexagonModel hm : vm.getTiles() )
                    if ( hm.getTileObject() != null && hm.getTileObject().getUnitType() != null )
                        if ( unitViewMatrix[hm.getRow()][hm.getCol()] != null)
                            stopAnimation( hm.getRow(), hm.getCol() );
        
            model.currentTurn += 1;
            model.currentTurn = model.currentTurn % numberOfPlayers;
            if ( model.currentTurn == 0 )
                model.isFirstTurn = false;
            findRightTextPlayer( turnTextPlayer );
            moneyDataContainer.setVisible(false);
            model.unitSelected = null;
            model.villageModelSelected = null;
            resetVillageHutButton();
            deactiveUnitDataContainer();
            
            boolean founded = false;
            while ( !founded )
            {
                if ( model.villageArray[model.currentPlayer].isEmpty())
                {
                    model.colorsArray[model.currentPlayer] = false;
                    findRightTextPlayer( turnTextPlayer );
                    int numActivePlayers = 0;
                    for( int i = 0; i < model.colorsArray.length; ++i )
                        if ( model.colorsArray[i] )
                            numActivePlayers++;
                    if ( numActivePlayers == 1 )
                        setVictoryStage();
                    
                }
                else
                    founded = true;
            }
            if ( !model.isFirstTurn )
            {
                for( VillageModel vm : model.villageArray[model.currentPlayer] )
                {
                    vm.verifyGravedVillage();
                    vm.effectiveProfit();
                    
                    for( HexagonModel hm : vm.getTiles() )
                    {
                        if ( hm.getTileObject() != null && hm.getTileObject().getUnitType() != null )
                        {
                            hm.getTileObject().getUnit().unitNotMoved();
                            if ( unitViewMatrix[hm.getRow()][hm.getCol()] != null )
                                unitViewMatrix[hm.getRow()][hm.getCol()].startJumpAnimation();
                            else
                                System.out.println( "QUESTO NON DOVREBBE ESSERE MAI STAMPATO ERRORE");
                        }
                    }
                }
            }
            });
        
        villageDataContainer.setAlignment(Pos.CENTER);
        villageDataContainer.getChildren().addAll( turnContainer, moneyDataContainer, skipButton );
        
        unitDataContainer = new HBox( 10 );
        unitDataContainer.setAlignment(Pos.CENTER);
        
        Text unitSelectedText = new Text("unit selected: ");
        unitSelectedText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        typeUnitSelected = new Text( "" );
        typeUnitSelected.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        coordUnitSelected = new Text( "" );
        coordUnitSelected.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        
        unitDataContainer.setVisible( false );
        unitDataContainer.getChildren().addAll( unitSelectedText, typeUnitSelected, coordUnitSelected );
        
        VBox unitVillageDataContainer = new VBox( 2 );
        unitVillageDataContainer.setAlignment(Pos.CENTER);
        unitVillageDataContainer.getChildren().addAll( villageDataContainer, unitDataContainer );
        
        return unitVillageDataContainer;
        
    }
    
    private HBox defineShopInterface( double buttonWidthDimension )
    {
        Model model = Model.getModel();
        HBox shopButtonContainer = new HBox( 10 );
        
        Button unitShopButton = new Button();
        Button buildingShopButton = new Button();
        
        buyUnits = new Button();
        buyBuildings = new Button();
        
        ImageView[] unitShopArray = new ImageView[4];
        for ( int i = 0; i < unitShopArray.length; ++i )
        {
            unitShopArray[i] = new ImageView( imageArray[i] );
            unitShopArray[i].setPreserveRatio( true );
            unitShopArray[i].setFitHeight( 80d );
        }
        
        ImageView[] buildingShopArray = new ImageView[3];
        for (  int i = 0; i < buildingShopArray.length; ++i )
        {
            buildingShopArray[i] = new ImageView( imageArray[i + 4] );
            buildingShopArray[i].setPreserveRatio( true );
            buildingShopArray[i].setFitHeight( 80d );
        }
        
        Text[] buyUnitText = new Text[4];
        for ( int i = 0; i < buyUnitText.length; ++i )
        {
            buyUnitText[i] = new Text( buttonDescription[i]);
            buyUnitText[i].setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        }
        
        Text[] buyBuildingText = new Text[3];
        for ( int i = 0; i < buyBuildingText.length; ++i )
        {
            buyBuildingText[i] = new Text( buttonDescription[i + 4]);
            buyBuildingText[i].setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        }
        this.villageHutPrice = buyBuildingText[0];
        buyUnits.setPrefWidth( buttonWidthDimension );
        buyUnits.setPrefHeight( 45d );
        buyUnits.setGraphic( buyUnitText[0] );
        buyUnits.setOnAction( (e) -> {
            
            model.canBuyUnit = !model.canBuyUnit;
            if ( model.canBuyBuilding )
            {
                model.canBuyBuilding = false;
                buyBuildings.setStyle("");
            }
            if ( model.canBuyUnit )
                buyUnits.setStyle("-fx-base: green;");
            else
                buyUnits.setStyle("");
            });
        
        buyBuildings.setPrefWidth( buttonWidthDimension );
        buyBuildings.setPrefHeight( 45d );
        buyBuildings.setGraphic( buyBuildingText[0]);
        
        buyBuildings.setOnAction( (e) -> {
            model.canBuyBuilding = !model.canBuyBuilding;
            if ( model.canBuyUnit )
            {
                model.canBuyUnit = false;
                buyUnits.setStyle("");
            }
            if ( model.canBuyBuilding )
                buyBuildings.setStyle("-fx-base: green;");
            else 
                buyBuildings.setStyle("");
            });      
        
        unitShopButton.setPrefWidth( buttonWidthDimension );
        unitShopButton.setPrefHeight( 85d );
        unitShopButton.setGraphic( unitShopArray[0] );
        
        unitShopButton.setOnAction( (e) -> {
            
            if ( model.canBuyUnit )
            {
                model.canBuyUnit = false;
                buyUnits.setStyle("");           
            }
            if ( model.canBuyBuilding )
            {
                model.canBuyBuilding = false;
                buyBuildings.setStyle("");
            }
            model.currentUnitShop += 1;
            model.currentUnitShop = model.currentUnitShop % 4;
            unitShopButton.setGraphic( unitShopArray[model.currentUnitShop]);
            buyUnits.setGraphic( buyUnitText[model.currentUnitShop]);
            });
        
        buildingShopButton.setPrefWidth( buttonWidthDimension );
        buildingShopButton.setPrefHeight( 85d );
        buildingShopButton .setGraphic( buildingShopArray[0] );
        
        buildingShopButton.setOnAction( (e) -> {
            
            if ( model.canBuyUnit )
            {
                model.canBuyUnit = false;
                buyUnits.setStyle("");           
            }
            if ( model.canBuyBuilding )
            {
                model.canBuyBuilding = false;
                buyBuildings.setStyle("");
            }
            model.currentBuildingShop += 1;
            model.currentBuildingShop = model.currentBuildingShop % 3;
            buildingShopButton.setGraphic( buildingShopArray[model.currentBuildingShop]);
            buyBuildings.setGraphic( buyBuildingText[model.currentBuildingShop]);
            });
        
        VBox unitVBox = new VBox( 10 );
        VBox buildingVBox = new VBox( 10 );
        
        unitVBox.setAlignment(Pos.CENTER);
        buildingVBox.setAlignment(Pos.CENTER);
        
        unitVBox.getChildren().addAll( unitShopButton, buyUnits );
        buildingVBox.getChildren().addAll( buildingShopButton, buyBuildings );
        
        shopButtonContainer.setAlignment(Pos.CENTER);
        shopButtonContainer.getChildren().addAll( unitVBox, buildingVBox );

        return shopButtonContainer;
    }
    
    private void findRightTextPlayer( Text turnTextPlayer )
    {
        Model model = Model.getModel();
        
        int supportVariable = model.currentTurn;
        int index = 0;

        for( int i = 0; i < model.colorsArray.length; ++i )
        {
            if( model.colorsArray[i] )
            {
                supportVariable -= 1;
                index = i;
            }
            if ( supportVariable < 0 )
                break;
        }
        
        turnTextPlayer.setText(playerName[index]);
        if ( index == 0 )
            turnTextPlayer.setFill(Color.RED);
        else if ( index == 1 )
            turnTextPlayer.setFill(Color.CYAN);
        else if ( index == 2 )
            turnTextPlayer.setFill(Color.LIGHTGREEN);
        else if ( index == 3 )
            turnTextPlayer.setFill(Color.YELLOW);
        else if ( index == 4 )
            turnTextPlayer.setFill(Color.WHITE);
        else
            turnTextPlayer.setFill(Color.PINK);
        
        model.currentPlayer = index;
    }
    
    public void setCoordUnitSelected( int row, int col )
    {
        this.coordUnitSelected.setText( " ( " + row + ", " + col + " )" );
    }
    
    public void setTypeUnitSelected( Model.UnitType unitType )
    {
        if ( unitType == Model.UnitType.PEASANT )
            this.typeUnitSelected.setText( "PEASANT");
        else if ( unitType == Model.UnitType.SPEARMAN )
            this.typeUnitSelected.setText( "SPEARMAN");
        else if ( unitType == Model.UnitType.BARON )
            this.typeUnitSelected.setText( "BARON");
        else 
            this.typeUnitSelected.setText( "KNIGHT");
        
        this.unitDataContainer.setVisible(true);
    }
    
    public void deactiveUnitDataContainer()
    {
        this.unitDataContainer.setVisible(false);
    }
    
    public void addObjectInGame( int row, int col, ObjectsView objectToAdd )
    {
        this.mapContainer.getChildren().add( objectToAdd );
        
        if ( unitViewMatrix[row][col] == null )
        {
            unitViewMatrix[row][col] = objectToAdd;
        }
        else
        {
            mapContainer.getChildren().remove(unitViewMatrix[row][col]);
            unitViewMatrix[row][col] = objectToAdd;
        }
    }
    
    public void setMoneyVillage( int villageMoney, int villagePrevision )
    {
        moneyCount.setText( "" + villageMoney );
        
        String previsionString = "";
        if ( villagePrevision >= 0 )
            previsionString += "+";
        
        previsionString += "" + villagePrevision;
        
        moneyPrevision.setText(previsionString);
        
        moneyDataContainer.setVisible(true);
    }
    
    public void deleteMoneyVillageText()
    {
        moneyDataContainer.setVisible(false);
    }
    
    public void controllDownTile( int row, int col )
    {
        hexagonViewMatrix[row][col].hexagonViewBehavior();
    }
    
    public ObjectsView getObjectsView( int row, int col )
    {
        return unitViewMatrix[row][col];
    }
    
    public void removeObjectInGame( int row, int col )
    {
        mapContainer.getChildren().remove( unitViewMatrix[row][col] );
        unitViewMatrix[row][col] = null;
    }
    
    public void determinateVisibleBorders( int row, int col )
    {
        Model model = Model.getModel();
        hexagonViewMatrix[row][col].assignWeight(model.hexagonModelMatrix[row][col].getWeight());
    }

    public void changeObjectViewCenterOfVillage( int row, int col )
    {
        if ( unitViewMatrix[row][col] != null )
        {
            mapContainer.getChildren().remove( unitViewMatrix[row][col] );
        }
        
        unitViewMatrix[row][col] = new ObjectsView( row, col, dimension, imageArray[7] , false );
        mapContainer.getChildren().add( unitViewMatrix[row][col] );
    }
    
    public void changeObjectViewTree( int row, int col )
    {
        if ( unitViewMatrix[row][col] != null )
        {
            mapContainer.getChildren().remove( unitViewMatrix[row][col] );
        }
        
        unitViewMatrix[row][col] = new ObjectsView( row, col, dimension, imageArray[8] , false );
        mapContainer.getChildren().add( unitViewMatrix[row][col] );  
    }
    
    public void changeObjectViewGrave( int row, int col )
    {
        if ( unitViewMatrix[row][col] != null )
        {
            mapContainer.getChildren().remove( unitViewMatrix[row][col] );
        }
        
        unitViewMatrix[row][col] = new ObjectsView( row, col, dimension, imageArray[9] , false );
        mapContainer.getChildren().add( unitViewMatrix[row][col] );  
    }
    
    public void changeObjectPosition( int startRow, int startCol, int finalRow, int finalCol )
    {
        if ( unitViewMatrix[finalRow][finalCol] != null )
            mapContainer.getChildren().remove( unitViewMatrix[finalRow][finalCol]);
        unitViewMatrix[finalRow][finalCol] = unitViewMatrix[startRow][startCol];
        unitViewMatrix[startRow][startCol] = null;
    }
    
    public void startAnimation( int row, int col )
    {
        if ( unitViewMatrix[row][col] != null )
            unitViewMatrix[row][col].startJumpAnimation();
    }
    
    public void stopAnimation( int row, int col )
    {
        if ( unitViewMatrix[row][col] != null )
            unitViewMatrix[row][col].stopJumpAnimation();
    }
    
    public void setVictoryStage()
    {
        Scene victoryWindowScene = FactoryView.createVictoryWindow();
        Stage victoryWindowStage = FactoryView.createVictoryStage( victoryWindowScene );
        Scene playGameWindowScene = SceneManager.getPlayGameWindowScene();
        Stage playGameWindowStage = (Stage) playGameWindowScene.getWindow();
        SceneManager.setVictoryWindowScene(victoryWindowScene);
        playGameWindowStage.close();
        victoryWindowStage.show();
    }
    
    public void resetVillageHutButton()
    {
        this.villageHutPrice.setText(buttonDescription[4]);
    }
    
    public void changeVillageHutPrice( int newPrice )
    {
        this.villageHutPrice.setText( "buy ( cost " + newPrice + " )" );
    }
    
}
