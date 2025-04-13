package com.gameapp.backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.gameapp.backend.model.LetterFeedback;

import jakarta.annotation.PostConstruct;

import com.gameapp.backend.model.GuessResponse;

@Service
public class GameService {
    private List<String> WORD_LIST; 
    private String currentWord;
    private int attempts;
    private static final int MAX_ATTEMPTS = 6;

    @PostConstruct
    public void init() throws IOException {
        InputStream input = getClass().getResourceAsStream("/wordle-list.txt");
        if (input == null) {
            throw new IOException("wordle-list.txt not found in classpath");
        }
        WORD_LIST = new BufferedReader(new InputStreamReader(input))
                    .lines()
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .filter(word -> word.length() == 5)
                    .collect(Collectors.toList());
        startNewGame();
    }

    public void startNewGame() {
        currentWord = getRandomWord();
        attempts = 0;
    }

    private String getRandomWord() {
        int index = (int) (Math.random() * WORD_LIST.size());
        return WORD_LIST.get(index);
    }

    public GuessResponse checkGuess(String guess) {
        attempts++;
        guess = guess.toUpperCase();
        
        if (guess.length() != 5) {
            return new GuessResponse(null, false, false, "Guess must be 5 letters");
        }

        LetterFeedback[] feedback = new LetterFeedback[5];
        boolean isWin = true;
        
        // Count letter frequencies in target word
        int[] letterCount = new int[26];
        for (char c : currentWord.toCharArray()) {
            letterCount[c - 'A']++;
        }

        // First pass: Mark correct positions
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == currentWord.charAt(i)) {
                feedback[i] = LetterFeedback.CORRECT;
                letterCount[guess.charAt(i) - 'A']--;
            }
        }

        // Second pass: mark the present letters and absent letters
        for (int i = 0; i < 5; i++) {
            if (feedback[i] == null) {
                char guessChar = guess.charAt(i);
                isWin = false;
                
                if (letterCount[guessChar - 'A'] > 0) {
                    feedback[i] = LetterFeedback.PRESENT;
                    letterCount[guessChar - 'A']--;
                } else {
                    feedback[i] = LetterFeedback.ABSENT;
                }
            }
        }

        boolean isGameOver = isWin || attempts >= MAX_ATTEMPTS;
        String message = isWin ? "Congratulations!" : 
                        (isGameOver ? "Game Over! The word was: " + currentWord : "");

        return new GuessResponse(feedback, isGameOver, isWin, message);
    }
}
