/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;

/**
 *
 * @author toson
 */
public class VictoryWindow extends VBox
{
    public VictoryWindow ()
    {
        super(15);
        
        Model model = Model.getModel();
        this.setAlignment(Pos.CENTER);
        this.setPadding( new Insets( 30, 30, 30 ,30 ));
        
        HBox congratulationsPhrase = new HBox (5);
        congratulationsPhrase.setAlignment(Pos.CENTER);
        
        Text startPhrase = new Text ( "The WINNER is ");
        startPhrase.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 22));
        
        Text middlePhrase = new Text( "" );
        middlePhrase.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 22));
        middlePhrase.setStrokeWidth( 1d );
        middlePhrase.setStroke(Color.BLACK);
        
        Text endPhrase = new Text( "congratulations!" );
        endPhrase.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 22));
        
        if ( model.currentPlayer == 0 )
        {
            middlePhrase.setText( "PLAYER RED ");
            middlePhrase.setFill(Color.RED);
        }
        else if ( model.currentPlayer == 1 )
        {
            middlePhrase.setText( "PLAYER BLUE ");
            middlePhrase.setFill( Color.CYAN );
        }
        else if ( model.currentPlayer == 2 )
        {
            middlePhrase.setText( "PLAYER GREEN ");
            middlePhrase.setFill( Color.LIGHTGREEN );
        }
        else if ( model.currentPlayer == 3 )
        {
            middlePhrase.setText( "PLAYER YELLOW ");
            middlePhrase.setFill( Color.YELLOW );
        }
        else if ( model.currentPlayer == 4 )
        {
            middlePhrase.setText( "PLAYER WHITE ");
            middlePhrase.setFill( Color.WHITE );
        }
        else
        {
            middlePhrase.setText( "PLAYER PINK ");
            middlePhrase.setFill( Color.PINK );
        }
        
        congratulationsPhrase.getChildren().addAll( startPhrase, middlePhrase, endPhrase );
        
        Button backToMainMenuButton = new Button( "Return to Main Menu" );
        
        backToMainMenuButton.setOnAction( (e) -> {
            Scene mainMenuScene = SceneManager.getMainMenuScene();
            Stage primaryStage = FactoryView.createPrimaryStage(mainMenuScene );
            Scene victoryWindowScene = SceneManager.getVictoryWindowScene();
            Stage victoryWindowStage = (Stage) victoryWindowScene.getWindow();
            victoryWindowStage.close();
            primaryStage.show();
            });
        
        this.getChildren().addAll( congratulationsPhrase, backToMainMenuButton );
   }
}
