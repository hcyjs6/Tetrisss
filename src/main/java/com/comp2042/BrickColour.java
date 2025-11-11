package com.comp2042;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public final class BrickColour {

    private BrickColour() {
    }

    public static Paint getFillColor(int value) {
        switch (value) {
            case 0:
                return Color.TRANSPARENT;
            case 1:
                return Color.web("#FFB6C1"); // Light Pink
            case 2:
                return Color.web("#DDA0DD"); // Plum
            case 3:
                return Color.web("#E0FFE0"); // Light Mint Green
            case 4:
                return Color.web("#FFFACD"); // Lemon Chiffon
            case 5:
                return Color.web("#FFDAB9"); // Peach Puff
            case 6:
                return Color.web("#B0E0E6"); // Powder Blue
            case 7:
                return Color.web("#DA70D6"); // Orchid
            default:
                return Color.web("#FFC0CB"); // Pink
        }
    }
}

