package com.gameapp.backend.model;

public enum LetterFeedback {
    CORRECT,    // Green - right letter, right position
    PRESENT,    // Yellow - right letter, wrong position
    ABSENT      // Gray - letter not in word
}
