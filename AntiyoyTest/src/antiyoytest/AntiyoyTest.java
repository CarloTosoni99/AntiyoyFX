/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antiyoytest;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import view.CustomGameMenu;
import view.FactoryView;
import view.MainMenu;
import view.SceneManager;

/**
 *
 * @author toson
 */
public class AntiyoyTest extends Application {
    
    @Override
    public void start(Stage primaryStage) 
    {
        Scene mainMenuScene = FactoryView.createMainMenuScene();
        primaryStage.setTitle("Antiyoy");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();        
        SceneManager.setMainMenuScene( mainMenuScene );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

