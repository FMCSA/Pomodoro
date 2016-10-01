package com.filipealves.pomodoro.model;

/**
 * We have a timed attempt to complete either a focus time or a break time.
 */
public enum AttemptKind {
    FOCUS(25 * 60, "Focus time"),
    BREAK(5 * 60, "Break time");

    private int mTotalSeconds;
    private String mDisplayName;

    // Constructor for each AttemptKind enum
    AttemptKind(int totalSeconds, String displayName) {
        this.mTotalSeconds = totalSeconds;
        this.mDisplayName = displayName;
    }

    public int getTotalSeconds() {
        return mTotalSeconds;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
