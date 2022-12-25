/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author toson
 */
public class MainMenu extends VBox
{
    private final double menuWidth = 120d;
    private final double menuHeight = 50d;
    
    
    public MainMenu()
    {
        super (10);
        this.setAlignment(Pos.CENTER);
        this.setPadding( new Insets( 10, 5, 10, 5 ));
        
        
        Button playGameButton = new Button("Start Game");
        Button loadButton = new Button("Load Game");
        Button howToPlayButton = new Button("How To Play");
        Button optionsButton = new Button("Options");
        
        Label authorLabel = new Label("Created by Carlo Tosoni");
        
        playGameButton.setOnAction(( ae ) -> {
            Scene customGameMenuScene = FactoryView.createCustomGameMenuScene();
            Stage secondaryStage = FactoryView.createSecondaryStage(customGameMenuScene);
            SceneManager.setCustomGameMenuScene(customGameMenuScene);
            Scene mainMenuScene = SceneManager.getMainMenuScene();
            Stage primaryStage = (Stage) mainMenuScene.getWindow();
            primaryStage.close();
            secondaryStage.show();
        });
        
        howToPlayButton.setOnAction(( ae ) -> {
            Scene howToPlayWindowScene = FactoryView.createHowToPlayWindow();
            Stage howToPlayStage = FactoryView.createHowToPlayStage(howToPlayWindowScene);
            SceneManager.setHotToPlayWindowScene(howToPlayWindowScene);
            Scene mainMenuScene = SceneManager.getMainMenuScene();
            Stage primaryStage = (Stage) mainMenuScene.getWindow();
            primaryStage.close();
            howToPlayStage.show();
        });
        
        playGameButton.setPrefWidth(menuWidth);
        playGameButton.setPrefHeight(menuHeight);
        loadButton.setPrefWidth(menuWidth);
        loadButton.setPrefHeight(menuHeight);
        howToPlayButton.setPrefWidth(menuWidth);
        howToPlayButton.setPrefHeight(menuHeight);
        optionsButton.setPrefWidth(menuWidth);
        optionsButton.setPrefHeight(menuHeight);
        

        setAlignment(Pos.CENTER);
        
        getChildren().addAll( playGameButton, loadButton, howToPlayButton, optionsButton, authorLabel );
        
            
    }
    

}
