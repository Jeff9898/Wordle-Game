import React, { useState, useEffect } from 'react';
import '../styles/WordleGame.css';

const WordleGame = () => {
    const [guesses, setGuesses] = useState([]);
    const [currentGuess, setCurrentGuess] = useState('');
    const [feedback, setFeedback] = useState([]);
    const [gameOver, setGameOver] = useState(false);
    const [message, setMessage] = useState('');

    useEffect(() => {
        document.addEventListener('keydown', handleKeyPress);
        return () => {
            document.removeEventListener('keydown', handleKeyPress);
        };
    }, [currentGuess, gameOver]);

    const handleKeyPress = (event) => {
        if (gameOver) return;
        
        if (event.key === 'Enter' && currentGuess.length === 5) {
            submitGuess();
        } else if (event.key === 'Backspace') {
            setCurrentGuess(prev => prev.slice(0, -1));
        } else if (currentGuess.length < 5 && event.key.match(/^[a-zA-Z]$/)) {
            setCurrentGuess(prev => prev + event.key.toUpperCase());
        }
    };

    const submitGuess = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/guess', {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain',
                },
                body: currentGuess
            });
            
            const result = await response.json();
            setGuesses([...guesses, currentGuess]);
            setFeedback([...feedback, result.feedback]);
            setGameOver(result.gameOver);
            setMessage(result.message);
            setCurrentGuess('');
            
        } catch (error) {
            console.error('Error:', error);
            setMessage('Error connecting to server');
        }
    };

    const startNewGame = async () => {
        try {
            await fetch('http://localhost:8080/api/new-game', {
                method: 'POST'
            });
            setGuesses([]);
            setFeedback([]);
            setCurrentGuess('');
            setGameOver(false);
            setMessage('');
        } catch (error) {
            console.error('Error:', error);
            setMessage('Error starting new game');
        }
    };

    return (
        <div className="wordle-game">
            <h1>Wordle</h1>
            <div className="game-board">
                {guesses.map((guess, i) => (
                    <div key={i} className="guess-row">
                        {guess.split('').map((letter, j) => (
                            <div key={j} className={`letter ${feedback[i][j].toLowerCase()}`}>
                                {letter}
                            </div>
                        ))}
                    </div>
                ))}
                {!gameOver && (
                    <div className="guess-row current">
                        {currentGuess.split('').map((letter, i) => (
                            <div key={i} className="letter">{letter}</div>
                        ))}
                        {[...Array(5 - currentGuess.length)].map((_, i) => (
                            <div key={i + currentGuess.length} className="letter empty" />
                        ))}
                    </div>
                )}
            </div>
            {message && <div className="message">{message}</div>}
            {gameOver && (
                <button className="new-game-button" onClick={startNewGame}>
                    New Game
                </button>
            )}
        </div>
    );
};

export default WordleGame;