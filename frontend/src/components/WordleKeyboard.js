import React from 'react';
import '../styles/WordleKeyboard.css';

const keyLayout = [
    ["Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"],
    ["A", "S", "D", "F", "G", "H", "J", "K", "L"],
    ["Enter", "Z", "X", "C", "V", "B", "N", "M", "Back"]
];

// keyStatuses = object mapping letters (or keys like enter/back) to their statuses:
// 'none' | 'absent' | 'present' | 'correct'
// onKeyClick = handler hooked up so that clicking a key triggers the right action
const WordleKeyboard = ({ keyStatuses, onKeyClick }) => {
    const renderKey = (key) => {
        // use keyStatuses[key] if set - otherwise default to 'none'
        const status = keyStatuses[key] || 'none';
        return (
            <button 
                key={key}
                className={`key ${status}`}
                onClick={() => onKeyClick(key)}
            >
                {key}
            </button>
        );
    };

    return (
        <div className="keyboard">
            {keyLayout.map((row, rowIndex) => (
                <div key={rowIndex} className="keyboard-row">
                    {row.map(renderKey)}
                </div>
            ))}
        </div>
    );
};

export default WordleKeyboard;