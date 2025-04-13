package com.gameapp.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gameapp.backend.service.GameService;
import com.gameapp.backend.model.GuessResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    
    @Autowired
    private GameService gameService;
    
    @PostMapping("/api/guess")
    public ResponseEntity<GuessResponse> checkGuess(@RequestBody String guess) {
        return ResponseEntity.ok(gameService.checkGuess(guess));
    }

    @PostMapping("/api/new-game")
    public ResponseEntity<String> newGame() {
        gameService.startNewGame();
        return ResponseEntity.ok("New game started");
    }
}
