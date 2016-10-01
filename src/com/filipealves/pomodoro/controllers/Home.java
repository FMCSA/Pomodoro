package com.filipealves.pomodoro.controllers;

import com.filipealves.pomodoro.model.Attempt;
import com.filipealves.pomodoro.model.AttemptKind;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Created by Filipe on 29/09/16.
 */
public class Home {
    @FXML
    private VBox container;
    @FXML
    private Label title;

    private Attempt mCurrentAttempt;
    private StringProperty mTimerText;
    // The Animation Timeline
    private Timeline mTimeline;

    public Home() {
        mTimerText = new SimpleStringProperty();
        setTimerText(0);
    }

    private void prepareAttempt(AttemptKind kind) {
        // Clears the style of the current Attempt
        clearAttemptStyles();
        // Changes to new Attempt
        mCurrentAttempt = new Attempt("", kind);

        // Adds the new style of the current Attempt according to the CSS class with the same name as the kind
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
        setTimerText(mCurrentAttempt.getRemainingSeconds());

        mTimeline = new Timeline();
        mTimeline.setCycleCount(kind.getTotalSeconds());
        // Get all current existing keyframes and adds a new one with the duration and behaviour
        mTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            mCurrentAttempt.tick();
            setTimerText(mCurrentAttempt.getRemainingSeconds());
        }));
    }

    public void playTimer() {
        mTimeline.play();
    }

    public void pauseTimer() {
        mTimeline.pause();
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

    // Binding the data to the time label (check home.fxml)
    public String getTimerText() {
        return mTimerText.get();
    }

    public StringProperty timerTextProperty() {
        return mTimerText;
    }

    public void setTimerText(String timerText) {
        this.mTimerText.set(timerText);
    }

    public void setTimerText(int remainingSeconds) {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        // Using leading zeros with %02d (2 zeros in decimal)
        setTimerText(String.format("%02d:%02d", minutes, seconds));
    }

    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }
}
