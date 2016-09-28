package com.filipealves.pomodoro.model;

/**
 * Created by Filipe on 28/09/16.
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
}
