/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;

/**
 *
 * @author toson
 */
public final class CustomGameMenu extends VBox 
{
    
    public CustomGameMenu()
    {
        super(5);
        
        Model model = Model.getModel();
        
        Node configurationGameMenu = createConfigurationsGameMenu();
        Node startGameMenu = createStartGameMenu();
        
        VBox optionsCustomeGame = new VBox( 20 );
        optionsCustomeGame.setAlignment(Pos.CENTER);
        optionsCustomeGame.getChildren().addAll(configurationGameMenu, startGameMenu);
        
        Label titleLabel = new Label("Custom your Game");
        
        this.setAlignment(Pos.CENTER);
        this.setPadding( new Insets ( 8, 8, 8, 8));
        this.getChildren().addAll(titleLabel, optionsCustomeGame);

        
        
    }
    
    private static Node createConfigurationsGameMenu()
    {
        Model model = Model.getModel();
        VBox gameCostumization = new VBox (10);
        gameCostumization.setAlignment(Pos.CENTER);
        
        TitledPane costumColor = new TitledPane();
        costumColor.setText("Choose a Color");
        
        CheckBox cb1 = new CheckBox("Red");
        cb1.setOnAction( (a) -> {
            model.colorsArray[0]=!model.colorsArray[0];
            });
        
        CheckBox cb2 = new CheckBox("Blue");
        cb2.setOnAction( (a) -> {
            model.colorsArray[1]=!model.colorsArray[1];
            });
        
        CheckBox cb3 = new CheckBox("Green");
        cb3.setOnAction( (a) -> {
            model.colorsArray[2]=!model.colorsArray[2];
            });
        
        CheckBox cb4 = new CheckBox("Yellow");
        cb4.setOnAction( (a) -> {
            model.colorsArray[3]=!model.colorsArray[3];
            });
        
        CheckBox cb5 = new CheckBox("White");
        cb5.setOnAction( (a) -> {
            model.colorsArray[4]=!model.colorsArray[4];
            });
        
        CheckBox cb6 = new CheckBox("Pink");
        cb6.setOnAction( (a) -> {
            model.colorsArray[5]=!model.colorsArray[5];
            });
        
        
        HBox checkBoxColorContainer = new HBox (5);
        checkBoxColorContainer.setAlignment(Pos.CENTER);
        checkBoxColorContainer.getChildren().addAll(cb1, cb2, cb3, cb4, cb5, cb6 );
        
        costumColor.setContent( checkBoxColorContainer );
             
        TitledPane costumDimension = new TitledPane();
        costumDimension.setText("Choose the map Dimension");
        
        ToggleGroup tg = new ToggleGroup();
        
        RadioButton rb1 = new RadioButton("Small");
        rb1.setToggleGroup( tg );
        rb1.setOnAction( ( ae) -> {
            model.dimension = 1;
            });
        
        RadioButton rb2 = new RadioButton("Medium");
        rb2.setToggleGroup( tg );        
        rb2.setOnAction( ( ae) -> {
            model.dimension = 2;
            });        
        
        RadioButton rb3 = new RadioButton("Large");
        rb3.setToggleGroup( tg );
        rb3.setOnAction( ( ae) -> {
            model.dimension = 3;
            });        
        
        HBox checkBoxDimensionContainer = new HBox(5);
        checkBoxDimensionContainer.setAlignment(Pos.CENTER);
        checkBoxDimensionContainer.getChildren().addAll( rb1, rb2, rb3 );
        
        costumDimension.setContent( checkBoxDimensionContainer );
        
        TitledPane costumEnemies = new TitledPane();
        costumEnemies.setText("Choose number of Enemies");
        
        Label choosePlayers = new Label("Choose the number of players");
        
        SpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 6);
        Spinner numberOfPlayers = new Spinner();
        numberOfPlayers.setValueFactory(svf);
        
        HBox labelSpinnerContainer = new HBox(5);
        labelSpinnerContainer.setAlignment(Pos.CENTER);
        labelSpinnerContainer.getChildren().addAll( choosePlayers, numberOfPlayers);
        
        costumEnemies.setContent( labelSpinnerContainer );
              
        gameCostumization.getChildren().addAll( costumColor, costumDimension, costumEnemies );
        
        return gameCostumization;
    }
    
    private Node createStartGameMenu()
    {
        Model model = Model.getModel();
        
        Button start = new Button("Start");
        
        start.setOnAction(( ae )-> {
            
            if ( model.dimension != 0 )
            {
                Scene playGameWindowScene = FactoryView.createPlayGameWindow();
                SceneManager.setPlayGameWindowScene(playGameWindowScene);
                Stage playGameStage = FactoryView.createPlayGameStage( playGameWindowScene );
                Scene customGameMenuScene = SceneManager.getCustomGameMenuScene();
                Stage secondaryStage = (Stage) customGameMenuScene.getWindow();
                secondaryStage.close();
                playGameStage.show();
            }
        });
        
        Button back = new Button("Back");
        
        back.setOnAction(( ae )-> {
            Scene mainMenuScene = SceneManager.getMainMenuScene();
            Stage primaryStage = FactoryView.createPrimaryStage(mainMenuScene);
            Scene customGameMenuScene = SceneManager.getCustomGameMenuScene();
            Stage secondaryStage = (Stage) customGameMenuScene.getWindow();
            secondaryStage.close();
            primaryStage.show();
        });
        
        start.setPrefWidth(120);
        back.setPrefWidth(120);
        start.setPrefHeight(55);
        back.setPrefHeight(55);

        HBox root = new HBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll( start, back );
        return root;
    }
    
}
