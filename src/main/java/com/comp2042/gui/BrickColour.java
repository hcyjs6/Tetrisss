package com.comp2042.gui;

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
                return Color.web("#FFB6C1"); 
            case 2:
                return Color.web("#DDA0DD"); 
            case 3:
                return Color.web("#E0FFE0"); 
            case 4:
                return Color.web("#FFFACD"); 
            case 5:
                return Color.web("#FFDAB9");
            case 6:
                return Color.web("#B0E0E6"); 
            case 7:
                return Color.web("#DA70D6"); 
            default:
                return Color.web("#FFC0CB"); 
        }
    }
}

