/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Model;

/**
 *
 * @author toson
 */
public class ObjectsView extends ImageView
{
    
    private int row;
    private int col;
    private double L;
    private double width;
    
    private double yRelativePosition;
    private double yAbsolutPosition;
    
    private TranslateTransition jumpAnimation;
    private static final double L_SMALL = 30d;
    private static final double L_MEDIUM = 22d;
    private static final double L_BIG = 18d;
    
    private static final double JUMP_HEIGHT = -7d; 
    
    private static final double DIMENSION_SMALL_HEIGHT = 57d;
    private static final double DIMENSION_SMALL_WIDTH = 57d;
    
    private static final double DIMENSION_MEDIUM_HEIGHT = 40d;
    private static final double DIMENSION_MEDIUM_WIDTH = 40d;
    
    private static final double DIMENSION_BIG_HEIGHT = 33d;
    private static final double DIMENSION_BIG_WIDTH = 33d;
    
    
    public ObjectsView( int row, int col, int dimension, Image image, boolean isVillageHut )
    {
        super();
        
        Model model = Model.getModel();
        
        this.row = row;
        this.col = col;

        this.L = 0d;  
        double offsetX = 0d;
        double offsetY = 0d;
        
        this.jumpAnimation = new TranslateTransition( Duration.millis( 400 ), this );
        jumpAnimation.setCycleCount(Timeline.INDEFINITE);
        jumpAnimation.setAutoReverse( true );
        jumpAnimation.setByY( JUMP_HEIGHT );
        
        this.setImage( image );
        this.setPreserveRatio( true );
        this.setSmooth(true);
        width = 0d;
        
        if ( dimension == 1 )
        {
            L = L_SMALL;
            this.setFitHeight( DIMENSION_SMALL_HEIGHT );
            width = DIMENSION_SMALL_WIDTH;
            
        }    
        else if ( dimension == 2 )
        {
            L = L_MEDIUM;
            this.setFitHeight( DIMENSION_MEDIUM_HEIGHT );
            width = DIMENSION_MEDIUM_WIDTH;
        }
        else if ( dimension == 3 )
        {
            L = L_BIG;
            this.setFitHeight( DIMENSION_BIG_HEIGHT );
            width = DIMENSION_BIG_WIDTH;
        }
        
        if ( isVillageHut )
        {
            this.setFitHeight(this.getFitHeight() * 2/3);
            width = width * 2/3;
        }
        
        final double sqrt3 = Math.sqrt(3d);
        
        if ( col % 2 == 1 )
            offsetY = sqrt3*L/2;
        
        offsetX = offsetX + col*3*L/2;
        offsetY = row*sqrt3*L + offsetY;
        
        this.setY( (sqrt3*L - this.getFitHeight())/2 + offsetY );
        this.setX( (2*L - width )/2 + offsetX );
        jumpAnimation.setFromY( 0 );

        this.yAbsolutPosition = this.getY();
        this.yRelativePosition = 0d;
        
        this.setOnMouseClicked( (e) -> {model.playGameWindow.controllDownTile( this.row, this.col );});
        
        
    }
    
    public void createObjectsViewTranslatAnimation(  int rowFinal, int colFinal )
    {
        final double sqrt3 = Math.sqrt(3d);
        
        double tmp_offsetY = 0;
        
        if ( this.col % 2 == 0 && colFinal % 2 == 1 )
            tmp_offsetY = sqrt3*L/2;
        else if ( this.col % 2 == 1 && colFinal % 2 == 0 )
            tmp_offsetY = -1 * sqrt3*L/2;
        
        final double offsetX = (colFinal - this.col)*3*L/2;
        final double offsetY = (rowFinal - this.row)*sqrt3*L + tmp_offsetY;
        
        this.yRelativePosition = this.yRelativePosition + offsetY;

        TranslateTransition translate = new TranslateTransition( Duration.millis(500), this );

        translate.setByX(  offsetX  );
        translate.setByY( offsetY  );
        
        this.row = rowFinal;
        this.col = colFinal;
        
        translate.play();
        
    }
    
    public void startJumpAnimation()
    {
        jumpAnimation.setFromY(yRelativePosition);
        jumpAnimation.play();
    }
    
    public void stopJumpAnimation()
    {
        jumpAnimation.stop();
        this.setY(yAbsolutPosition);
    }
    
}
