package com.filipealves.pomodoro.controllers;

import com.filipealves.pomodoro.model.Attempt;
import com.filipealves.pomodoro.model.AttemptKind;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Filipe on 29/09/16.
 */
public class Home {
    @FXML
    private VBox container;
    @FXML
    private Label title;

    private Attempt mCurrentAttempt;

    // Set the current Attempt to a new one
    private void prepareAttempt(AttemptKind kind) {
        clearAttemptStyles();
        mCurrentAttempt = new Attempt("", kind);
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
    }

    private void addAttemptStyle(AttemptKind kind) {
        // Returns a list
        container.getStyleClass().add(kind.toString().toLowerCase());
    }

    // Loop through enum a pop if off
    private void clearAttemptStyles() {
        for(AttemptKind kind : AttemptKind.values()) {
            container.getStyleClass().remove(kind.toString().toLowerCase());
        }
    }

    public void DEBUG(ActionEvent actionEvent) {
        System.out.println("HI mofokers");
    }
}
