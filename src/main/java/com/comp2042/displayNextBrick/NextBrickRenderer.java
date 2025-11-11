package com.comp2042.displayNextBrick;

import com.comp2042.BrickColour;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class NextBrickRenderer {

    private static final int NEXT_BRICK_SIZE = 28;

    private NextBrickRenderer() {
    }

    public static Rectangle[][] initNextBrick(GridPane nextBrickPanel, int[][] nextBrickData) {
        nextBrickPanel.getChildren().clear();

        if (nextBrickData == null || nextBrickData.length == 0 || nextBrickData[0].length == 0) {
            return null;
        }

        Rectangle[][] nextBrickRectangles = new Rectangle[nextBrickData.length][nextBrickData[0].length];

        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = createRectangle(nextBrickData[i][j]);
                nextBrickRectangles[i][j] = rectangle;
                nextBrickPanel.add(rectangle, j, i);
            }
        }

        return nextBrickRectangles;
    }

    public static Rectangle[][] refreshNextBrick(GridPane nextBrickPanel, Rectangle[][] nextBrickRectangles, int[][] nextBrickData) {

        if (nextBrickRectangles == null || nextBrickRectangles.length != nextBrickData.length || nextBrickRectangles[0].length != nextBrickData[0].length) {
            return initNextBrick(nextBrickPanel, nextBrickData);
        }

        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                drawRectangle(nextBrickRectangles[i][j], nextBrickData[i][j]);
            }
        }

        return nextBrickRectangles;
    }

    private static Rectangle createRectangle(int cellValue) {
        Rectangle rectangle = new Rectangle(NEXT_BRICK_SIZE, NEXT_BRICK_SIZE);
        drawRectangle(rectangle, cellValue);
        return rectangle;
    }


    private static void drawRectangle(Rectangle rectangle, int cellValue) {
        rectangle.setFill(BrickColour.getFillColor(cellValue));

        if (cellValue != 0) {
            rectangle.setOpacity(1.0);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(1.0);
            rectangle.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        } else {
            rectangle.setOpacity(0.0);
            rectangle.setStroke(null);
        }
    }
}

