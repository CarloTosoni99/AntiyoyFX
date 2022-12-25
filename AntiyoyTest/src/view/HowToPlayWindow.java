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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author toson
 */
public class HowToPlayWindow extends VBox
{
    private static final double GROUP_DISTANCE = 40d;
    private static final double TEXT_DISTANCE = 8d;
    private static final double HORIZONTAL_DIMENSION = 400d;
    private static final double HORIZONTAL_DISTANCE = 14d;
    private static final double LITTLE_HORIZONTAL_DISTANCE = 5d;
    private static final double IMAGE_DIMENSION = 100d;
    private static final int LENGTH = 6;
    
    private Image[] imageArray = new Image[11];
    
    public HowToPlayWindow()
    {
        super( GROUP_DISTANCE );
        this.setAlignment(Pos.CENTER);
        this.setPadding( new Insets( 10, 20, 10, 20));
        
        HBox[] hBoxArray = new HBox[LENGTH];
        VBox[] vBoxArray = new VBox[LENGTH];
        ImageView[] imageViewArray = new ImageView[LENGTH];
        Text[][] textMatrix = new Text[LENGTH][2];
        
        HBox[] hBoxTextArray = new HBox[LENGTH];
        Text[][] coloredTextMatrix = new Text[LENGTH][3];
        
        assignImages();
        
        for( int i = 0; i < hBoxArray.length; ++i )
        {
            hBoxArray[i] = new HBox( HORIZONTAL_DISTANCE );
            hBoxArray[i].setAlignment(Pos.CENTER);
            hBoxArray[i].setPrefWidth(HORIZONTAL_DIMENSION);
            hBoxArray[i].setMaxWidth(HORIZONTAL_DIMENSION);
            vBoxArray[i] = new VBox( TEXT_DISTANCE );
            
            hBoxTextArray[i] = new HBox( LITTLE_HORIZONTAL_DISTANCE );
            
            imageViewArray[i] = new ImageView();
            imageViewArray[i].setPreserveRatio(true);
            imageViewArray[i].setSmooth(true);
            imageViewArray[i].setFitHeight(IMAGE_DIMENSION);
            
            if ( i % 2 == 0 )
            {
                vBoxArray[i].setAlignment(Pos.CENTER_LEFT);
                hBoxTextArray[i].setAlignment(Pos.CENTER_LEFT);
            }
            else
            {
                vBoxArray[i].setAlignment(Pos.CENTER_RIGHT);
                hBoxTextArray[i].setAlignment(Pos.CENTER_RIGHT);
            }
            
            for( int j = 0; j < textMatrix[i].length; ++j )
            {
                textMatrix[i][j] = new Text("");
                textMatrix[i][j].setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 19));
            }
            for( int j = 0; j < coloredTextMatrix[i].length; ++j )
            {
                coloredTextMatrix[i][j] = new Text("");
                coloredTextMatrix[i][j].setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 19));
                if ( j == 1 )
                {
                    coloredTextMatrix[i][j].setStrokeWidth( 1d );
                    coloredTextMatrix[i][j].setStroke(Color.BLACK);
                }
                
            }
        }
        
        textMatrix[0][0].setText( "Progetto esame \"Programmazione Interfacce Grafiche\"" );
        coloredTextMatrix[0][0].setText( "Università degli studi di ");
        coloredTextMatrix[0][1].setText("PERUGIA");
        coloredTextMatrix[0][1].setFill(Color.RED);
        hBoxTextArray[0].getChildren().addAll( coloredTextMatrix[0][0], coloredTextMatrix[0][1] );
        
        textMatrix[0][1].setText( "Carlo Tosoni 2020/2021");
        vBoxArray[0].getChildren().addAll( textMatrix[0][0], hBoxTextArray[0], textMatrix[0][1] );
        
        imageViewArray[0].setImage( imageArray[3] );
        hBoxArray[0].getChildren().addAll( vBoxArray[0], imageViewArray[0] );

        textMatrix[1][0].setText( "Welcome in Antiyoy! The purpose of the game");
        coloredTextMatrix[1][0].setText( "is to defend yours ");
        coloredTextMatrix[1][1].setText( "VILLAGES ");
        coloredTextMatrix[1][1].setFill(Color.CYAN);
        coloredTextMatrix[1][2].setText( "from the enemies players;");
        hBoxTextArray[1].getChildren().addAll( coloredTextMatrix[1] );
        
        textMatrix[1][1].setText( "Every village has an own economy and its coins.");
        vBoxArray[1].getChildren().addAll( textMatrix[1][0], hBoxTextArray[1], textMatrix[1][1] );
        
        imageViewArray[1].setImage( imageArray[7]);
        imageViewArray[1].setFitHeight( 120d );
        hBoxArray[1].getChildren().addAll( imageViewArray[1], vBoxArray[1]);
        
        textMatrix[2][0].setText( "Use your coins to buy units and buildinds,");
        coloredTextMatrix[2][0].setText( "place buildings on your tiles to ");
        coloredTextMatrix[2][1].setText( "DEFENDE");
        coloredTextMatrix[2][1].setFill(Color.LIGHTGREEN);
        coloredTextMatrix[2][2].setText( "your territory.");
        hBoxTextArray[2].getChildren().addAll( coloredTextMatrix[2] );
        
        textMatrix[2][1].setText( "But be careful, buildings have a maintenance cost.");
        vBoxArray[2].getChildren().addAll( textMatrix[2][0], hBoxTextArray[2], textMatrix[2][1] );
        
        imageViewArray[2].setImage( imageArray[6] );
        hBoxArray[2].getChildren().addAll( vBoxArray[2], imageViewArray[2] );
        
        textMatrix[3][0].setText( "Buy units to conquer enemies tiles,");
        coloredTextMatrix[3][0].setText( "Remember, if your village goes ");
        coloredTextMatrix[3][1].setText( "BANKRUPT");
        coloredTextMatrix[3][1].setFill(Color.YELLOW);
        coloredTextMatrix[3][2].setText( "every unit");
        hBoxTextArray[3].getChildren().addAll( coloredTextMatrix[3] );
        
        textMatrix[3][1].setText( "of the village will die. ( Rip )");
        vBoxArray[3].getChildren().addAll( textMatrix[3][0], hBoxTextArray[3], textMatrix[3][1] );
        
        imageViewArray[3].setImage( imageArray[9] );
        imageViewArray[3].setFitHeight( 130d );
        hBoxArray[3].getChildren().addAll( imageViewArray[3], vBoxArray[3] );
        
        textMatrix[4][0].setText( "To improve your economy, you can conquer tiles or build");
        coloredTextMatrix[4][0].setText( "many ");
        coloredTextMatrix[4][1].setText( "VILLAGE HUTS");
        coloredTextMatrix[4][1].setFill(Color.WHITE);
        coloredTextMatrix[4][2].setText( "around your village.");
        hBoxTextArray[4].getChildren().addAll( coloredTextMatrix[4] );
        
        textMatrix[4][1].setText( "Destroy enemy village huts to win yours clashes");
        vBoxArray[4].getChildren().addAll( textMatrix[4][0], hBoxTextArray[4], textMatrix[4][1] );
        
        imageViewArray[4].setImage( imageArray[4] );
        hBoxArray[4].getChildren().addAll( vBoxArray[4], imageViewArray[4] );
        
        textMatrix[5][0].setText( "plan your strategy, forge your kingdom and defeat your");
        coloredTextMatrix[5][0].setText( "enemies, remain the ");
        coloredTextMatrix[5][1].setText( "LAST PLAYER");
        coloredTextMatrix[5][1].setFill(Color.PINK);
        coloredTextMatrix[5][2].setText( "in the game to");
        hBoxTextArray[5].getChildren().addAll( coloredTextMatrix[5] );
        
        textMatrix[5][1].setText( "win the match, good luck =)");
        vBoxArray[5].getChildren().addAll( textMatrix[5][0], hBoxTextArray[5], textMatrix[5][1] );
        
        imageViewArray[5].setImage( imageArray[0] );
        hBoxArray[5].getChildren().addAll( imageViewArray[5], vBoxArray[5] );
        
        Button backToMenuButton = new Button( "Return to Main Menu");
        backToMenuButton.setOnAction( (e) -> {
            Scene mainMenuScene = SceneManager.getMainMenuScene();
            Stage primaryStage = FactoryView.createPrimaryStage(mainMenuScene);
            Scene howToPlayWindowScene = SceneManager.getHowToPlayWindowScene();
            Stage howToPlayStage = (Stage) howToPlayWindowScene.getWindow();
            howToPlayStage.close();
            primaryStage.show();
        });
        backToMenuButton.setPrefWidth( 200d );
        backToMenuButton.setPrefHeight( 50d );
                
        this.getChildren().addAll( hBoxArray );
        this.getChildren().add( backToMenuButton );
        
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
    }
}
