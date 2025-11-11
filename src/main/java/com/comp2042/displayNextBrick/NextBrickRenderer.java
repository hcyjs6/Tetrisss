package com.comp2042.displayNextBrick;

import com.comp2042.BrickColour;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class NextBrickRenderer {

    private static final int NEXT_BRICK_SIZE = 28;

    private NextBrickRenderer() {
    }

    public static void initNextBrick(GridPane nextBrickPanel, int[][] nextBrickData) {
        nextBrickPanel.getChildren().clear();

        if (nextBrickData == null || nextBrickData.length == 0 || nextBrickData[0].length == 0) {
            return;
        }

        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
               
                Rectangle rectangle = new Rectangle(NEXT_BRICK_SIZE, NEXT_BRICK_SIZE);
                rectangle.setFill(BrickColour.getFillColor(nextBrickData[i][j]));
                rectangle.setOpacity(1.0);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(1.0);
                rectangle.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);

                if (nextBrickData[i][j] != 0) {
                    nextBrickPanel.add(rectangle, j, i);
                } 
            }
        }
    }    
}

