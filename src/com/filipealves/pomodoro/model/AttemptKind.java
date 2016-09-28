package com.filipealves.pomodoro.model;

/**
 * Created by Filipe on 28/09/16.
 */
public enum AttemptKind {
    FOCUS(25 * 60),
    BREAK(5 * 60);

    private int mTotalSeconds;

    AttemptKind(int mTotalSeconds) {
        this.mTotalSeconds = mTotalSeconds;
    }

    public int getTotalSeconds() {
        return mTotalSeconds;
    }
}
