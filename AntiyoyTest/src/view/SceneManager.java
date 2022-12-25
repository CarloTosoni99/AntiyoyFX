/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author toson
 */
public class SceneManager 
{
    private static Scene mainMenuScene;
    private static Scene customGameMenuScene;
    private static Scene playGameWindowScene;
    private static Scene victoryWindowScene;
    private static Scene howToPlayWindowScene;
    
    public static void setMainMenuScene( Scene mainMenuScene )
    {
        SceneManager.mainMenuScene = mainMenuScene;
    }
    
    public static void setCustomGameMenuScene( Scene customGameMenuScene )
    {
        SceneManager.customGameMenuScene = customGameMenuScene;
    }
    
    public static void setPlayGameWindowScene( Scene playGameWindowScene )
    {
        SceneManager.playGameWindowScene = playGameWindowScene;
    }
    
    public static void setVictoryWindowScene( Scene victoryWindowScene )
    {
        SceneManager.victoryWindowScene = victoryWindowScene;
    }
    
    public static void setHotToPlayWindowScene( Scene howToPlayWindowScene )
    {
        SceneManager.howToPlayWindowScene = howToPlayWindowScene;
    }
            
    public static Scene getMainMenuScene()
    {
        return mainMenuScene;
    }
    
    public static Scene getCustomGameMenuScene()
    {
        return customGameMenuScene;
    }
    
    public static Scene getPlayGameWindowScene()
    {
        return playGameWindowScene;
    }
    
    public static Scene getVictoryWindowScene()
    {
        return victoryWindowScene;
    }
    
    public static Scene getHowToPlayWindowScene()
    {
        return howToPlayWindowScene;
    }
    
  
}
