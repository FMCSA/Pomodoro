package com.filipealves.pomodoro.model;

/**
 * Attempt class to run for a specific time some kind of attempt (AttemptKind).
 */
public class Attempt {
    private String mMessage;
    private int mRemainingSeconds;
    private AttemptKind mKind;

    public Attempt(String message, AttemptKind kind) {
        this.mMessage = message;
        this.mKind = kind;
        this.mRemainingSeconds = kind.getTotalSeconds();
    }

    public String getMessage() {
        return mMessage;
    }

    public int getRemainingSeconds() {
        return mRemainingSeconds;
    }

    public AttemptKind getKind() {
        return mKind;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public void tick() {
        mRemainingSeconds--;
    }
}
