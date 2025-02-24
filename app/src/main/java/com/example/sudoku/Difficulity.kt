package com.example.sudoku

enum class Difficulty(val removedCells: Int) {
    EASY(30),
    NORMAL(40),
    HARD(50)
}
