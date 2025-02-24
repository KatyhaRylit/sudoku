package com.example.sudoku

data class Move(
    val row: Int,
    val col: Int,
    val oldValue: Int,
    val newValue: Int
)
