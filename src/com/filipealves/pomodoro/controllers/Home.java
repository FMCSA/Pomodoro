package com.filipealves.pomodoro.controllers;

import com.filipealves.pomodoro.model.Attempt;
import com.filipealves.pomodoro.model.AttemptKind;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * Created by Filipe on 29/09/16.
 */
public class Home {
    @FXML
    private VBox container;
    @FXML
    private Label title;
    @FXML
    private TextArea message;

    private final AudioClip mApplause;
    private Attempt mCurrentAttempt;
    private StringProperty mTimerText;
    private Timeline mTimeline;

    public Home() {
        mTimerText = new SimpleStringProperty();
        setTimerText(0);
        mApplause = new AudioClip(getClass().getResource("/sounds/applause.mp3").toExternalForm());
    }

    private void prepareAttempt(AttemptKind kind) {
        reset();

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

        // Fire an event when timeline finishes
        mTimeline.setOnFinished(e -> {
            saveCurrentAttempt();
            mApplause.play();
            prepareAttempt(mCurrentAttempt.getKind() == AttemptKind.FOCUS ?
                    AttemptKind.BREAK : AttemptKind.FOCUS);
        });
    }

    private void saveCurrentAttempt() {
        mCurrentAttempt.setMessage(message.getText());
        mCurrentAttempt.save();
    }

    private void reset() {
        // Clears the style of the current Attempt
        clearAttemptStyles();

        if(mTimeline != null && mTimeline.getStatus() == Animation.Status.RUNNING) {
            mTimeline.stop();
        }
    }

    public void playTimer() {
        container.getStyleClass().add("playing");
        mTimeline.play();
    }

    public void pauseTimer() {
        container.getStyleClass().remove("playing");
        mTimeline.pause();
    }

    private void addAttemptStyle(AttemptKind kind) {
        // Returns a list
        container.getStyleClass().add(kind.toString().toLowerCase());
    }

    // Loop through enum a pop if off
    private void clearAttemptStyles() {
        container.getStyleClass().remove("playing");
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

    public void handlePlay(ActionEvent actionEvent) {
        // If there's not a current attempt running we restart it and prepare an attempt
        if(mCurrentAttempt == null) {
            handleRestart(actionEvent);
        }
        else {
            playTimer();
        }
    }

    public void handlePause(ActionEvent actionEvent) {
        pauseTimer();
    }
}
