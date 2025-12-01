package com.comp2042.gui;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * This class displays notifications during gameplay such as points earned, combos, and level-up messages.
 * Notification panel appears on screen with animation effects when rows are cleared.
 * 
 * @author Sek Joe Rin
 */
public class NotificationPanel extends BorderPane {

    /**
     * Creates a new NotificationPanel with the specified text.
     * 
     * @param text the notification message to display
     */
    public NotificationPanel(String text) {
        setMinHeight(560);
        setMinWidth(280);
        final Label notification = new Label(text);
        notification.getStyleClass().add("notificationStyle");
        notification.setTextAlignment(TextAlignment.CENTER);
        final Effect glow = new Glow(0.6);
        notification.setEffect(glow);
        notification.setTextFill(Color.WHITE);
        setCenter(notification);

    }

    /**
     * Displays the notification with fade and translate animations.
     * The notification will automatically remove itself from the list after the animation complete.
     * 
     * @param list the ObservableList of nodes where this notification is displayed on the screen
     */
    public void shownotification(ObservableList<Node> list) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2500), this);
        tt.setToY(this.getLayoutY() - 40);
        ft.setFromValue(1);
        ft.setToValue(0);
        ParallelTransition transition = new ParallelTransition(tt, ft);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(NotificationPanel.this);
            }
        });
        transition.play();
    }
}
