package com.gameapp.backend.model;

public class GuessResponse {
    private LetterFeedback[] feedback;
    private boolean isGameOver;
    private boolean isWin;
    private String message;

    public GuessResponse(LetterFeedback[] feedback, boolean isGameOver, boolean isWin, String message) {
        this.feedback = feedback;
        this.isGameOver = isGameOver;
        this.isWin = isWin;
        this.message = message;
    }

    // Getters
    public LetterFeedback[] getFeedback() {
        return feedback;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public String getMessage() {
        return message;
    }
}
