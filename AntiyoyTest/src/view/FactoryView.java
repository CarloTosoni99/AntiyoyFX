/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;

/**
 *
 * @author toson
 */
public class FactoryView 
        
{
    
    public static Scene createMainMenuScene()
    {
        MainMenu mainMenu = new MainMenu();
        Scene mainMenuScene = new Scene( mainMenu, 280, 300 );
        return mainMenuScene;
    }
    
    public static Scene createCustomGameMenuScene()
    {
        CustomGameMenu customGameMenu = new CustomGameMenu();
        Scene customGameMenuScene = new Scene( customGameMenu, 550, 380 );
        return customGameMenuScene;
    }
    
    public static Scene createPlayGameWindow( )
    {
        Model model = Model.getModel();
        model.createMatrix();
        
        PlayGameWindow playGameWindow = new PlayGameWindow( model.dimension );
        Scene playGameWindowScene = new Scene( playGameWindow );
        return playGameWindowScene;

    }
    
    public static Scene createVictoryWindow()
    {
        VictoryWindow victoryWindow = new VictoryWindow();
        Scene victoryWindowScene = new Scene ( victoryWindow );
        return victoryWindowScene;        
    }
    
    public static Scene createHowToPlayWindow()
    {
        HowToPlayWindow howToPlayWindow = new HowToPlayWindow();
        Scene howToPlayWindowScene = new Scene( howToPlayWindow );
        return howToPlayWindowScene;
    }
    
    public static Stage createSecondaryStage( Scene customGameMenuScene )
    {
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Antiyoy");
        secondaryStage.setScene( customGameMenuScene );
        return secondaryStage;
    }
    
    public static Stage createPrimaryStage( Scene mainMenuScene )
    {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("antiyoy");
        primaryStage.setScene( mainMenuScene );
        return primaryStage;
    }
    
    public static Stage createPlayGameStage( Scene playGameWindowScene )
    {
        Stage playGameStage = new Stage();
        playGameStage.setTitle("Antiyoy");
        playGameStage.setScene( playGameWindowScene );
        return playGameStage; 
    }
    
    public static Stage createVictoryStage( Scene victoryWindowScene )
    {
        Stage victoryWindowStage = new Stage();
        victoryWindowStage.setTitle("Antiyoy");
        victoryWindowStage.setScene( victoryWindowScene );
        return victoryWindowStage;
    }
    
    public static Stage createHowToPlayStage( Scene howToPlayWindowScene )
    {
        Stage howToPlayStage = new Stage();
        howToPlayStage.setTitle("Antiyoy");
        howToPlayStage.setScene(howToPlayWindowScene);
        return howToPlayStage;
    }
}
